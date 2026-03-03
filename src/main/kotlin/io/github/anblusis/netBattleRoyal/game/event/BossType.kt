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
import org.bukkit.entity.AbstractArrow
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
import java.util.EnumSet
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

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

            startPattern(game, skel, 80L) { boss ->
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
    SLIME_OVERLORD("슬라임 군주", listOf(CustomEquipment.SLIME_HELMET.item)) {
        override fun spawnAt(game: Game, loc: Location) {
            val slime = game.world.spawn(loc, Slime::class.java) { s ->
                s.customName(text(displayName).color(NamedTextColor.GREEN))
                s.isCustomNameVisible = true
                s.size = 6
                s.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 80.0
                s.health = 80.0
                s.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 0, false, false, true))
                tagBoss(s, this)
            }
            game.entities.add(slime)

            startPattern(game, slime, 60L) { boss ->
                boss.world.playSound(boss.location, Sound.ENTITY_SLIME_SQUISH, 1.0f, 0.8f)
                boss.world.spawnParticle(Particle.ITEM_SLIME, boss.location, 80, 1.5, 0.3, 1.5, 0.1)
                boss.world.getNearbyPlayers(boss.location, 6.0).forEach { player ->
                    val knock = player.location.toVector().subtract(boss.location.toVector()).normalize()
                        .multiply(1.1).setY(0.6)
                    player.velocity = knock
                    player.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 40, 1, false, true, true))
                }
            }

            scheduleDespawn(game, slime)
        }
    },
    BONE_SENTINEL("해골 파수꾼", listOf(CustomEquipment.BONE_CHESTPLATE.item)) {
        override fun spawnAt(game: Game, loc: Location) {
            val sentinel = game.world.spawn(loc, WitherSkeleton::class.java) { s ->
                s.customName(text(displayName).color(NamedTextColor.DARK_GRAY))
                s.isCustomNameVisible = true
                s.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 120.0
                s.health = 120.0
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

            startPattern(game, sentinel, 100L) { boss ->
                boss.world.playSound(boss.location, Sound.ENTITY_WITHER_SKELETON_HURT, 1.0f, 0.9f)
                val base = boss.eyeLocation
                repeat(8) { i ->
                    val angle = i * (Math.PI * 2 / 8)
                    val dir = Vector(cos(angle), 0.1, sin(angle)).normalize()
                    val arrow = boss.world.spawnArrow(base, dir, 1.6f, 6.0f)
                    arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
                }
                boss.world.spawnParticle(Particle.ASH, boss.location, 24, 0.6, 0.3, 0.6, 0.01)
            }

            scheduleDespawn(game, sentinel)
        }
    };

    abstract fun spawnAt(game: Game, loc: Location)
}

private fun tagBoss(entity: LivingEntity, bossType: BossType) {
    entity.persistentDataContainer.set(NamespacedKey(plugin, "boss"), PersistentDataType.BYTE, 1)
    entity.persistentDataContainer.set(NamespacedKey(plugin, "boss_type"), PersistentDataType.STRING, bossType.name)
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

private fun startPattern(game: Game, entity: LivingEntity, periodTicks: Long, action: (LivingEntity) -> Unit): TickerTask {
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
        .filter { it.isValid && !it.isDead && it.world == location.world }
        .filter { it.location.distanceSquared(location) <= radius * radius }
        .minByOrNull { it.location.distanceSquared(location) }

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
            val next = nearestPlayer(game, horse.location, 24.0) ?: return false
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
            horse.pathfinder.moveTo(current.location, 3.0)
            horse.world.spawnParticle(Particle.CLOUD, horse.location, 6, 0.2, 0.1, 0.2, 0.01)
            chargeTick--
        }
    }

    Bukkit.getMobGoals().addGoal(horse, 1, goal)
}
