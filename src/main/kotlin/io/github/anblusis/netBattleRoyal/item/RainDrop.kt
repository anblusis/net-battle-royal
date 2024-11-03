package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import net.kyori.adventure.text.Component.text
import org.bukkit.WeatherType
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object RainDrop : CustomEquipmentSystem() {
    override val players = mutableListOf<Player>()

    override fun onEnable(player: Player, equipment: CustomEquipment?) {
        if (player in players) return

        super.onEnable(player, CustomEquipment.RAIN_DROP)
    }

    override fun onDisable(player: Player, equipment: CustomEquipment?) {
        if (player !in players) return

        super.onDisable(player, CustomEquipment.RAIN_DROP)
    }

    override fun onUpdate() {
        super.onUpdate()
        players.forEach { player ->
            if (player.playerWeather == WeatherType.DOWNFALL) player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.INCREASE_DAMAGE,
                    5,
                    0
                )
            )
        }
    }
}