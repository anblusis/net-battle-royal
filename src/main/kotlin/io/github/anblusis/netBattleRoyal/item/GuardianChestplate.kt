package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.tool.hostileFilter
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object GuardianChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableSetOf<Player>()

    override fun onEnable(player: Player): Boolean {
        if (!super.onEnable(player)) return false

        val listener = GuardianChestplateListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener

        return true
    }

    override fun onDisable(player: Player): Boolean {
        if (!super.onDisable(player)) return false

        HandlerList.unregisterAll(listeners[player] ?: return true)
        listeners.remove(player)

        return true
    }

    private class GuardianChestplateListener(val player: Player) : Listener {
        private var lastUsed = 0L

        @EventHandler(ignoreCancelled = true)
        fun onDamage(event: EntityDamageEvent) {
            if (event.entity != player) return

            val finalHealth = player.health - event.finalDamage
            if (finalHealth <= 6.0) {
                val now = System.currentTimeMillis()
                if (now - lastUsed < 30000) return

                lastUsed = now
                player.setCooldown(CustomEquipment.GUARDIAN_CHESTPLATE.item.type, 600)

                player.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 20 * 10, 1))

                player.world.spawnParticle(Particle.EXPLOSION_EMITTER, player.location, 5, 0.5, 0.5, 0.5, 0.1)
                player.world.playSound(player.location, Sound.ENTITY_IRON_GOLEM_HURT, 1f, 0.5f)

                player.getNearbyEntities(4.0, 4.0, 4.0).forEach { entity ->
                    if (player.hostileFilter().test(entity)) {
                        entity as LivingEntity
                        entity.damage(2.0, player)
                        entity.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 40, 1))
                    }
                }
            }
        }
    }
}


