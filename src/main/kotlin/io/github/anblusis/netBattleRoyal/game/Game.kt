package io.github.anblusis.netBattleRoyal.game

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.event.FightStart
import io.github.anblusis.netBattleRoyal.inv.InvManager
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.world.City
import io.github.monun.invfx.frame.InvFrame
import io.github.monun.tap.task.TickerTask
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldBorder
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.awt.Color
import kotlin.math.abs
import kotlin.random.Random

class Game(
    mapName: String,
    playWorld: World,
    val mode: Int,
    players: MutableList<Player>
) {
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
    private lateinit var chestLocations: List<ChestData>
    private val tickTask: TickerTask
    internal val mainInv: InvFrame
    internal val itemInv: InvFrame
    internal val mapItem: ItemStack

    private var worldTime: Long = 12000L
    private var chestCount: Int = 0
    internal var targetWorldBorderSize: Double = 0.0
    internal val worldBorderDots: HashMap<Pair<Int, Int>, Color?> = hashMapOf()
    internal val chestRegionCount: HashMap<Region?, Int> = hashMapOf()
    internal val tasks: MutableList<GameTask> = mutableListOf()
    internal val entities: MutableList<Entity> = mutableListOf()
    internal val marmottes: MutableList<Marmotte> = mutableListOf()
    internal val objects: MutableList<GameObject> = mutableListOf()

    val worldBorderCenter
        get() = worldBorder.center

    val worldBorderSize
        get() = worldBorder.size

    init {
        registerGame(mapName, playWorld)
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

        if (state == GameState.PLAYING) worldTime += 3L
        if (worldTime >= 24000L) worldTime = 0L
        world.time = worldTime
    }

    private fun setChests() {
        chests = mutableListOf()
        val leftChestLocations = mutableListOf<ChestData>()
        leftChestLocations.addAll(chestLocations)
        leftChestLocations.shuffle()

        run {
            repeat(chestCount) {
                if (leftChestLocations.isEmpty()) return@run

                val type = when (leftChestLocations.first().type) {
                    ChestType.NORMAL -> if (Random.nextDouble() <= 0.08) ChestType.RARE else ChestType.NORMAL
                    ChestType.RARE -> if (Random.nextDouble() <= 0.2) ChestType.NORMAL else ChestType.RARE
                    ChestType.EPIC -> if (Random.nextDouble() <= 0.5) ChestType.RARE else ChestType.EPIC
                }

                val chest = RoyalChest(this, leftChestLocations.first().apply { this.type = type }, chestTables[type]!!)
                chests.add(chest)
                leftChestLocations.removeFirst()
            }
        }

        chests.forEach {
            chestRegionCount[it.region] = (chestRegionCount[it.region] ?: 0) + 1
        }
    }

    private fun registerGame(mapName: String, playWorld: World) {
        state = GameState.READYING

        world = playWorld
        worldBorder = world.worldBorder

        world.setGameRule(GameRule.DO_INSOMNIA, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false)
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)

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
            }
        }

        regions.forEach { region ->
            region.gameWeather = worldDefaultWeather
        }

        val armors = mutableListOf<CustomEquipment>()
        CustomRecipe.values().map { it.result }.plus(
            chestTables.values
                .map { table -> table.loots.map { loot -> loot.item } }.flatten()
        )
            .forEach { item ->
                if (item in CustomEquipment.values().map { armor -> armor.item }) {
                    armors.add(CustomEquipment.values().find { it.item == item }!!)
                }
            }
        customEquipments = armors

        worldBorder.center = center
        worldBorder.damageAmount = 1.0
        worldBorder.damageBuffer = 0.0
        worldBorder.warningTime = 0
        worldBorder.warningDistance = 5
        targetWorldBorderCenter = worldBorderCenter
        targetWorldBorderSize = worldBorderSize
    }

    private fun registerMarmotte(players: MutableList<Player>) {
        players.forEach { player ->
            val marmotte = DataManager.addMarmotte(player, this)
            marmottes.add(marmotte)
        }
    }

    private fun registerEvent() {
        tasks.add(GameTask(this, FightStart(this), "무적 해제", 3600, 3600, 1, false))
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