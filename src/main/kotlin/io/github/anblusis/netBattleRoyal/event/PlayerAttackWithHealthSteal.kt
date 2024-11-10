package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomAttribute
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

fun playerAttackWithHealthSteal(listener: EventManager, event: EntityDamageByEntityEvent) {
    DataManager.getMarmotte(event.damager as Player)?.let {
        if (it.game.state == GameState.PLAYING) {
            if (event.finalDamage != 0.0) {
                it.stat[CustomAttribute.HEALTH_STEAL]?.let { value ->
                    (event.damager as Player).health += event.finalDamage * value / (value + 10)
                }
            }
        }
    }
}
