package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomAttribute
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

fun playerAttackWithDefensePenetration(listener: EventManager, event: EntityDamageByEntityEvent) {
    DataManager.getMarmotte(event.damager as Player)?.let {
        if (it.game.state == GameState.PLAYING) {
            if (event.finalDamage != 0.0) {
                val protectedDamage = event.damage - event.finalDamage
                val reducedDamage =
                    protectedDamage * it.stat[CustomAttribute.DEFENSE_PENETRATION]!!.coerceAtMost(100.0) * 0.01
                event.damage += reducedDamage
            }
        }
    }
}
