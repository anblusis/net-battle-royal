package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

fun playerAttackWithHealthSteal(listener: EventManager, event: EntityDamageByEntityEvent) : EventResult {
    DataManager.getMarmotte(event.damager as Player)?.let {
        if (it.game.state == GameState.PLAYING) {
            if (event.finalDamage != 0.0) {
                it.stat[CustomAttribute.HEALTH_STEAL]?.let { value ->
                    (event.damager as Player).health += event.finalDamage * value / (value + 10)
                }
            }
            return EventResult.PLAYER_ATTACK_WITH_HEALTH_STEAL
        }
    }
    return EventResult.FAIL
}
