package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.data.EventResult
import io.github.anblusis.netBattleRoyal.data.transcendLevel
import org.bukkit.Sound
import org.bukkit.Tag
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

fun playerInteractWithTranscendBook(listener: EventManager, event: PlayerInteractEvent): EventResult {
    if (event.action !in listOf(Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR)) return EventResult.FAIL
    if (DataManager.getMarmotte(event.player) == null) return EventResult.FAIL

    val player = event.player
    val inventory = player.inventory
    if (!Tag.ITEMS_TRIMMABLE_ARMOR.isTagged(inventory.itemInOffHand.type)) {
        player.sendMessage("주로 사용하지 않는 손에 갑옷을 든 채로 사용해주세요.")
        return EventResult.FAIL
    }

    if (event.item!!.amount != 1) {
        player.sendMessage("초월서는 한 번에 하나씩만 사용할 수 있습니다.")
        return EventResult.FAIL
    }

    val level = event.item!!.transcendLevel

    inventory.setItemInMainHand(inventory.itemInOffHand)
    inventory.setItemInOffHand(null)

    player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1.5f)
    player.server.dispatchCommand(
        player.server.consoleSender,
        "psychics enchant ${player.name} $level add"
    )

    return EventResult.TRANSCEND_ITEM
}
