package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.event.CreateEpicChest
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.monun.tap.data.persistentData
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.entity.Firework
import org.bukkit.event.entity.FireworkExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

internal fun fireworkExplode(listener: EventManager, event: FireworkExplodeEvent) {
    val firework = event.entity

    if (firework.isEpicChestFirework()) {
        val location = firework.location
        val world = firework.world

        val game = plugin.games.find { it.world == location.world && it.isInWorldBorder(location, false) } ?: return

        game.world.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, game.worldBorderSize.toFloat() / 32f, 1f)

        CreateEpicChest(game, location).run()
    }
}
