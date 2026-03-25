package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.enchantValue
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

fun playerInteractWithRandomEnchantBook(listener: EventManager, event: PlayerInteractEvent) {
    if (event.action !in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)) return
    if (DataManager.getMarmotte(event.player) == null) return

    val item = event.item ?: return

    val player = event.player

    val enchantedBook = ItemStack(Material.BOOK).enchantWithLevels(item.enchantValue, false, java.util.Random())
    val remain = player.inventory.addItem(enchantedBook)
    remain.values.forEach { drop ->
        player.world.dropItemNaturally(player.location, drop)
    }

    player.playSound(player.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.7f, 1.2f)

    if (player.gameMode != GameMode.CREATIVE) item.amount--
}