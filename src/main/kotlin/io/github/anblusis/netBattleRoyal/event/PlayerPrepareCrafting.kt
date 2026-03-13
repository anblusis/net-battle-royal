package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomRecipe
import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

fun playerPrepareCrafting(listener: EventManager, event: PrepareItemCraftEvent) {
    val marmotte = DataManager.getMarmotte(event.view.player as Player)

    val recipe = event.recipe
    if (recipe != null) {
        val customRecipe = CustomRecipe.entries.find {
            it.toBukkitRecipe().result == recipe.result
        } ?: return

        if (marmotte == null || !marmotte.game.customRecipes.contains(customRecipe)) {
            event.inventory.result = null
            return
        }

        event.inventory.result = customRecipe.resultFunction(event.inventory.matrix)
        return
    }

    // 레시피 북에는 1책+1금만 표시하고, 실제 제작은 1책+금 n개까지 허용한다.
    if (marmotte == null || CustomRecipe.RANDOM_ENCHANT_BOOK !in marmotte.game.customRecipes) return
    if (!isRandomEnchantBookMaterialMatrix(event.inventory.matrix)) return

    event.inventory.result = CustomRecipe.RANDOM_ENCHANT_BOOK.resultFunction(event.inventory.matrix)
}

private fun isRandomEnchantBookMaterialMatrix(matrix: Array<ItemStack?>): Boolean {
    val items = matrix.filterNotNull()
    if (items.isEmpty()) return false

    val bookCount = items.sumOf { item -> if (item.type == Material.BOOK) item.amount else 0 }
    val goldCount = items.sumOf { item -> if (item.type == Material.GOLD_INGOT) item.amount else 0 }
    val hasOther = items.any { item -> item.type != Material.BOOK && item.type != Material.GOLD_INGOT }

    return !hasOther && bookCount == 1 && goldCount >= 1
}