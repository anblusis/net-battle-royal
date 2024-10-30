package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.*
import org.bukkit.Material
import org.bukkit.inventory.meta.Damageable

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent) : EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    val game = DataManager.getMarmotte(event.player)!!.game

    if (event.oldItem.type != Material.AIR) {
        val oldItem = event.oldItem.clone().apply {
            try {
                itemMeta = (itemMeta as Damageable).apply {
                    damage = 0
                }
            } catch (_: ClassCastException) {}
        }
        game.customEquipments.filter { it.itemSlot.isArmor }.find { it.item.isSimilar(oldItem) }?.let { armor ->
            armor.system.onDisable(event.player, armor)
        }
    }

    if (event.newItem.type != Material.AIR) {
        val newItem = event.newItem.clone().apply {
            try {
                itemMeta = (itemMeta as Damageable).apply {
                    damage = 0
                }
            } catch (_: ClassCastException) {}
        }
        game.customEquipments.filter { it.itemSlot.isArmor}.find { it.item.isSimilar(newItem) }?.let { armor ->
            armor.system.onEnable(event.player, armor)
        }
    }
    return EventResult.CHANGE_ARMOR
}
