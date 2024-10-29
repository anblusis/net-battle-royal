package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.item.ArmorSystem
import io.github.anblusis.netBattleRoyal.item.RainArmor
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.Listener
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.ItemFlag

enum class CustomArmor(val item: ItemStack, val stat: Map<String, Double>, val system: ArmorSystem = object : ArmorSystem() {}) {
    RAIN_ARMOR(ItemStack(Material.LEATHER_CHESTPLATE).apply item@ {
        itemMeta = (itemMeta as LeatherArmorMeta).apply {
            displayName(
                text().color(NamedTextColor.BLUE).content("RainArmor").decoration(TextDecoration.ITALIC, false).build()
            )
            setColor(Color.AQUA)
            lore(
                listOf(space())
                    .plus(
                        text()
                            .color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("비가 내리는 상황에서 신속 부여").build()
                    )
                    .plus(listOf(space()))
                    .plus(
                        makeAttributeLore(this@item, mapOf("armor" to 10.0, "armor_tough" to 0.1, "attack_damage" to 2.0, "movement_speed" to 0.02, "health_steal" to 3.0, "defense_penetration" to 15.0))
                    )
            )
            removeAttributeModifier(Attribute.GENERIC_ARMOR)
            removeItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        }
    }, mapOf("armor" to 10.0, "armor_tough" to 0.1, "attack_damage" to 2.0, "movement_speed" to 0.02, "health_steal" to 3.0, "defense_penetration" to 15.0), RainArmor);
}

fun makeAttributeLore(item: ItemStack, stat: Map<String, Double>): List<Component> {
    val lore = ArrayList<Component>()
    val useExplain = when (item.type.equipmentSlot) {
        EquipmentSlot.HEAD -> "머리에 있을 때:"
        EquipmentSlot.CHEST -> "몸에 있을 때:"
        EquipmentSlot.LEGS -> "다리에 있을 때:"
        EquipmentSlot.FEET -> "발에 있을 때:"
        EquipmentSlot.HAND -> "주로 사용하는 손에 있을 때:"
        EquipmentSlot.OFF_HAND -> "주로 사용하지 않는 손에 있을 때"
    }
    lore.add(text()
        .content(useExplain)
        .color(NamedTextColor.GRAY)
        .decoration(TextDecoration.ITALIC, false).build()
    )
    if (item.type.equipmentSlot == EquipmentSlot.HAND) {
        stat.forEach { (key, value) ->
            lore.add(text()
                .content("${value}${statKoreanName[key]}")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }
    stat.forEach { (key, value) ->
        if (value < 0) {
            lore.add(text()
                .content("$value${statKoreanName[key]}")
                .color(NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false).build()
            )
        } else {
            lore.add(text()
                .content("+${value}${statKoreanName[key]}")
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }
    return lore
}

val statKoreanName = mapOf(
    "armor" to " 방어",
    "armor_tough" to " 방어 강도",
    "attack_damage" to " 공격 피해",
    "attack_speed" to " 공격 속도",
    "movement_speed" to " 이동 속도",
    "knockback_resistance" to " 밀치기 저항",
    "mana_regen" to " 마나 재생",
    "health_steal" to " 흡혈",
    "defense_penetration" to "% 방어 관통",
)