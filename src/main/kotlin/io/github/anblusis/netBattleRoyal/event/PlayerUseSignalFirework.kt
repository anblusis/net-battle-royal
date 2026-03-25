package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.event.CreateEpicChest
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.monun.tap.data.persistentData
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.FireworkEffect
import org.bukkit.GameMode
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.entity.Firework
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

private val EPIC_CHEST_FIREWORK_KEY = NamespacedKey(plugin, "is_epic_chest_firework")

internal fun Firework.isEpicChestFirework(): Boolean {
    return persistentDataContainer.getOrDefault(EPIC_CHEST_FIREWORK_KEY, PersistentDataType.BOOLEAN, false)
}

fun playerUseSignalFirework(listener: EventManager, event: PlayerInteractEvent) {
    val player = event.player

    val marmotte = DataManager.getMarmotte(player) ?: return

    event.isCancelled = true

    if (player.eyeLocation.y < player.world.getHighestBlockYAt(player.location)) {
        player.sendMessage(text("천장이 뚫린 곳에서 사용해 주세요."))
        return
    }

    val item = event.item ?: return

    if (player.gameMode != GameMode.CREATIVE) item.amount--

    val location = player.location.clone().add(0.0, 3.0, 0.0)
    val world = player.world

    val firework = world.spawn(location, Firework::class.java)
    val meta = firework.fireworkMeta
    // meta.power = 5
    meta.addEffects(
        FireworkEffect.builder()
            .withColor(org.bukkit.Color.YELLOW)
            .with(FireworkEffect.Type.BALL_LARGE)
            .trail(true)
            .build()
    )
    firework.fireworkMeta = meta
    firework.persistentDataContainer.set(EPIC_CHEST_FIREWORK_KEY, PersistentDataType.BOOLEAN, true)
    firework.velocity = firework.velocity.apply { x = 0.0; y = 0.5; z = 0.0 }
    firework.ticksToDetonate = 60
}
