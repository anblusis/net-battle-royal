package io.github.anblusis.netBattleRoyal.item

import io.github.anblusis.netBattleRoyal.data.CustomArmor
import io.github.anblusis.netBattleRoyal.data.DataManager
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player

abstract class ArmorSystem {
    val players = mutableListOf<Player>()

    open fun onArmorEquip(player: Player, armor: CustomArmor) {
        players.add(player)
        armor.stat["armor"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR)!!.addModifier(AttributeModifier( "${armor.item.displayName()}", it, AttributeModifier.Operation.ADD_NUMBER))
        }
        armor.stat["armor_tough"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)!!.baseValue += it
        }
        armor.stat["knockback_resistance"]?.let {
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)!!.baseValue += it
        }
        armor.stat["movement_speed"]?.let {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue += it
        }
        armor.stat["attack_damage"]?.let {
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue += it
        }
        armor.stat["attack_speed"]?.let {
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)!!.baseValue += it
        }
        armor.stat["mana_regen"]?.let {
            DataManager.getMarmotte(player)!!.stat["mana_regen"]?.plus(it)
        }
        armor.stat["health_steal"]?.let {
            DataManager.getMarmotte(player)!!.stat["health_steal"]?.plus(it)
        }
        armor.stat["defense_penetration"]?.let {
            DataManager.getMarmotte(player)!!.stat["defense_penetration"]?.plus(it)
        }
    }

    open fun onArmorUnequip(player: Player, armor: CustomArmor) {
        players.remove(player)
        armor.stat["armor"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR)!!.removeModifier(AttributeModifier( "${armor.item.displayName()}", it, AttributeModifier.Operation.ADD_NUMBER))
        }
        armor.stat["armor_tough"]?.let {
            player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)!!.baseValue -= it
        }
        armor.stat["knockback_resistance"]?.let {
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)!!.baseValue -= it
        }
        armor.stat["movement_speed"]?.let {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue -= it
        }
        armor.stat["attack_damage"]?.let {
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue -= it
        }
        armor.stat["attack_speed"]?.let {
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)!!.baseValue -= it
        }
        armor.stat["mana_regen"]?.let {
            DataManager.getMarmotte(player)!!.stat["mana_regen"]?.minus(it)
        }
        armor.stat["health_steal"]?.let {
            DataManager.getMarmotte(player)!!.stat["health_steal"]?.minus(it)
        }
        armor.stat["defense_penetration"]?.let {
            DataManager.getMarmotte(player)!!.stat["defense_penetration"]?.minus(it)
        }
    }

    open fun onUpdate() {}

    open fun onRemove() {}
}