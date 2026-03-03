package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.game.event.CreateEpicChest
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound
import org.bukkit.entity.Firework
import org.bukkit.event.player.PlayerInteractEvent

fun playerUseSignalFirework(listener: EventManager, event: PlayerInteractEvent) {
    val player = event.player

    val marmotte = DataManager.getMarmotte(player) ?: return

    event.isCancelled = true

    val item = event.item ?: return

    if (player.inventory.itemInMainHand.isSimilar(item)) {
        player.inventory.itemInMainHand.amount--
    } else if (player.inventory.itemInOffHand.isSimilar(item)) {
        player.inventory.itemInOffHand.amount--
    }

    val location = player.location
    val world = player.world

    val firework = world.spawn(location, Firework::class.java)
    val meta = firework.fireworkMeta
    meta.power = 2
    firework.fireworkMeta = meta

    firework.detonate()

    world.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f, 1.0f)

    player.sendMessage(text("신호 폭죽을 발사했습니다! 보급이 투하됩니다...").color(NamedTextColor.YELLOW))

    CreateEpicChest(marmotte.game, location).run()
}
