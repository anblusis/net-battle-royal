package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerQuitEvent

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent) : EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    val game = DataManager.getMarmotte(event.player)!!.game

    game.customArmors.find { it.item == event.oldItem }?.let { armor ->
        armor.system.onArmorUnequip(event.player, armor)
    }
    game.customArmors.find { it.item == event.newItem }?.let { armor ->
        armor.system.onArmorEquip(event.player, armor)
    }
    return EventResult.CHANGE_ARMOR
}
