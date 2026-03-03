package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.game.event.BossType
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.persistence.PersistentDataType

fun bossDeath(listener: EventManager, event: EntityDeathEvent) {
    val boss = event.entity
    val game = plugin.games.find { it.world == boss.world } ?: return

    val bossName = boss.customName() ?: text(boss.name)

    game.marmottes.forEach { m ->
        m.player.sendMessage(bossName.append(text("(이)가 처치되었습니다!").color(NamedTextColor.GOLD)))
    }
    game.world.playSound(boss.location, Sound.ENTITY_ENDER_DRAGON_DEATH, game.worldBorder.size.toFloat() / 32f, 1f)

    event.drops.clear()
    event.droppedExp = 300 + (0..200).random()

    // 보스 타입 식별 후 보스별 드랍 풀에서 지급
    val typeKey = NamespacedKey(plugin, "boss_type")
    val typeName = boss.persistentDataContainer.get(typeKey, PersistentDataType.STRING)!!
    val pool = BossType.valueOf(typeName).drops

    pool.forEach { item ->
        event.drops.add(item.clone())
    }

    if (typeName == BossType.SKELETON_KNIGHT.name && boss.isInsideVehicle) {
        val horse = boss.vehicle
        horse?.remove()
    }
}
