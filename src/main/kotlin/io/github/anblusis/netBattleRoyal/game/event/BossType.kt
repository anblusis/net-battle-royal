package io.github.anblusis.netBattleRoyal.game.event

import com.destroystokyo.paper.entity.ai.Goal
import com.destroystokyo.paper.entity.ai.GoalKey
import com.destroystokyo.paper.entity.ai.GoalType
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Skeleton
import org.bukkit.entity.SkeletonHorse
import org.bukkit.entity.Slime
import org.bukkit.entity.WitherSkeleton
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.monun.tap.task.TickerTask
import org.bukkit.GameMode
import java.util.EnumSet
import java.util.UUID
import kotlin.random.Random

private val BOSS_KEY = NamespacedKey(plugin, "boss")
private val BOSS_TYPE_KEY = NamespacedKey(plugin, "boss_type")
private const val SOFT_BLOCK_MAX_BLAST_RESISTANCE = 6.0f

fun Entity.isBoss(): Boolean {
    return persistentDataContainer.has(BOSS_KEY, PersistentDataType.BYTE)
}

fun Entity.getBossType(): String? {
    return persistentDataContainer.get(BOSS_TYPE_KEY, PersistentDataType.STRING)
}

enum class BossType(val displayName: String, val drops: List<ItemStack>) {
    SKELETON_KNIGHT("스켈레톤 기사", listOf(CustomEquipment.KNIGHT_CAPE.item)) {
        override fun spawnAt(game: Game, loc: Location) {
            val horse = game.world.spawn(loc, SkeletonHorse::class.java) { h ->
                h.isTamed = true
                h.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 100.0
                h.health = 100.0
                h.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, Int.MAX_VALUE, 0, false, false, true))
            }
            val skel = game.world.spawn(loc, Skeleton::class.java) { s ->
                s.customName(text(displayName).color(NamedTextColor.RED))
                s.isCustomNameVisible = true
                s.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 100.0
                s.health = 100.0
                s.equipment.run {
                    setItemInMainHand(ItemStack(Material.BOW))
                    helmet = ItemStack(Material.DIAMOND_HELMET)
                    chestplate = CustomEquipment.KNIGHT_CAPE.item
                    leggings = ItemStack(Material.DIAMOND_LEGGINGS)
                    boots = ItemStack(Material.DIAMOND_BOOTS)
                }
                tagBoss(s, this)
            }

            horse.addPassenger(skel)
            addSkeletonKnightChargeGoal(game, horse)

            game.entities.add(horse)
            game.entities.add(skel)

            startPattern(skel, 80L) { boss ->
                val target = nearestPlayer(game, boss.location, 40.0) ?: return@startPattern
                boss.world.playSound(boss.location, Sound.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f)
                repeat(5) {
                    val aim = target.eyeLocation.toVector().subtract(boss.eyeLocation.toVector()).normalize()
                    val spread = Vector(
                        Random.nextDouble(-0.08, 0.08),
                        Random.nextDouble(-0.03, 0.06),
                        Random.nextDouble(-0.08, 0.08)
                    )
                    val arrow = boss.launchProjectile(Arrow::class.java)
                    arrow.velocity = aim.add(spread).multiply(2.0)
                }
                boss.world.spawnParticle(Particle.CRIT, boss.eyeLocation, 12, 0.2, 0.2, 0.2, 0.0)
            }

