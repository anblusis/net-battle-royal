package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.data.EventResult
import net.kyori.adventure.text.Component.text
import org.bukkit.entity.Player
import org.bukkit.event.entity.ExpBottleEvent

fun expBottleHit(listener: EventManager, event: ExpBottleEvent): EventResult {
    if (event.entity.customName() != BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName()) return EventResult.FAIL

    event.experience *= 15
    return EventResult.EXP_BOTTLE_HIT
}
