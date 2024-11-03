package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.entity.Player

abstract class CustomEquipmentSystem {
    abstract val players: MutableList<Player>

    open fun onEnable(player: Player, equipment: CustomEquipment? = null) {
        players.add(player)

        val stat = DataManager.getMarmotte(player)!!.stat

        equipment?.stat?.filterKeys { it.attribute == null }?.forEach {
            stat[it.key] = stat[it.key]?.plus(it.value)
        }
    }

    open fun onDisable(player: Player, equipment: CustomEquipment? = null) {
        players.remove(player)

        val stat = DataManager.getMarmotte(player)!!.stat

        equipment?.stat?.filterKeys { it.attribute == null }?.forEach {
            stat[it.key] = stat[it.key]?.minus(it.value)
        }
    }

    open fun onUpdate() {
        players.toList().forEach { player ->
            if (DataManager.getMarmotte(player) == null) onDisable(player)
        }
    }

    open fun onRemove() {
        players.toList().forEach { player ->
            onDisable(player)
        }
    }
}