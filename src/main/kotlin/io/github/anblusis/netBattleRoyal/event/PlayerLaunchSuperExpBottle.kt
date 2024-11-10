package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData

fun playerLaunchSuperExpBottle(listener: EventManager, event: PlayerLaunchProjectileEvent) {
    event.projectile.customName(BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName())
}
