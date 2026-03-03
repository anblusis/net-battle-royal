package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor

fun playerElytraBoost(listener: EventManager, event: PlayerElytraBoostEvent) {
    if (event.player.inventory.chestplate!!.equalsDisplayName(CustomEquipment.KNIGHT_CAPE.item)) {
        event.isCancelled = true
    }
}
