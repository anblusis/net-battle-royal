package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Monster
import org.bukkit.entity.Slime
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration
import kotlin.random.Random

private val NIGHT_MONSTER_KEY = NamespacedKey(plugin, "night_monster")
private val NIGHT_MONSTER_EXP_KEY = NamespacedKey(plugin, "night_monster_bonus_exp")

fun isNightMonster(entity: Entity): Boolean {
    return entity.persistentDataContainer.has(NIGHT_MONSTER_KEY, PersistentDataType.BYTE)
}

fun getNightMonsterBonusExp(entity: Entity): Int {
    return entity.persistentDataContainer.get(NIGHT_MONSTER_EXP_KEY, PersistentDataType.INTEGER) ?: 0
}

class CreateMonsterWave(
    private val game: Game,
    private val regions: List<Region>,
    private val wave: MonsterWave
) : Runnable {
    override fun run() {
        regions.forEach { region ->
            MonsterWaveSpawner.spawnRegionWave(game, region, wave)
        }

        game.marmottes.filter { it.region in regions }.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("몬스터가 스폰됩니다.").color(NamedTextColor.GOLD),
                    Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(2),
                        Duration.ofMillis(500)
                    )
                )
            )
        }
    }
}

class CreateNightMonsterWave(
    private val game: Game,
    private val day: Int
) : Runnable {
    override fun run() {
        MonsterWaveSpawner.spawnGlobalWave(game, day)
    }
}

private object MonsterWaveSpawner {
    private data class SpawnBounds(
        val minX: Double,
        val maxX: Double,
        val minZ: Double,
        val maxZ: Double
    )

    fun spawnGlobalWave(game: Game, nightDay: Int) {
        val border = game.world.worldBorder
        val size = border.size
        val center = border.center
        val bounds = SpawnBounds(
            minX = center.x - size / 2,
            maxX = center.x + size / 2,
            minZ = center.z - size / 2,
            maxZ = center.z + size / 2
        )

        val area = size * size
        val avgDensity = MonsterWave.entries.map { it.groupDensity }.average()
        val groupCount = (area / avgDensity).toInt().coerceAtLeast(1)

        spawnWaveGroups(game, bounds, groupCount, nightDay) {
            MonsterWave.pickNightWave(nightDay)
        }
    }

    fun spawnRegionWave(game: Game, region: Region, wave: MonsterWave, nightDay: Int? = null) {
        val area = region.width * region.height
        val groupCount = (area / wave.groupDensity).toInt().coerceAtLeast(1)
        val bounds = SpawnBounds(
            minX = region.center.x - region.width / 2,
            maxX = region.center.x + region.width / 2,
            minZ = region.center.z - region.height / 2,
            maxZ = region.center.z + region.height / 2
        )

        spawnWaveGroups(game, bounds, groupCount, nightDay) { wave }
    }

    private fun spawnWaveGroups(
        game: Game,
        bounds: SpawnBounds,
        groupCount: Int,
        nightDay: Int?,
        waveSelector: () -> MonsterWave?
    ) {
        repeat(groupCount) {
            val wave = waveSelector() ?: return@repeat
            val anchor = randomGlobalSpawnLocation(game.world, bounds, wave) ?: return@repeat
            val groupSize = Random.nextInt(wave.minGroupSize, wave.maxGroupSize + 1)

            repeat(groupSize) {
                val spawnLocation = randomGlobalClusterLocation(game.world, anchor, bounds, wave)
                val monster = game.world.spawnEntity(spawnLocation, wave.type) as? Monster ?: return@repeat
                configureMonster(game, monster, wave, nightDay)
            }
        }
    }

    private fun randomGlobalSpawnLocation(world: World, bounds: SpawnBounds, wave: MonsterWave): Location? {
        repeat(10) {
            val x = Random.nextDouble(bounds.minX, bounds.maxX)
            val z = Random.nextDouble(bounds.minZ, bounds.maxZ)
            val blockX = x.toInt()
            val blockZ = z.toInt()
            val minY = world.minHeight + 1
            val maxY = world.getHighestBlockYAt(blockX, blockZ) + 1

            if (maxY < minY) return@repeat

            val yCandidates = (minY..maxY).filter { y ->
                isSpawnableY(world, blockX, y, blockZ)
            }
            val y = yCandidates.randomOrNull() ?: return@repeat
            return buildSpawnLocation(world, x, y, z, wave)
        }
        return null
    }

    private fun randomGlobalClusterLocation(world: World, anchor: Location, bounds: SpawnBounds, wave: MonsterWave): Location {
        val x = (anchor.x + Random.nextDouble(-wave.clusterRadius, wave.clusterRadius)).coerceIn(bounds.minX, bounds.maxX)
        val z = (anchor.z + Random.nextDouble(-wave.clusterRadius, wave.clusterRadius)).coerceIn(bounds.minZ, bounds.maxZ)
        val blockX = x.toInt()
        val blockZ = z.toInt()

        val y = pickSpawnYNearAnchor(world, blockX, blockZ, anchor.blockY)
            ?: anchor.blockY.coerceIn(world.minHeight + 1, world.maxHeight - 2)

        return buildSpawnLocation(world, x, y, z, wave)
    }

