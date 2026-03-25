package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object SentinelSoul : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false
        
        val listener = SentinelSoulListener(player)
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

    private class SentinelSoulListener(val player: Player) : Listener {
        private var lastUsed = 0L

        @EventHandler(ignoreCancelled = true)
        fun onAttack(event: EntityDamageByEntityEvent) {
            if (event.damager != player) return

            val target = event.entity as? LivingEntity ?: return
            
            val now = System.currentTimeMillis()
            if (now - lastUsed < 10000) return

            target.addPotionEffect(PotionEffect(PotionEffectType.WITHER, 100, 1))
            lastUsed = now
            player.setCooldown(CustomEquipment.SENTINEL_SOUL.item.type, 200)
        }
    }
}
