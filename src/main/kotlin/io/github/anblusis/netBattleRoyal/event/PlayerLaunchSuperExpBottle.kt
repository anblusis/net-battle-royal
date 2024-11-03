package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.data.EventResult

fun playerLaunchSuperExpBottle(listener: EventManager, event: PlayerLaunchProjectileEvent): EventResult {
    if (!event.itemStack.isSimilar(BattleRoyalItemData.SUPER_EXP_BOTTLE.item)) return EventResult.FAIL

    event.projectile.customName(BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName())
    return EventResult.PLAYER_LAUNCH_SUPER_EXP_BOTTLE
}
