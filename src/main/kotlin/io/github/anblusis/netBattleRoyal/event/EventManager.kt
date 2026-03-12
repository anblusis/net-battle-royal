package io.github.anblusis.netBattleRoyal.event

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.data.transcendBook
import io.github.anblusis.netBattleRoyal.game.event.BossType
import io.github.anblusis.netBattleRoyal.game.event.getNightMonsterBonusExp
import io.github.anblusis.netBattleRoyal.game.event.isNightMonster
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.NamespacedKey
import org.bukkit.block.Chest
import org.bukkit.entity.Animals
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ExpBottleEvent
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.PortalCreateEvent
import org.bukkit.persistence.PersistentDataType

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
    private fun onPlayerQuit(event: PlayerQuitEvent) {
        playerQuit(this, event)
    }

    @EventHandler
    private fun onPlayerInteract(event: PlayerInteractEvent) {
        when {
            event.item == null -> return
            event.item!!.isSimilar(BattleRoyalItemData.MAGIC_STICK.item) -> playerInteractWithMagicStick(this, event)
            event.item!!.isSimilar(BattleRoyalItemData.SIGNAL_FIREWORK.item) -> playerUseSignalFirework(this, event)
            transcendBook.content() in event.item!!.displayName().toString() -> playerInteractWithTranscendBook(
                this,
                event
            )

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
    private fun onPlayerChangeArmor(event: PlayerArmorChangeEvent) {
        playerChangeArmor(this, event)
    }

    @EventHandler
    fun onExpBottleTHit(event: ExpBottleEvent) {
        expBottleHit(this, event)
    }

    @EventHandler
    fun onPlayerLaunchProjectile(event: PlayerLaunchProjectileEvent) {
        if (event.itemStack.isSimilar(BattleRoyalItemData.SUPER_EXP_BOTTLE.item))
            playerLaunchSuperExpBottle(this, event)
    }

    @EventHandler
    fun onShootArrow(event: EntityShootBowEvent) {
        if (event.entity is Player) playerShootArrow(this, event)
        else if (event.entity.persistentDataContainer.has(NamespacedKey(plugin, "boss"), PersistentDataType.BYTE) &&
            event.entity.persistentDataContainer.get(NamespacedKey(plugin, "boss_type"), PersistentDataType.STRING) == BossType.SKELETON_KNIGHT.name)
            skeletonBossShootArrow(this, event)
    }

    /*
    @EventHandler
    fun onTntExplode(event: EntityExplodeEvent) {
        if (event.entity is TNTPrimed) event.isCancelled = true
    }
    */

    @EventHandler
    fun onCreatePortal(event: PortalCreateEvent) {
        if (event.world in plugin.games.map { it.world }) event.isCancelled = true
    }

    @EventHandler
    fun onAnimalSpawnNaturally(event: CreatureSpawnEvent) {
        if (event.spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL && event.entity is Animals)
            event.isCancelled = true
    }

    @EventHandler
    fun onArrowHit(event: ProjectileHitEvent) {
        if (event.entity is Arrow) arrowHit(this, event)
    }

    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        itemSpawn(this, event)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        val pdc = entity.persistentDataContainer
        val bossKey = NamespacedKey(plugin, "boss")
        val isBoss = pdc.has(bossKey, PersistentDataType.BYTE)
        val isNightMob = isNightMonster(entity)
        if (!isBoss && !isNightMob) return

        if (isNightMob) {
            plugin.games.find { entity in it.nightEntities }?.untrackNightEntity(entity)
            event.droppedExp += getNightMonsterBonusExp(entity)
        }

        if (isBoss) {
            bossDeath(this, event)
        }
    }

    @EventHandler
    fun onPlayerUseFireworkRocket(event: PlayerElytraBoostEvent) {
        playerElytraBoost(this, event)
    }

    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        prepareAnvil(this, event)
    }
}