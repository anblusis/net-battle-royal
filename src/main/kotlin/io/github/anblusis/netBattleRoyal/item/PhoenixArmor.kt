package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.WeatherType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private class PhoenixListener(val player: Player) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onPlayerReceiveDamage(event: EntityDamageByEntityEvent) {
        if (event.entity == player && event.damager is LivingEntity) {
            if (event.cause != DamageCause.ENTITY_ATTACK) return
            val damager = event.damager as LivingEntity
            damager.fireTicks = (damager.fireTicks + 20).coerceAtMost(80)
        }
    }
}

object PhoenixHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.PHOENIX_HELMET)
        val listener = PhoenixListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.PHOENIX_HELMET)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }
}

object PhoenixChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.PHOENIX_CHESTPLATE)
        val listener = PhoenixListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.PHOENIX_CHESTPLATE)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }
}

object PhoenixLeggings : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.PHOENIX_LEGGINGS)
        val listener = PhoenixListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.PHOENIX_LEGGINGS)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }
}

object PhoenixBoots : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.PHOENIX_BOOTS)
        val listener = PhoenixListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.PHOENIX_BOOTS)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }
}

