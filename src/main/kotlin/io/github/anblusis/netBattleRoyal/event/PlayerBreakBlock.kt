package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent

fun playerBreakBlock(listener: EventManager, event: BlockBreakEvent) {
    if (DataManager.getMarmotte(event.player) == null) return
    if (event.block.type !in listOf(
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.LAPIS_BLOCK,
            Material.REDSTONE_BLOCK,
            Material.COAL_BLOCK
        )
    ) return

    event.block.type = Material.AIR
}
