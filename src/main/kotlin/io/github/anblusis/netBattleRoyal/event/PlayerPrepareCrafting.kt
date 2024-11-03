package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomRecipe
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import org.bukkit.entity.Player
import org.bukkit.event.inventory.PrepareItemCraftEvent

fun playerPrepareCrafting(listener: EventManager, event: PrepareItemCraftEvent): EventResult {
    val recipe = event.recipe ?: return EventResult.FAIL
    val customRecipe = CustomRecipe.values().find { it.toBukkitRecipe() == recipe } ?: return EventResult.FAIL
    val marmotte = DataManager.getMarmotte(event.view.player as Player)
    if (marmotte == null || !marmotte.game.customRecipes.contains(customRecipe)) {
        event.inventory.result = null
        return EventResult.CANCEL_CRAFTING
    } else return EventResult.FAIL
}