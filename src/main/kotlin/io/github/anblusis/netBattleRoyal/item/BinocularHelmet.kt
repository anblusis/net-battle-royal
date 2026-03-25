package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object BinocularHelmet : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false
        val listener = BinocularListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
        return true
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
            // 10m 이상일 때 1m당 1% 증가 (최대 20%)
            val bonusPercent = ((distance - 10.0) * 0.01).coerceAtMost(0.20)

            event.damage *= (1.0 + bonusPercent)
        }
    }
}
