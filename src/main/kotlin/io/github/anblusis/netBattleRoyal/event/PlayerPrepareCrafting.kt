package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomRecipe
import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.entity.Player
import org.bukkit.event.inventory.PrepareItemCraftEvent

fun playerPrepareCrafting(listener: EventManager, event: PrepareItemCraftEvent) {
    val recipe = event.recipe ?: return
    val customRecipe = CustomRecipe.values().find {
        it.toBukkitRecipe().result == recipe.result
    } ?: return
    val marmotte = DataManager.getMarmotte(event.view.player as Player)
    if (marmotte == null || !marmotte.game.customRecipes.contains(customRecipe)) {
        event.inventory.result = null
    } else {
        customRecipe.resultFunction(event.inventory.matrix).let {
            event.inventory.result = it
        }
    }
}