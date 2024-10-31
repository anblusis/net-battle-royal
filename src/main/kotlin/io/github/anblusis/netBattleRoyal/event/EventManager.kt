package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.data.transcendBook
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ExpBottleEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.ItemStack

object EventManager : Listener {

    fun register() {
        try {
            plugin.server.pluginManager.registerEvents(this, plugin)
        } catch (e: Exception) {
            plugin.logger.severe("Failed to register event listener: ${e.message}")
            e.printStackTrace()
        }
    }

    @EventHandler
    private fun onPlayerQuit(event: PlayerQuitEvent) { playerQuit(this, event) }

    @EventHandler
    private fun onPlayerInteract(event: PlayerInteractEvent) {
        when {
            event.item == null -> return
            event.item!!.isSimilar(BattleRoyalItemData.MAGIC_STICK.item) -> playerInteractWithMagicStick(this, event)
            transcendBook.content() in event.item!!.displayName().toString() -> playerInteractWithTranscendBook(this, event)
            else -> return
        }
    }

    @EventHandler
    private fun onPlayerInventoryOpen(event: InventoryOpenEvent) {
        if (event.player !is Player) return
        if (event.inventory.holder is Chest) {
            playerOpenChest(this, event)
        }
    }

    @EventHandler
    private fun onPlayerDamaged(event: EntityDamageEvent) {
        if (event.entity is Player) playerDamaged(this, event)
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onPlayerAttackWithHealthSteal(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            playerAttackWithHealthSteal(this, event)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private fun onPlayerAttackWithDefensePenetration(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            playerAttackWithDefensePenetration(this, event)
        }
    }

    @EventHandler
    private fun onPlayerPrepareCrafting(event: PrepareItemCraftEvent) {
        if (event.view.player !is Player) return
        playerPrepareCrafting(this, event)
    }

    @EventHandler
    private fun onPlayerBreakBlock(event: BlockBreakEvent) {
        playerBreakBlock(this, event)
    }

    @EventHandler
    private fun onPlayerChangeArmor(event: PlayerArmorChangeEvent) {
        playerChangeArmor(this, event)
    }

    @EventHandler
    fun onExpBottleTHit(event: ExpBottleEvent) {
        expBottleHit(this, event)
    }

    @EventHandler
    fun onPlayerLaunchProjectile(event: PlayerLaunchProjectileEvent) {
        playerLaunchSuperExpBottle(this, event)
    }
}