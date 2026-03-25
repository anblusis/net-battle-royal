package io.github.anblusis.netBattleRoyal.game

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.event.CreateNightMonsterWave
import io.github.anblusis.netBattleRoyal.game.event.FightStart
import io.github.anblusis.netBattleRoyal.game.event.WorldBorderDecrease
import io.github.anblusis.netBattleRoyal.inv.InvManager
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.world.City
import xyz.icetang.lib.invfx.frame.InvFrame
import io.github.monun.tap.task.TickerTask
import org.bukkit.GameRules
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.WorldBorder
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.awt.Color
import kotlin.math.abs
import kotlin.random.Random

class Game(
    mapName: String,
    playWorld: World,
    val mode: Int,
    players: MutableList<Player>
) {
    companion object {
        const val RANDOM_CHEST_QUALITY = true
        const val NO_READY_TIME = false
        const val FIRST_DAY_TICKS = 180 * 20
        const val NORMAL_PHASE_TICKS = 120 * 20
        const val DAY_BORDER_DECREASE_TICKS = 40 * 20
    }

    internal lateinit var chests: MutableList<RoyalChest>
    internal lateinit var regions: List<Region>
    internal lateinit var world: World
    internal lateinit var center: Location
    internal lateinit var chestTables: HashMap<ChestType, ChestLootTable>
    internal lateinit var worldBorder: WorldBorder
    internal lateinit var state: GameState
    internal lateinit var mapColors: List<Byte>
    internal lateinit var targetWorldBorderCenter: Location
    internal lateinit var worldDefaultWeather: GameWeather
    internal lateinit var customRecipes: List<CustomRecipe>
    internal lateinit var customEquipments: List<CustomEquipment>
    internal lateinit var customRecipeSets: List<CustomRecipeSet>
    internal lateinit var dropItems: List<ItemStack>
    private lateinit var chestLocations: List<ChestData>
    private val tickTask: TickerTask
    internal var worldBorderMoveTask: TickerTask? = null
    internal val mainInv: InvFrame
    internal val itemInv: InvFrame
    internal val mapItem: ItemStack

    private var worldTime: Long = 1000L
    private var chestCount: Int = 0
    internal var day: Int = 1
    internal var minY: Int = 0
    internal var phase: GamePhase = GamePhase.DAY
    internal var phaseMaxTick: Int = FIRST_DAY_TICKS
    internal val phaseDisplayName
        get() = phase.displayName
    internal val phaseProgress
        get() = if (phaseMaxTick <= 0) 1.0 else (currentPhaseTick.toDouble() / phaseMaxTick).coerceIn(0.0, 1.0)

    private var currentPhaseTick: Int = FIRST_DAY_TICKS
    internal var targetWorldBorderSize: Double = 0.0
    internal val worldBorderDots: HashMap<Pair<Int, Int>, Color?> = hashMapOf()
    internal val chestRegionCount: HashMap<Region?, Int> = hashMapOf()
    internal val tasks: MutableList<GameTask> = mutableListOf()
    internal val entities: MutableList<Entity> = mutableListOf()
    internal val nightEntities: MutableList<Entity> = mutableListOf()
    internal val marmottes: MutableList<Marmotte> = mutableListOf()
    internal val objects: MutableList<GameObject> = mutableListOf()

    internal val worldBorderCenter
        get() = worldBorder.center

    internal val worldBorderSize
        get() = worldBorder.size

    init {
        registerGame(mapName, playWorld, players.size)
        registerMarmotte(players)
        registerEvent()

        setChests()

        tickTask = plugin.ticker.runTaskTimer(this::onTick, 0L, 1L)
        mainInv = InvManager.createMainInv(this)
        itemInv = InvManager.createItemInv(this)
        mapItem = BattleRoyalMap(this).item
    }

    private fun onTick() {
        chests.forEach { it.update() }
        marmottes.forEach {
            it.update()
        }
        objects.forEach {
            it.onUpdate()
        }
        customEquipments.forEach {
            it.system.onUpdate()
        }

        val timedOutTasks = mutableListOf<GameTask>()
        tasks.forEach { task ->
            if (task.tick == 0) timedOutTasks.add(task)
            else task.tick--
        }
        timedOutTasks.forEach { task ->
            if (task.canRestart) {
                task.tick = task.maxTick
            } else {
                tasks.remove(task)
            }

            task.run()
        }

        tickDayNightCycle()
    }

    private fun setChests() {
        chests = mutableListOf()
        val leftChestLocations = mutableListOf<ChestData>()
        leftChestLocations.addAll(chestLocations)
        leftChestLocations.shuffle()

        run {
            repeat(chestCount) {
                if (leftChestLocations.isEmpty()) return@run

                val type = if (RANDOM_CHEST_QUALITY) when (leftChestLocations.first().type) {
                    ChestType.NORMAL -> if (Random.nextDouble() <= 0.1) ChestType.RARE else ChestType.NORMAL
                    ChestType.RARE -> if (Random.nextDouble() <= 0.25) ChestType.NORMAL else ChestType.RARE
                    ChestType.EPIC -> if (Random.nextDouble() <= 0.5) ChestType.RARE else ChestType.EPIC
                } else leftChestLocations.first().type

                val chest = RoyalChest(this, leftChestLocations.first().apply { this.type = type }, chestTables[type]!!)
                chests.add(chest)
                leftChestLocations.removeFirst()
            }
        }

        chests.forEach {
            chestRegionCount[it.region] = (chestRegionCount[it.region] ?: 0) + 1
        }
    }

    private fun registerGame(mapName: String, playWorld: World, playerCount: Int) {
        state = GameState.READYING

        world = playWorld
        worldBorder = world.worldBorder

        world.setGameRule(GameRules.SPAWN_PHANTOMS, false)
        world.setGameRule(GameRules.ADVANCE_TIME, false)
        world.setGameRule(GameRules.ADVANCE_WEATHER, false)
        world.setGameRule(GameRules.SPAWN_MOBS, false)
        world.setGameRule(GameRules.SPAWN_PATROLS, false)
        world.setGameRule(GameRules.SPAWN_WANDERING_TRADERS, false)
        world.setGameRule(GameRules.IMMEDIATE_RESPAWN, true)
        world.setGameRule(GameRules.FIRE_SPREAD_RADIUS_AROUND_PLAYER, 32)

        when (mapName) {
            "school" -> {
                center = City.getCenter(playWorld)
                worldDefaultWeather = City.getWorldDefaultWeather()
                worldBorder.size = City.getWorldBorderSize()
                chestCount = City.getChestCount()
                chestLocations = City.getChestLocations(playWorld)
                regions = City.getRegions(playWorld)
                chestTables = City.getChestTables()
                mapColors = City.getMapColors()
                customRecipes = City.getCustomRecipes()
                customRecipeSets = City.getCustomRecipeSets()
                minY = City.getMinY()
            }
        }

        regions.forEach { region ->
            region.gameWeather = worldDefaultWeather
        }

        /* 그냥 전체 아이템 저장 하는 방식으로
        val armors = mutableListOf<CustomEquipment>()
        CustomRecipe.entries.map { it.result }.plus(
            chestTables.values.flatMap { table -> table.loots.map { loot -> loot.item } }
        )
            .forEach { item ->
                if (item in CustomEquipment.entries.map { armor -> armor.item }) {
                    armors.add(CustomEquipment.entries.find { it.item == item }!!)
                }
            }
         */

        // 드랍 전용 아이템 계산: 레시피/상자에 없는 장비 아이템들
        val recipeItems = customRecipes.map { it.result }
        val chestItems = chestTables.values.flatMap { table -> table.loots.map { it.item } }
        val equipmentItems = CustomEquipment.entries.map { it.item }
        dropItems = equipmentItems.filter { it !in recipeItems && it !in chestItems }

        customEquipments = CustomEquipment.entries

        worldBorder.center = center
        worldBorder.damageAmount = 1.0
        worldBorder.damageBuffer = 0.0
        worldBorder.warningTimeTicks = 0
        worldBorder.warningDistance = 5

        val clampedPlayerCount = playerCount.coerceIn(4..20)
        val sizeDecrease = worldBorderSize * (0.4 - clampedPlayerCount * 0.02)
        worldBorder.size = worldBorderSize - sizeDecrease
        val randomVector = Vector(
            Random.nextDouble(-sizeDecrease / 2, sizeDecrease / 2),
            0.0,
            Random.nextDouble(-sizeDecrease / 2, sizeDecrease / 2)
        )
        worldBorder.center = worldBorderCenter.clone().add(randomVector)

        targetWorldBorderCenter = worldBorderCenter.clone()
        targetWorldBorderSize = worldBorderSize
        updateWorldTime()
    }

    private fun registerMarmotte(players: MutableList<Player>) {
        players.forEach { player ->
            val marmotte = DataManager.addMarmotte(player, this)
            marmottes.add(marmotte)
        }
    }

    private fun registerEvent() {
        tasks.add(GameTask(
            game = this,
            task = FightStart(this),
            displayName = "무적 해제",
            tick = if (NO_READY_TIME) 30 else FIRST_DAY_TICKS,
            priority = 1,
            canRestart = false,
            isVisible = true
        ))
    }

    internal fun trackNightEntity(entity: Entity) {
        entities.add(entity)
        nightEntities.add(entity)
    }

    internal fun untrackNightEntity(entity: Entity) {
        nightEntities.remove(entity)
        entities.remove(entity)
    }

    internal fun clearNightEntities() {
        nightEntities.toList().forEach { entity ->
            if (!entity.isDead && entity.isValid) {
                entity.world.spawnParticle(Particle.SMOKE, entity.location, 10, 0.5, 0.5, 0.5, 0.1)
                entity.world.playSound(entity.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.1f)
                entity.remove()
            }
            entities.remove(entity)
        }
        nightEntities.clear()
    }

    private fun tickDayNightCycle() {
        currentPhaseTick--
        if (currentPhaseTick <= 0) {
            if (phase == GamePhase.DAY) {
                startNight()
            } else {
                startNextDay()
            }
            return
        }

        updateWorldTime()
    }

    internal fun skipToNextPhase(): Boolean {
        if (state != GameState.PLAYING) return false

        if (phase == GamePhase.DAY) {
            completeCurrentBorderDecrease()
            startNight()
        } else {
            startNextDay()
        }

        return true
    }

    private fun completeCurrentBorderDecrease() {
        worldBorderMoveTask?.cancel()
        worldBorderMoveTask = null
        worldBorder.center = targetWorldBorderCenter.clone()
        worldBorder.size = targetWorldBorderSize
    }

    private fun startNight() {
        phase = GamePhase.NIGHT
        setPhaseTick(NORMAL_PHASE_TICKS)
        WorldBorderDecrease.planNext(this)
        WorldBorderDecrease.announceNightPreview(this, DAY_BORDER_DECREASE_TICKS)
        CreateNightMonsterWave(this, day).run()
        updateWorldTime()
    }

    private fun startNextDay() {
        clearNightEntities()
        day++
        phase = GamePhase.DAY
        setPhaseTick(NORMAL_PHASE_TICKS)
        WorldBorderDecrease.announceDayStart(this, DAY_BORDER_DECREASE_TICKS)
        WorldBorderDecrease(this, DAY_BORDER_DECREASE_TICKS).run()
        updateWorldTime()
    }

    private fun setPhaseTick(tick: Int) {
        phaseMaxTick = tick
        currentPhaseTick = tick
    }

    private fun updateWorldTime() {
        val total = phaseMaxTick.coerceAtLeast(1)
        val progress = 1.0 - currentPhaseTick.toDouble() / total
        worldTime = (
            phase.startWorldTime + ((phase.endWorldTime - phase.startWorldTime) * progress)
            ).toLong()
        if (worldTime >= 24000L) worldTime -= 24000L
        world.time = worldTime
    }

    fun isInRegion(region: Region, spot: Location): Boolean {
        if (spot.world != region.center.world) return false
        val dx = abs(spot.x - region.center.x)
        val dz = abs(spot.z - region.center.z)
        return dx <= region.width / 2 && dz <= region.height / 2
    }

    fun isInWorldBorder(spot: Location, checkTarget: Boolean): Boolean {
        val center = if (checkTarget) targetWorldBorderCenter else worldBorderCenter
        val size = if (checkTarget) targetWorldBorderSize else worldBorderSize

        val dx = abs(spot.x - center.x)
        val dz = abs(spot.z - center.z)
        return dx <= size / 2 && dz <= size / 2
    }

    fun remove() {
        tickTask.cancel()
        worldBorderMoveTask?.cancel()

        entities.forEach {
            it.remove()
        }

        objects.toList().forEach {
            it.onRemove()
        }

        chests.toList().forEach { it.remove() }

        customEquipments.forEach {
            it.system.onRemove()
        }

        marmottes.toList().forEach { it.remove() }

        plugin.games.remove(this)
    }
}