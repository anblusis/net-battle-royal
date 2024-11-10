package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.WeatherType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object SlimeChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.SLIME_CHESTPLATE)
        val listener = SlimeListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.SLIME_CHESTPLATE)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }

    class SlimeListener(val player: Player) : Listener {
        @EventHandler(ignoreCancelled = true)
        fun onPlayerReceiveDamage(event: EntityDamageByEntityEvent) {
            if (event.entity == player && event.damager is LivingEntity) {
                if (event.cause != DamageCause.ENTITY_ATTACK) return
                val damager = event.damager as LivingEntity
                val amplifier = damager.getPotionEffect(PotionEffectType.SLOW)?.amplifier ?: -1
                damager.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.SLOW,
                        50,
                        amplifier + 1
                    )
                )
            }
        }
    }
}