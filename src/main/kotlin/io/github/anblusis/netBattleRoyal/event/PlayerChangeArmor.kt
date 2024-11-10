package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent) {
    if (DataManager.getMarmotte(event.player) == null) return
    val game = DataManager.getMarmotte(event.player)!!.game

    game.customEquipments.filter { it.itemSlot.isArmor }
        .find { it.item.equalsDisplayName(event.oldItem) }?.system?.onDisable(event.player)

    game.customEquipments.filter { it.itemSlot.isArmor }
        .find { it.item.equalsDisplayName(event.newItem) }?.system?.onEnable(event.player)
}
