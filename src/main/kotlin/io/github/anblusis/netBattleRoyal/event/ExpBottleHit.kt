package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.GameState
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ExpBottleEvent

fun expBottleHit(listener: EventManager, event: ExpBottleEvent) : EventResult {
    if (event.entity.customName() != BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName()) return EventResult.FAIL

    event.experience *= 10
    (event.entity.shooter as? Player)?.sendMessage("§a+${event.experience} exp")
    return EventResult.EXP_BOTTLE_HIT
}
