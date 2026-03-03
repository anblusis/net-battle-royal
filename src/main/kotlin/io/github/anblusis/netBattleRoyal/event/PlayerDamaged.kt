package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent

fun playerDamaged(listener: EventManager, event: EntityDamageEvent) {
    DataManager.getMarmotte(event.entity as Player)?.let { marmotte ->
        if (marmotte.game.state == GameState.READYING) {
            event.isCancelled = true
            return
        }
    }
}
