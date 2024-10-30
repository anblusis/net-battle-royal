package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.inventory.meta.Damageable

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent) : EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    val game = DataManager.getMarmotte(event.player)!!.game

    game.customEquipments.filter { it.itemSlot.isArmor }.find { it.item.equalsDisplayName(event.oldItem) }?.let { armor ->
        armor.system.onDisable(event.player, armor)
    }

    game.customEquipments.filter { it.itemSlot.isArmor }.find { it.item.equalsDisplayName(event.newItem) }?.let { armor ->
        armor.system.onEnable(event.player, armor)
    }

    return EventResult.CHANGE_ARMOR
}
