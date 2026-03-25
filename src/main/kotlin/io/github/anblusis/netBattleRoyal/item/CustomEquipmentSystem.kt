package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomEquipment
import io.github.anblusis.netBattleRoyal.data.DataManager
import org.bukkit.entity.Player

abstract class CustomEquipmentSystem {
    abstract val players: MutableSet<Player>
    internal var equipment: CustomEquipment? = null

    open fun onEnable(player: Player): Boolean {
        if (!players.add(player)) return false

        val marmotte = DataManager.getMarmotte(player) ?: return false
        val stat = marmotte.stat

        equipment?.stat?.filterKeys { it.attribute == null }?.forEach {
            stat[it.key] = stat[it.key]?.plus(it.value) ?: it.value
        }

        return true
    }

    open fun onDisable(player: Player): Boolean {
        if (!players.remove(player)) return false

        val marmotte = DataManager.getMarmotte(player) ?: return false
        val stat = marmotte.stat

        equipment?.stat?.filterKeys { it.attribute == null }?.forEach {
            stat[it.key] = stat[it.key]?.minus(it.value) ?: 0.0
        }

        return true
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