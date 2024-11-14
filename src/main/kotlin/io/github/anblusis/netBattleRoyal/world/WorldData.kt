package io.github.anblusis.netBattleRoyal.world

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.GameWeather
import org.bukkit.Location
import org.bukkit.World

internal interface WorldData {
    fun getCenter(world: World): Location

    fun getWorldDefaultWeather(): GameWeather

    fun getWorldBorderSize(): Double

    fun getChestCount(): Int

    fun getChestLocations(world: World): List<ChestData>

    fun getRegions(world: World): List<Region>

    fun getChestTables(): HashMap<ChestType, ChestLootTable>

    fun getMapColors(): List<Byte>

    fun getCustomRecipes(): List<CustomRecipe>

    fun getCustomRecipeSets(): List<CustomRecipeSet>
}