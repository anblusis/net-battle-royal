package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.event.player.PlayerQuitEvent

fun playerQuit(listener: EventManager, event: PlayerQuitEvent) {
    DataManager.getMarmotte(event.player)?.remove()
}
