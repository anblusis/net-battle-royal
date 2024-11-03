package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import org.bukkit.event.player.PlayerQuitEvent

fun playerQuit(listener: EventManager, event: PlayerQuitEvent): EventResult {
    DataManager.getMarmotte(event.player)?.remove()
    return EventResult.PLAYER_QUIT
}
