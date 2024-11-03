package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent

fun playerBreakBlock(listener: EventManager, event: BlockBreakEvent): EventResult {
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL
    if (event.block.type !in listOf(
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.LAPIS_BLOCK,
            Material.REDSTONE_BLOCK,
            Material.COAL_BLOCK
        )
    ) return EventResult.FAIL

    event.block.type = Material.AIR
    return EventResult.DELETE_BREAK_ITEM
}
