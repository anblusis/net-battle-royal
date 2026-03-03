package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.persistence.PersistentDataType

private val bannedDropItems = listOf(
    Material.IRON_BLOCK,
    Material.GOLD_BLOCK,
    Material.DIAMOND_BLOCK,
    Material.EMERALD_BLOCK,
    Material.LAPIS_BLOCK,
    Material.REDSTONE_BLOCK,
    Material.COAL_BLOCK,
    Material.NETHERITE_BLOCK,
    Material.REDSTONE,
    Material.GLOWSTONE_DUST,
    Material.SHULKER_BOX,
    Material.BOOK
)

fun itemSpawn(listener: EventManager, event: ItemSpawnEvent) {
    val item = event.entity
    val loc = item.location
    if (!plugin.games.any { game -> loc.world == game.world && game.isInWorldBorder(loc, false) }) return

    val stack = item.itemStack
    if (stack.type !in bannedDropItems) return

    if (item.thrower == null) event.isCancelled = true
}