    private fun pickSpawnYNearAnchor(world: World, blockX: Int, blockZ: Int, anchorY: Int): Int? {
        val minY = (anchorY - 2).coerceAtLeast(world.minHeight + 1)
        val maxY = (anchorY + 2).coerceAtMost(world.maxHeight - 2)
        if (maxY < minY) return null

        val yCandidates = (minY..maxY).filter { y ->
            isSpawnableY(world, blockX, y, blockZ)
        }
        return yCandidates.randomOrNull()
    }

    private fun isSpawnableY(world: World, blockX: Int, y: Int, blockZ: Int): Boolean {
        val below = world.getBlockAt(blockX, y - 1, blockZ).type
        val feet = world.getBlockAt(blockX, y, blockZ).type
        val head = world.getBlockAt(blockX, y + 1, blockZ).type
        return below.isSolid && !feet.isSolid && !head.isSolid
    }

    private fun buildSpawnLocation(world: World, x: Double, y: Int, z: Double, wave: MonsterWave): Location {
        return Location(world, x, y.toDouble(), z).add(
            0.5,
            0.0,
            0.5
        )
    }

    private fun configureMonster(game: Game, monster: Monster, wave: MonsterWave, nightDay: Int?) {
        monster.customName(text(wave.displayName).color(NamedTextColor.RED))
        monster.removeWhenFarAway = false
        monster.canPickupItems = false

        if (monster is Slime) {
            monster.size = if (nightDay == null) Random.nextInt(1, 3) else Random.nextInt(2, 4)
        }

        if (nightDay == null) {
            game.entities.add(monster)
            plugin.ticker.runTask({
                game.entities.remove(monster)
                if (!monster.isDead) {
                    monster.world.spawnParticle(Particle.SMOKE, monster.location, 10, 0.5, 0.5, 0.5, 0.1)
                    monster.world.playSound(monster.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.1f)
                    monster.remove()
                }
            }, 2800L)
            return
        }

        val pdc = monster.persistentDataContainer
        pdc.set(NIGHT_MONSTER_KEY, PersistentDataType.BYTE, 1)
        pdc.set(NIGHT_MONSTER_EXP_KEY, PersistentDataType.INTEGER, wave.extraExp)

        applyNightEnhancements(monster, wave, nightDay)
        game.trackNightEntity(monster)
    }

    private fun applyNightEnhancements(monster: Monster, wave: MonsterWave, nightDay: Int) {
        val enhancementTier = ((nightDay - wave.minNight).coerceAtLeast(0) / 2) + 1

        if (enhancementTier >= 1 && Random.nextDouble() < 0.35) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 0, false, false))
        }

        if (enhancementTier >= 2 && Random.nextDouble() < 0.25) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, Int.MAX_VALUE, 0, false, false))
        }

        if (enhancementTier >= 3 && Random.nextDouble() < 0.18) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, Int.MAX_VALUE, 0, false, false))
        }

        val equipment = monster.equipment
        val dropChance = 0f
        if (enhancementTier >= 2 && Random.nextDouble() < 0.3) {
            equipment.helmet = ItemStack(if (nightDay >= 5) Material.IRON_HELMET else Material.CHAINMAIL_HELMET)
            equipment.helmetDropChance = dropChance
        }
        if (enhancementTier >= 3 && wave.type in setOf(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.PILLAGER, EntityType.VINDICATOR) && Random.nextDouble() < 0.2) {
            equipment.chestplate = ItemStack(if (nightDay >= 6) Material.IRON_CHESTPLATE else Material.CHAINMAIL_CHESTPLATE)
            equipment.chestplateDropChance = dropChance
        }
    }
}

enum class MonsterWave(
    val displayName: String,
    val type: EntityType,
    val groupDensity: Int,
    val minGroupSize: Int,
    val maxGroupSize: Int,
    val clusterRadius: Double,
    val extraExp: Int,
    val minNight: Int,
    val nightWeight: Int
) {
    ZOMBIE("좀비", EntityType.ZOMBIE, 3200, 4, 7, 5.0, 6, 1, 24),
    SKELETON("스켈레톤", EntityType.SKELETON, 3400, 3, 5, 6.0, 7, 1, 20),
    SLIME("슬라임", EntityType.SLIME, 2800, 4, 8, 4.5, 5, 1, 18),
    SILVERFISH("좀벌레", EntityType.SILVERFISH, 2600, 6, 10, 3.5, 4, 1, 14),
    CREEPER("크리퍼", EntityType.CREEPER, 4200, 2, 4, 5.5, 8, 2, 12),
    PILLAGER("약탈자", EntityType.PILLAGER, 4400, 3, 5, 6.0, 10, 2, 11),
    WITCH("마녀", EntityType.WITCH, 5200, 1, 3, 4.5, 14, 3, 8),
    VINDICATOR("변명자", EntityType.VINDICATOR, 5000, 2, 4, 5.0, 16, 4, 7);

    companion object {
        fun pickNightWave(day: Int): MonsterWave? {
            val candidates = entries.filter { it.minNight <= day }
            if (candidates.isEmpty()) return null

            val totalWeight = candidates.sumOf { it.nightWeight }
            var picked = Random.nextInt(totalWeight)
            candidates.forEach { wave ->
                picked -= wave.nightWeight
                if (picked < 0) return wave
            }

            return candidates.last()
        }
    }
}