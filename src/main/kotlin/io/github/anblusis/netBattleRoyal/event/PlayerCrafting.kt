package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomRecipe
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

internal fun playerPrepareCrafting(listener: EventManager, event: PrepareItemCraftEvent) {
    val marmotte = DataManager.getMarmotte(event.view.player as Player)

    if (isRandomEnchantBookMaterialMatrix(event.inventory.matrix)) {
        event.inventory.result = CustomRecipe.RANDOM_ENCHANT_BOOK.resultFunction(event.inventory.matrix)
        return
    }

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
}

private fun isRandomEnchantBookMaterialMatrix(matrix: Array<ItemStack?>): Boolean {
    val items = matrix.filterNotNull()
    if (items.isEmpty()) return false

    val bookCount = items.count { item -> item.isSimilar(ItemStack(Material.BOOK)) }
    val goldCount = items.count{ item -> item.type == Material.GOLD_INGOT }
    val hasOther = items.any { item -> !item.isSimilar(ItemStack(Material.BOOK)) && item.type != Material.GOLD_INGOT }

    return !hasOther && bookCount == 1 && goldCount >= 1
}

internal fun inventoryClick(listener: EventManager, event: InventoryClickEvent) {
    if (event.slotType != InventoryType.SlotType.RESULT) return
    val inventory = event.inventory as? CraftingInventory ?: return
    val result = event.currentItem ?: return

    if (result.equalsDisplayName(BattleRoyalItemData.RANDOM_ENCHANT_BOOK.item)) {
        val matrix = inventory.matrix
        val player = event.whoClicked as Player

        event.isCancelled = true

        if (event.isShiftClick) {
            val materials = matrix.filterNotNull()
            var craftAmount = materials.minOfOrNull { it.amount } ?: 0

            if (craftAmount <= 0) return

            val finalResult = result.clone()
            finalResult.amount = craftAmount

            val leftover = player.inventory.addItem(finalResult)
            leftover.values.forEach { item ->
                craftAmount -= item.amount
            }

            for (i in matrix.indices) {
                val item = matrix[i] ?: continue
                item.amount -= craftAmount
            }
        } else {
            player.setItemOnCursor(result)
            matrix.forEach { item ->
                if (item != null) item.amount--
            }
        }
        inventory.matrix = matrix
    }
}

internal fun playerCraftItem(listener: EventManager, event: CraftItemEvent) {

}
