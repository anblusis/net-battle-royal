package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.NamespacedKey
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private val SLIME_ARMOR_KEY = NamespacedKey(plugin, "slime_armor_count")

private open class SlimeListener(val player: Player) : Listener {
    val armorCount: Byte
        get() = player.persistentDataContainer.getOrDefault(SLIME_ARMOR_KEY, PersistentDataType.BYTE, 0)

    fun plusArmorCount() {
        player.persistentDataContainer.set(SLIME_ARMOR_KEY, PersistentDataType.BYTE, (armorCount + 1).toByte())
    }

    fun minusArmorCount() {
        armorCount.run {
            if (this <= 1) player.persistentDataContainer.remove(SLIME_ARMOR_KEY)
            else player.persistentDataContainer.set(SLIME_ARMOR_KEY, PersistentDataType.BYTE, (this - 1).toByte())
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerReceiveDamage(event: EntityDamageByEntityEvent) {
        if (event.entity == player && event.damager is LivingEntity) {
            if (event.cause != DamageCause.ENTITY_ATTACK) return
            val damager = event.damager as LivingEntity
            val amplifier = armorCount - 1
            damager.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOWNESS,
                    40,
                    amplifier
                )
            )
        }
    }
}

object SlimeHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, SlimeListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = SlimeListener(player)
        listener.plusArmorCount()
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        listener.minusArmorCount()
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }
}

object SlimeChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, SlimeListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = SlimeListener(player)
        listener.plusArmorCount()
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        listener.minusArmorCount()
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }
}

object SlimeLeggings : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, SlimeListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = SlimeListener(player)
        listener.plusArmorCount()
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        listener.minusArmorCount()
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }
}

private class SlimeBootsListener(player: Player) : SlimeListener(player) {
    @EventHandler
    fun onPlayerHasFallDamage(event: EntityDamageEvent) {
        if (event.entity == player && event.cause == DamageCause.FALL) {
            event.isCancelled = true
            event.entity.velocity = event.entity.velocity.apply {
                y = event.entity.fallDistance * 0.04
            }
        }
    }
}

object SlimeBoots : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, SlimeBootsListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = SlimeBootsListener(player)
        listener.plusArmorCount()
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        listener.minusArmorCount()
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }
}

object SlimeOverlordBoots : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, SlimeBootsListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = SlimeBootsListener(player)
        listener.plusArmorCount()
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        listener.minusArmorCount()
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }

    override fun onUpdate() {
        players.forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.JUMP_BOOST, 2, 1, false, false, true))
        }
    }
}