            scheduleDespawn(game, horse, skel)
        }
    },
    SLIME_OVERLORD("슬라임 군주", listOf(ItemStack(Material.SLIME_BALL, 20), CustomEquipment.SLIME_OVERLORD_BOOTS.item)) {
        override fun spawnAt(game: Game, loc: Location) {
            val slime = game.world.spawn(loc, Slime::class.java) { s ->
                s.customName(text(displayName).color(NamedTextColor.RED))
                s.isCustomNameVisible = true
                s.size = 7
                s.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 150.0
                s.health = 150.0
                s.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1, false, false, true))
                s.addPotionEffect(PotionEffect(PotionEffectType.JUMP_BOOST, Int.MAX_VALUE, 2, false, false, true))
                tagBoss(s, this)
            }
            game.entities.add(slime)
            addSlimeOverlordGoal(game, slime)
            scheduleDespawn(game, slime)
        }
    },
    BONE_SENTINEL("해골 파수꾼", listOf(CustomEquipment.SENTINEL_SOUL.item)) {
        override fun spawnAt(game: Game, loc: Location) {
            val sentinel = game.world.spawn(loc, WitherSkeleton::class.java) { s ->
                s.customName(text(displayName).color(NamedTextColor.DARK_GRAY))
                s.isCustomNameVisible = true
                s.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 140.0
                s.health = 140.0
                s.equipment.run {
                    setItemInMainHand(ItemStack(Material.STONE_SWORD))
                    helmet = CustomEquipment.BONE_HELMET.item
                    chestplate = CustomEquipment.BONE_CHESTPLATE.item
                    leggings = CustomEquipment.BONE_LEGGINGS.item
                    boots = CustomEquipment.BONE_BOOTS.item
                }
                tagBoss(s, this)
            }
            game.entities.add(sentinel)
            addBoneSentinelChargeGoal(game, sentinel)

            scheduleDespawn(game, sentinel)
        }
    };

    abstract fun spawnAt(game: Game, loc: Location)
}

private fun tagBoss(entity: LivingEntity, bossType: BossType) {
    entity.persistentDataContainer.set(BOSS_KEY, PersistentDataType.BYTE, 1)
    entity.persistentDataContainer.set(BOSS_TYPE_KEY, PersistentDataType.STRING, bossType.name)
    entity.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, Int.MAX_VALUE, 0, false, false, true))
}

private fun scheduleDespawn(game: Game, vararg entities: Entity) {
    plugin.ticker.runTask({
        entities.forEach { entity ->
            game.entities.remove(entity)
            if (!entity.isDead) entity.remove()
        }
    }, 20L * 60L * 5L)
}

private fun startPattern(entity: LivingEntity, periodTicks: Long, action: (LivingEntity) -> Unit): TickerTask {
    lateinit var task: TickerTask
    task = plugin.ticker.runTaskTimer({
        if (!entity.isValid || entity.isDead) {
            task.cancel()
            return@runTaskTimer
        }
        action(entity)
    }, periodTicks, periodTicks)
    return task
}

private fun nearestPlayer(game: Game, location: Location, radius: Double) =
    game.marmottes.map { it.player }
        .filter { it.isValid && !it.isDead && it.gameMode == GameMode.SURVIVAL }
        .filter { it.location.distanceSquared(location) <= radius * radius }
        .minByOrNull { it.location.distanceSquared(location) }

private fun addSlimeOverlordGoal(game: Game, slime: Slime) {
    val key = GoalKey.of(Slime::class.java, NamespacedKey(plugin, "slime_overlord_behavior"))
    val goal = object : Goal<Slime> {
        private var superJumpCooldown = 80
        private var superJumpActive = false
        private var airborneTicks = 0
        private var pendingJump = false

        override fun getKey(): GoalKey<Slime> = key

        override fun getTypes(): EnumSet<GoalType> = EnumSet.of(GoalType.MOVE)

        override fun shouldActivate(): Boolean {
            if (!slime.isValid || slime.isDead) return false

            if (superJumpCooldown > 0) superJumpCooldown--

            if (!superJumpActive && slime.isOnGround && superJumpCooldown <= 0) {
                pendingJump = true
                return true
            }

            return false
        }

        override fun shouldStayActive(): Boolean {
            return slime.isValid && !slime.isDead && superJumpActive
        }

        override fun start() {
            if (pendingJump) {
                val target = nearestPlayer(game, slime.location, 24.0)
                val horizontal = (target?.location?.toVector()?.subtract(slime.location.toVector())?.setY(0.0)
                    ?: Vector(Random.nextDouble(-1.0, 1.0), 0.0, Random.nextDouble(-1.0, 1.0))).normalize().multiply(0.45)

                slime.velocity = Vector(horizontal.x, 1.55, horizontal.z)
                slime.world.playSound(slime.location, Sound.ENTITY_SLIME_JUMP, 1.2f, 0.6f)
                slime.world.spawnParticle(Particle.CLOUD, slime.location, 28, 0.4, 0.2, 0.4, 0.02)

                superJumpActive = true
                airborneTicks = 0
                superJumpCooldown = 120
                pendingJump = false
            }
        }

        override fun stop() {
            pendingJump = false
        }

        override fun tick() {
            if (!superJumpActive) return

            val direction = slime.velocity.clone().apply { y = y.coerceAtLeast(0.0)}.normalize().multiply(1)
            breakSoftBlocksAround(slime.location.clone().add(0.0, 2.0, 0.0).add(direction) , 2.5)

            if (!slime.isOnGround) {
                airborneTicks++
            } else if (airborneTicks > 4) {
                applyAreaImpact(game, slime.location, 6.0, 8.0, 1.35, 0.65)
                superJumpActive = false
            }
        }
    }

    Bukkit.getMobGoals().addGoal(slime, 1, goal)
}

