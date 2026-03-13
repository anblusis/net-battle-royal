package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.enchantValue
import io.github.anblusis.netBattleRoyal.data.randomEnchantBook
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

fun playerInteractWithRandomEnchantBook(listener: EventManager, event: PlayerInteractEvent) {
    if (event.action !in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)) return
    if (DataManager.getMarmotte(event.player) == null) return

    val item = event.item ?: return
    if (item.type != Material.BOOK) return
    if (randomEnchantBook.content() !in item.displayName().toString()) return

    val level = item.enchantValue
    if (level <= 0) return

    event.isCancelled = true

    val player = event.player
    consumeOneInUsedHand(event)

    val enchantedBook = ItemStack(Material.BOOK).enchantWithLevels(level, false, java.util.Random())
    val remain = player.inventory.addItem(enchantedBook)
    remain.values.forEach { drop ->
        player.world.dropItemNaturally(player.location, drop)
    }

    player.playSound(player.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.7f, 1.2f)
}

private fun consumeOneInUsedHand(event: PlayerInteractEvent) {
    val player = event.player
    val current = event.item ?: return

    if (current.amount > 1) {
        current.amount -= 1
        return
    }

    when (event.hand) {
        EquipmentSlot.HAND -> player.inventory.setItemInMainHand(ItemStack(Material.AIR))
        EquipmentSlot.OFF_HAND -> player.inventory.setItemInOffHand(ItemStack(Material.AIR))
        else -> {}
    }
}
