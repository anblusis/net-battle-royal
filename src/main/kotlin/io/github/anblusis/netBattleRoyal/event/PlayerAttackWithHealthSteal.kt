package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.CustomAttribute
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

fun playerAttackWithHealthSteal(listener: EventManager, event: EntityDamageByEntityEvent) {
    val marmotte = DataManager.getMarmotte(event.damager as Player)?: return

    if (marmotte.game.state != GameState.PLAYING) return

    if (event.finalDamage != 0.0) {
            val damager = marmotte.player
            damager.health = (damager.health + event.finalDamage * marmotte.stat[CustomAttribute.HEALTH_STEAL]!!.coerceAtMost(1.0)).coerceAtMost(damager.getAttribute(
                Attribute.MAX_HEALTH)!!.value)
    }
}
