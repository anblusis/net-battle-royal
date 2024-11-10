package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import net.kyori.adventure.text.Component.text
import org.bukkit.entity.Player
import org.bukkit.event.entity.ExpBottleEvent

fun expBottleHit(listener: EventManager, event: ExpBottleEvent) {
    if (event.entity.customName() != BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName()) return

    event.experience *= 15
}
