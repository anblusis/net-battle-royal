package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

fun playerChangeOffHandItem(player: Player, previousItem: ItemStack?, newItem: ItemStack?) : EventResult {
    val game = DataManager.getMarmotte(player)!!.game

    if (previousItem != null) {
        game.customEquipments.filter { it.itemSlot == EquipmentSlot.OFF_HAND }.find { it.item.equalsDisplayName(previousItem) }?.let { equipment ->
            equipment.system.onDisable(player, equipment)
        }
    }

    if (newItem != null) {
        game.customEquipments.filter { it.itemSlot == EquipmentSlot.OFF_HAND }.find { it.item.equalsDisplayName(newItem) }?.let { equipment ->
            equipment.system.onEnable(player, equipment)
        }
    }

    return EventResult.CHANGE_MAIN_HAND_ITEM
}
