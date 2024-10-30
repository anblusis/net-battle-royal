package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.WeatherType
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RainArmor: CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment) {
        super.onEnable(player, equipment)
        val listener = RainListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment) {
        super.onDisable(player, equipment)
        HandlerList.unregisterAll(listeners[player] ?: return)
    }

    override fun onUpdate() {
        players.forEach { player ->
            if (player.playerWeather == WeatherType.DOWNFALL) player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SPEED,
                    5,
                    1
                )
            )
        }
    }

    override fun onRemove() {
        val willRemovedPlayers = players.toList()
        willRemovedPlayers.forEach { onDisable(it, CustomEquipment.RAIN_ARMOR) }
    }

    class RainListener(val player: Player): Listener {
    }
}