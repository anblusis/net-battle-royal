package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.NamespacedKey
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.persistence.PersistentDataType

private val PHOENIX_ARMOR_KEY = NamespacedKey(plugin, "phoenix_armor_count")

private class PhoenixListener(val player: Player) : Listener {
    val armorCount: Byte
        get() = player.persistentDataContainer.getOrDefault(PHOENIX_ARMOR_KEY, PersistentDataType.BYTE, 0)

    fun plusArmorCount() {
        player.persistentDataContainer.set(PHOENIX_ARMOR_KEY, PersistentDataType.BYTE, (armorCount + 1).toByte())
    }

    fun minusArmorCount() {
        armorCount.run {
            if (this <= 1) player.persistentDataContainer.remove(PHOENIX_ARMOR_KEY)
            else player.persistentDataContainer.set(PHOENIX_ARMOR_KEY, PersistentDataType.BYTE, (this - 1).toByte())
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerReceiveDamage(event: EntityDamageByEntityEvent) {
        if (event.entity == player && event.damager is LivingEntity) {
            if (event.cause != DamageCause.ENTITY_ATTACK) return
            val damager = event.damager as LivingEntity
            val ticks = armorCount * 20
            damager.fireTicks = ticks.coerceAtMost(damager.fireTicks)
        }
    }
}

object PhoenixHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, PhoenixListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = PhoenixListener(player)
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

object PhoenixChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, PhoenixListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = PhoenixListener(player)
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

object PhoenixLeggings : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, PhoenixListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = PhoenixListener(player)
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

object PhoenixBoots : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, PhoenixListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = PhoenixListener(player)
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
