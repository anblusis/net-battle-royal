package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.WeatherType
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RainChestplate : CustomEquipmentSystem() {
    private val listeners = hashMapOf<Player, Listener>()
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.RAIN_CHESTPLATE)
        val listener = RainListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.RAIN_CHESTPLATE)
        HandlerList.unregisterAll(listeners[player]!!)
        listeners.remove(player)
    }

    override fun onUpdate() {
        super.onUpdate()
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

    class RainListener(val player: Player) : Listener {
    }
}