package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomArmor
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.WeatherType
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RainArmor: ArmorSystem() {
    private val listeners = hashMapOf<Player, Listener>()

    override fun onArmorEquip(player: Player, armor: CustomArmor) {
        super.onArmorEquip(player, armor)
        val listener = RainListener(player)
        player.server.pluginManager.registerEvents(listener, plugin)
        listeners[player] = listener
    }

    override fun onArmorUnequip(player: Player, armor: CustomArmor) {
        super.onArmorUnequip(player, armor)
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
        willRemovedPlayers.forEach { onArmorUnequip(it, CustomArmor.RAIN_ARMOR) }
    }

    class RainListener(val player: Player): Listener {
    }
}