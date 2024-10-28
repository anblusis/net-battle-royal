package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.item.ArmorSystem
import io.github.anblusis.netBattleRoyal.item.RainArmor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.Listener
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta

enum class CustomArmor(val item: ItemStack, val stat: Map<String, Double>, val system: ArmorSystem = object : ArmorSystem() {}) {
    RAIN_ARMOR(BattleRoyalItemData.RAIN_ARMOR.item, mapOf("armor" to 10.0, "armor_tough" to 0.1), RainArmor)

    fun makeStatLore(): List<Component> {
        val lore = ArrayList<Component>()
        val useExplain = when (item.type.equipmentSlot) {
            EquipmentSlot.HEAD -> "투구"
            EquipmentSlot.CHEST -> "몸에 착용 시:"
            EquipmentSlot.LEGS -> ""
            EquipmentSlot.FEET -> ""
            EquipmentSlot.HAND -> ""
            EquipmentSlot.OFF_HAND -> ""
        }
        lore.add(text()
            .content(useExplain)
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false).build()
        )
        stat.forEach { (key, value) ->
            lore.add(text()
                .content("+${value} ${statKoreanName[key]}")
                .color(NamedTextColor.DARK_BLUE)
                .decoration(TextDecoration.ITALIC, false).build()
            )
        }
        return lore
    }
}

val statKoreanName = mapOf(
    "armor" to "방어",
    "armor_tough" to "방어 강도"
)