package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerQuitEvent

fun playerLaunchSuperExpBottle (listener: EventManager, event: PlayerLaunchProjectileEvent) : EventResult {
    if (event.itemStack != BattleRoyalItemData.SUPER_EXP_BOTTLE.item) return EventResult.FAIL

    event.projectile.customName(BattleRoyalItemData.SUPER_EXP_BOTTLE.item.displayName())
    return EventResult.PLAYER_LAUNCH_SUPER_EXP_BOTTLE
}
