package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.GameMode
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryOpenEvent

fun playerOpenChest(listener: EventManager, event: InventoryOpenEvent) {
    if (!event.player.isValid || event.player.gameMode == GameMode.SPECTATOR)  return
    val marmotte = DataManager.getMarmotte(event.player as Player)
    marmotte?.game?.chests?.forEach {
        if (it.location == (event.inventory.holder as Chest).location) {
            it.open()
        }
    }
}
