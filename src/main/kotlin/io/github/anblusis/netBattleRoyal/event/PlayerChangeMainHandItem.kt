package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.meta.Damageable

fun playerChangeMainHandItem(listener: EventManager, event: PlayerItemHeldEvent) : EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL

    val player = event.player
    val game = DataManager.getMarmotte(player)!!.game

    player.inventory.getItem(event.previousSlot)?.clone()?.let { previousItem ->
        try {
            previousItem.itemMeta = (previousItem.itemMeta as Damageable).apply {
                damage = 0
            }
        } catch (_: ClassCastException) {}

        game.customEquipments.filter { it.itemSlot == EquipmentSlot.HAND }.find { it.item.isSimilar(previousItem) }?.let { equipment ->
            equipment.system.onDisable(event.player, equipment)
        }
    }

    player.inventory.getItem(event.newSlot)?.clone()?.let { newItem ->
        try {
            newItem.itemMeta = (newItem.itemMeta as Damageable).apply {
                damage = 0
            }
        } catch (_: ClassCastException) {}

        game.customEquipments.filter { it.itemSlot == EquipmentSlot.HAND }.find { it.item.isSimilar(newItem) }?.let { equipment ->
            equipment.system.onEnable(event.player, equipment)
        }
    }

    return EventResult.CHANGE_MAIN_HAND_ITEM
}
