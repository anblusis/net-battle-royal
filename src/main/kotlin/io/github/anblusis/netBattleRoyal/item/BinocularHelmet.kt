package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.math.min

object BinocularHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return
        super.onEnable(player, CustomEquipment.BINOCULARS_HELMET)
        val listener = BinocularListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return
        super.onDisable(player, CustomEquipment.BINOCULARS_HELMET)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }

    private class BinocularListener(val player: Player) : Listener {
        @EventHandler
        fun onDealDamage(event: EntityDamageByEntityEvent) {
            val victim = event.entity as? LivingEntity ?: return
            val sourcePlayer: Player = when (event.damager) {
                is Player -> event.damager as Player
                is Projectile if (event.damager as Projectile).shooter is Player -> (event.damager as Projectile).shooter as Player
                else -> return
            }
            if (sourcePlayer != player) return

            val distance = sourcePlayer.location.distance(victim.location)
            if (distance < 10.0) return
            val bonusPercent = min(distance, 30.0) / 100.0

            event.damage *= 0.9 + bonusPercent
        }
    }
}

