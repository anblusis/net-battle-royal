package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomArmor
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

abstract class ArmorSystem {
    val players = mutableListOf<Player>()

    open fun onArmorEquip(player: Player, armor: CustomArmor) {
        players.add(player)
        armor.stat["armor"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR)!!.baseValue += it
        }
        armor.stat["armor_tough"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)!!.baseValue += it
        }
    }

    open fun onArmorUnequip(player: Player, armor: CustomArmor) {
        players.remove(player)
        armor.stat["armor"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR)!!.baseValue -= it
        }
        armor.stat["armor_tough"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)!!.baseValue -= it
        }
    }

    open fun onUpdate() {}
}