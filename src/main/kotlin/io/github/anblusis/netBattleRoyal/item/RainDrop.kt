package io.github.anblusis.netBattleRoyal.item

import org.bukkit.WeatherType
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RainDrop : CustomEquipmentSystem() {
    override val players = mutableSetOf<Player>()

    override fun onUpdate() {
        super.onUpdate()
        players.forEach { player ->
            if (player.playerWeather == WeatherType.DOWNFALL) player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.STRENGTH,
                    5,
                    0
                )
            )
        }
    }
}