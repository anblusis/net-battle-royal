package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import kotlin.random.Random

private class PhoenixListener(val player: Player) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onPlayerReceiveDamage(event: EntityDamageByEntityEvent) {
        if (event.entity == player && event.damager is LivingEntity) {
            if (event.cause != DamageCause.ENTITY_ATTACK) return
            if (Random.nextDouble() > 0.25) return
            val damager = event.damager as LivingEntity
            if (damager.fireTicks > 0) return
            damager.fireTicks = 60
        }
    }
}

object PhoenixHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, PhoenixListener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = PhoenixListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
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
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
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
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
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
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        val listener = listeners[player]!!
        HandlerList.unregisterAll(listener)
        listeners.remove(player)
        return true
    }
}
