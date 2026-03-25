package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun playerConsumeItem(manager: EventManager, event: PlayerItemConsumeEvent) {
    val player = event.player
    val item = event.item

    if (item.isSimilar(BattleRoyalItemData.CALORIE_COMPRESSED_POTION.item)) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 100, 0))
    }
}

