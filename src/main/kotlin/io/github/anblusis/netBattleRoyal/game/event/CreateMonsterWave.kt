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
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration
import kotlin.random.Random

private val NIGHT_MONSTER_KEY = NamespacedKey(plugin, "night_monster")
private val NIGHT_MONSTER_EXP_KEY = NamespacedKey(plugin, "night_monster_bonus_exp")

fun Entity.isNightMonster(): Boolean {
    return persistentDataContainer.has(NIGHT_MONSTER_KEY, PersistentDataType.BYTE)
}

fun Entity.getNightMonsterBonusExp(): Int {
    return persistentDataContainer.get(NIGHT_MONSTER_EXP_KEY, PersistentDataType.INTEGER) ?: 0
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
            val anchor = randomGlobalSpawnLocation(game, game.world, bounds, wave) ?: return@repeat
            val groupSize = Random.nextInt(wave.minGroupSize, wave.maxGroupSize + 1)

            repeat(groupSize) {
                val spawnLocation = randomGlobalClusterLocation(game.world, anchor, bounds, wave)
                val monster = game.world.spawnEntity(spawnLocation, wave.type) as? Monster ?: return@repeat
                configureMonster(game, monster, wave, nightDay)
            }
        }
    }

    private fun randomGlobalSpawnLocation(game: Game, world: World, bounds: SpawnBounds, wave: MonsterWave): Location? {
        repeat(10) {
            val x = Random.nextDouble(bounds.minX, bounds.maxX)
            val z = Random.nextDouble(bounds.minZ, bounds.maxZ)
            val blockX = x.toInt()
            val blockZ = z.toInt()
            val minY = game.minY + 1
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

        val minY = (anchor.blockY - 2).coerceAtLeast(world.minHeight + 1)
        val maxY = (anchor.blockY + 2).coerceAtMost(world.maxHeight - 2)

        val yCandidates = (minY..maxY).filter { y ->
            isSpawnableY(world, blockX, y, blockZ)
        }

        val y = yCandidates.randomOrNull() ?: anchor.blockY.coerceIn(world.minHeight + 1, world.maxHeight - 2)

        return buildSpawnLocation(world, x, y, z, wave)
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
        val enhancementTier = 1 + (nightDay - wave.minNight).coerceAtLeast(0)

        val equipment = monster.equipment
        val dropChance = 0f
        val armorItems = mutableListOf<ItemStack?>()

        equipmentSlots.forEachIndexed { i, slot ->
            if (monster.canUseEquipmentSlot(slot) && Random.nextDouble() <= enhancementTier * 0.1) {
                equipment.setDropChance(slot, dropChance)
                armorItems.add(ItemStack(Material.valueOf("${armorMaterials[enhancementTier.coerceAtMost(armorMaterials.size) - 1]}_${armorNames[i]}")))
            } else armorItems.add(null)
        }

        monster.equipment.armorContents = armorItems.toTypedArray()

        if (monster.canUseEquipmentSlot(EquipmentSlot.CHEST)) return

        if (enhancementTier >= 2 && Random.nextDouble() < 0.3 + enhancementTier * 0.05) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, (enhancementTier / 2).coerceAtMost(3) - 1, true, true))
        }

        if (enhancementTier >= 3 && Random.nextDouble() < 0.2 + enhancementTier * 0.05) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, Int.MAX_VALUE, (enhancementTier / 3).coerceAtMost(3) - 1, true, true))
        }

        if (enhancementTier >= 4 && Random.nextDouble() < 0.1 + enhancementTier * 0.05) {
            monster.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, Int.MAX_VALUE, (enhancementTier / 4).coerceAtMost(2) - 1, true, true))
        }
    }

    private val equipmentSlots = listOf(EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD)
    private val armorNames = listOf("BOOTS", "LEGGINGS", "CHESTPLATE", "HELMET")
    private val armorMaterials = listOf("LEATHER", "COPPER", "GOLDEN", "IRON", "IRON", "DIAMOND", "DIAMOND", "NETHERITE")
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
    ZOMBIE("좀비", EntityType.ZOMBIE, 350, 1, 5, 5.0, 6, 1, 24),
    SKELETON("스켈레톤", EntityType.SKELETON, 350, 1, 3, 6.0, 7, 1, 20),
    SLIME("슬라임", EntityType.SLIME, 300, 2, 4, 4.5, 5, 2, 18),
    CREEPER("크리퍼", EntityType.CREEPER, 350, 1, 1, 5.5, 8, 2, 12),
    PILLAGER("약탈자", EntityType.PILLAGER, 450, 3, 5, 6.0, 10, 3, 11),
    WITCH("마녀", EntityType.WITCH, 450, 1, 1, 4.5, 14, 3, 8),
    VINDICATOR("변명자", EntityType.VINDICATOR, 550, 1, 3, 5.0, 16, 4, 7);

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