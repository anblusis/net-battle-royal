package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent

fun playerDamaged(listener: EventManager, event: EntityDamageEvent): EventResult {
    DataManager.getMarmotte(event.entity as Player)?.game?.let {
        if (it.state == GameState.READYING) {
            event.isCancelled = true
            return EventResult.PLAYER_DAMAGED
        }
    }
    return EventResult.FAIL
}
