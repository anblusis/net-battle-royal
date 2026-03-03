package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import org.bukkit.event.inventory.PrepareAnvilEvent

fun prepareAnvil(listener: EventManager, event: PrepareAnvilEvent) {
    val leftItem = event.inventory.firstItem ?: return
    val result = event.result ?: return

    if (!leftItem.hasItemMeta() || !leftItem.itemMeta.hasDisplayName()) return

    if (!leftItem.equalsDisplayName(result) ) {
        result.itemMeta = result.itemMeta.apply {
            displayName(leftItem.itemMeta.displayName())
        }
        event.result = result
    }
}