private fun addBoneSentinelChargeGoal(game: Game, sentinel: WitherSkeleton) {
    val key = GoalKey.of(WitherSkeleton::class.java, NamespacedKey(plugin, "bone_sentinel_charge"))
    val goal = object : Goal<WitherSkeleton> {
        private var cooldownTick = 70
        private var chargeTick = 0
        private var chargeDirection: Vector? = null
        private val hitPlayers = mutableSetOf<UUID>()

        override fun getKey(): GoalKey<WitherSkeleton> = key

        override fun getTypes(): EnumSet<GoalType> = EnumSet.of(GoalType.MOVE, GoalType.TARGET)

        override fun shouldActivate(): Boolean {
            if (!sentinel.isValid || sentinel.isDead) return false
            if (cooldownTick > 0) {
                cooldownTick--
                return false
            }

            if (Random.nextDouble() >= 0.5) {
                cooldownTick = 20
                return false
            }

            val target = nearestPlayer(game, sentinel.location, 26.0)

            if (target == null) {
                cooldownTick = 20
                return false
            }

            val direction = target.location.toVector().subtract(sentinel.location.toVector())

            if (direction.lengthSquared() <= 0.001) {
                cooldownTick = 20
                return false
            }

            chargeDirection = direction.normalize()
            return true
        }

        override fun shouldStayActive(): Boolean {
            return sentinel.isValid && !sentinel.isDead && chargeTick > 0
        }

        override fun start() {
            chargeTick = 12
            hitPlayers.clear()
            sentinel.pathfinder.stopPathfinding()
            sentinel.world.playSound(sentinel.location, Sound.ENTITY_WITHER_SKELETON_AMBIENT, 1.0f, 0.7f)
            sentinel.world.playSound(sentinel.location, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0f, 0.7f)
        }

        override fun stop() {
            chargeTick = 0
            chargeDirection = null
            hitPlayers.clear()
            cooldownTick = 120
            sentinel.pathfinder.stopPathfinding()
        }

        override fun tick() {
            val direction = chargeDirection ?: return
            if (chargeTick <= 0) {
                stop()
                return
            }

            sentinel.velocity = direction.clone().multiply(0.9).setY(0.12)

            breakSoftBlocksInFront(
                sentinel.location.clone().add(0.0, 0.2, 0.0),
                direction,
                distance = 2.4,
                halfWidth = 1.1,
                height = 2
            )

            val forward = direction.clone().setY(0.0)
            game.marmottes.map { it.player }
                .filter { it.isValid && !it.isDead }
                .filter { it.uniqueId !in hitPlayers }
                .filter { it.location.distanceSquared(sentinel.location) <= 3.2 * 3.2 }
                .forEach { player ->
                    val toPlayer = player.location.toVector().subtract(sentinel.location.toVector())
                    if (toPlayer.lengthSquared() > 0.001 && forward.lengthSquared() > 0.001) {
                        val forwardDot = toPlayer.normalize().dot(forward.clone().normalize())
                        if (forwardDot >= 0.15) {
                            player.damage(7.0, sentinel)
                            player.velocity = forward.clone().multiply(1.05).setY(0.45)
                            hitPlayers.add(player.uniqueId)
                        }
                    }
                }

            sentinel.world.spawnParticle(
                Particle.SMOKE,
                sentinel.location.clone().add(0.0, 1.0, 0.0),
                10,
                0.3,
                0.3,
                0.3,
                0.02
            )
            chargeTick--
        }
    }

    Bukkit.getMobGoals().addGoal(sentinel, 1, goal)
}

