package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent): EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    val game = DataManager.getMarmotte(event.player)!!.game

    game.customEquipments.filter { it.itemSlot.isArmor }
        .find { it.item.equalsDisplayName(event.oldItem) }?.system?.onDisable(event.player)

    game.customEquipments.filter { it.itemSlot.isArmor }
        .find { it.item.equalsDisplayName(event.newItem) }?.system?.onEnable(event.player)

    return EventResult.CHANGE_ARMOR
}
