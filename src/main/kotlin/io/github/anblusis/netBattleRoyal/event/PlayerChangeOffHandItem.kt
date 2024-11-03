package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import net.kyori.adventure.text.Component.text
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

fun playerChangeOffHandItem(player: Player, previousItem: ItemStack?, newItem: ItemStack?): EventResult {
    val game = DataManager.getMarmotte(player)!!.game

    if (previousItem != null) {
        game.customEquipments.filter { it.itemSlot == EquipmentSlot.OFF_HAND }
            .find { it.item.equalsDisplayName(previousItem) }?.system?.onDisable(player)
    }

    if (newItem != null) {
        game.customEquipments.filter { it.itemSlot == EquipmentSlot.OFF_HAND }
            .find { it.item.equalsDisplayName(newItem) }?.system?.onEnable(player)
    }

    return EventResult.CHANGE_MAIN_HAND_ITEM
}