private fun breakSoftBlocksAround(
    center: Location,
    radius: Double
): Int {
    val world = center.world ?: return 0
    val radiusInt = radius.toInt() + 1
    var broken = 0

    for (x in -radiusInt..radiusInt) {
        for (y in -radiusInt..radiusInt) {
            for (z in -radiusInt..radiusInt) {
                val location = center.clone().add(x.toDouble(), y.toDouble(), z.toDouble())
                if (location.distanceSquared(center) > radius * radius) continue

                val block = world.getBlockAt(location)
                if (block.isLiquid) continue
                if (!block.type.isBossBreakable()) continue

                block.type = Material.AIR
                broken++
            }
        }
    }
    return broken
}

private fun breakSoftBlocksInFront(
    origin: Location,
    direction: Vector,
    distance: Double,
    halfWidth: Double,
    height: Int
): Int {
    if (direction.lengthSquared() <= 0.001) return 0

    val world = origin.world ?: return 0
    val forward = direction.clone().setY(0.0).normalize()
    val right = Vector(-forward.z, 0.0, forward.x)
    var broken = 0
    var currentDistance = 0.8

    while (currentDistance <= distance) {
        var side = -halfWidth
        while (side <= halfWidth) {
            for (y in 0..height) {
                val target = origin.clone()
                    .add(forward.clone().multiply(currentDistance))
                    .add(right.clone().multiply(side))
                    .add(0.0, y.toDouble(), 0.0)
                val block = world.getBlockAt(target)
                if (!block.type.isBossBreakable()) continue

                block.type = Material.AIR
                broken++
            }
            side += 0.5
        }
        currentDistance += 0.6
    }
    return broken
}

private fun applyAreaImpact(
    game: Game,
    center: Location,
    radius: Double,
    damage: Double,
    knockback: Double,
    upward: Double
) {
    game.marmottes.map { it.player }
        .filter { it.isValid && !it.isDead && it.gameMode == GameMode.SURVIVAL }
        .filter { it.location.distanceSquared(center) <= radius * radius }
        .forEach { player ->
            player.damage(damage)
            val delta = player.location.toVector().subtract(center.toVector())
            val horizontal = if (delta.setY(0.0).lengthSquared() > 0.001) {
                delta.normalize().multiply(knockback)
            } else {
                Vector(Random.nextDouble(-0.2, 0.2), 0.0, Random.nextDouble(-0.2, 0.2))
            }
            player.velocity = Vector(horizontal.x, upward, horizontal.z)
        }
}

private fun Material.isBossBreakable(): Boolean {
    if (!isSolid) return false

    return blastResistance <= SOFT_BLOCK_MAX_BLAST_RESISTANCE
}

private fun addSkeletonKnightChargeGoal(game: Game, horse: SkeletonHorse) {
    val key = GoalKey.of(SkeletonHorse::class.java, NamespacedKey(plugin, "skeleton_knight_charge"))
    val goal = object : Goal<SkeletonHorse> {
        private var chargeTick = 0
        private var cooldownTick = 0
        private var target: LivingEntity? = null

        override fun getKey(): GoalKey<SkeletonHorse> = key

        override fun getTypes(): EnumSet<GoalType> = EnumSet.of(GoalType.MOVE, GoalType.TARGET)

        override fun shouldActivate(): Boolean {
            if (!horse.isValid || horse.isDead) return false
            if (cooldownTick > 0) {
                cooldownTick--
                return false
            }
            val next = nearestPlayer(game, horse.location, 64.0) ?: return false
            if (!horse.hasLineOfSight(next)) return false
            target = next
            return true
        }

        override fun shouldStayActive(): Boolean {
            val current = target ?: return false
            return horse.isValid && !horse.isDead && current.isValid && !current.isDead
        }

        override fun start() {
            chargeTick = 40
            horse.world.playSound(horse.location, Sound.ENTITY_HORSE_GALLOP, 1.0f, 0.9f)
        }

        override fun stop() {
            chargeTick = 0
            cooldownTick = 40
            target = null
            horse.pathfinder.stopPathfinding()
        }

        override fun tick() {
            val current = target ?: return
            if (chargeTick <= 0) {
                stop()
                return
            }
            horse.pathfinder.moveTo(current.location, 2.5)
            horse.world.spawnParticle(Particle.CLOUD, horse.location, 6, 0.2, 0.1, 0.2, 0.01)
            chargeTick--
        }
    }

    Bukkit.getMobGoals().addGoal(horse, 1, goal)
}
