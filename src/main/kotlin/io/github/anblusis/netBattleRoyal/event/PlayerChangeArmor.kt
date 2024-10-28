package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.meta.Damageable

fun playerChangeArmor(listener: EventManager, event: PlayerArmorChangeEvent) : EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    val game = DataManager.getMarmotte(event.player)!!.game

    if (event.oldItem.type != Material.AIR) {
        val oldItem = event.oldItem.clone().apply {
            itemMeta = (itemMeta as Damageable).apply {
                damage = 0
            }
        }
        game.customArmors.find { it.item.isSimilar(oldItem) }?.let { armor ->
            armor.system.onArmorUnequip(event.player, armor)
        }
    }

    if (event.newItem.type != Material.AIR) {
        val newItem = event.newItem.clone().apply {
            itemMeta = (itemMeta as Damageable).apply {
                damage = 0
            }
        }
        game.customArmors.find { it.item.isSimilar(newItem) }?.let { armor ->
            armor.system.onArmorEquip(event.player, armor)
        }
    }
    return EventResult.CHANGE_ARMOR
}
