package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Sound
import org.bukkit.Tag
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

fun playerInteractWithTranscendBook(listener: EventManager, event: PlayerInteractEvent) : EventResult {
    if (event.action !in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)) return EventResult.FAIL
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL

    val player = event.player
    val inventory = player.inventory
    if (!Tag.ITEMS_TRIMMABLE_ARMOR.isTagged(inventory.itemInOffHand.type)) {
        player.sendMessage("주로 사용하지 않는 손에 갑옷을 든 채로 사용해주세요.")
        return EventResult.FAIL
    }

    inventory.setItemInMainHand(inventory.itemInOffHand)
    inventory.setItemInOffHand(null)

    player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1.5f)
    player.server.dispatchCommand(
        player.server.consoleSender,
        "psychics enchant ${player.name} add ${event.item!!.transcendLevel}"
    )

    return EventResult.TRANSCEND_ITEM
}
