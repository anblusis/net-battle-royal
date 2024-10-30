package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.entity.Player

abstract class CustomEquipmentSystem {
    val players = mutableListOf<Player>()

    open fun onEnable(player: Player, equipment: CustomEquipment) {
        if (player in players) return
        players.add(player)
        equipment.stat.filterKeys { it.attribute == null }.forEach {
            DataManager.getMarmotte(player)!!.stat[it.key]?.plus(it.value)
        }
    }

    open fun onDisable(player: Player, equipment: CustomEquipment) {
        if (player !in players) return
        players.remove(player)
        equipment.stat.filterKeys { it.attribute == null }.forEach {
            DataManager.getMarmotte(player)!!.stat[it.key]?.minus(it.value)
        }
    }

    open fun onUpdate() {}

    open fun onRemove() {}
}