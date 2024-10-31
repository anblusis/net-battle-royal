package io.github.anblusis.netBattleRoyal.data

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.ArrayList
import org.bukkit.Color

enum class BattleRoyalItemData(val item: ItemStack) {
    MAGIC_STICK(ItemStack(Material.STICK).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.DARK_PURPLE).content("debugStick")
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    MAGIC_SWORD(ItemStack(Material.DIAMOND_SWORD).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.AQUA).content("검").decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    BATTLE_ROYAL_MAP(ItemStack(Material.FILLED_MAP).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.GREEN).content("지도").decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    TRANSCEND_BOOK(ItemStack(Material.BOOK).apply {
        itemMeta = itemMeta.apply {
            displayName(
                transcendBook
            )
            lore(
                listOf(
                    text().color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC,false)
                        .content("초월 시키고자 하는 장비를 반대 손에 두고 우클릭 시").build(),
                    text().color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC,false)
                        .content("책을 사용해 장비에 초월 인첸트를 부여합니다.").build()
                )
            )
        }
    }),
    SUPER_EXP_BOTTLE(ItemStack(Material.EXPERIENCE_BOTTLE).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.GREEN).content("슈퍼 경험치 병").decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
}

val transcendBook = text().content("초월서")
    .decoration(TextDecoration.ITALIC, false)
    .color(NamedTextColor.YELLOW).build()

val randomEnchantTag = text().content("랜덤 인첸트")
    .decoration(TextDecoration.ITALIC, false)
    .color(NamedTextColor.BLUE).build()

var ItemStack.enchantValue
    get() = lore()?.find { lore ->
        lore is TextComponent && lore.content() == randomEnchantTag.content()
    }?.let { tag ->
        tag.children().firstOrNull()?.color()?.value()
    } ?: 0
    set(value) {
        val lore = lore() ?: ArrayList()
        lore.removeIf { it is TextComponent && it.content() == randomEnchantTag.content() }

        if (value > 0) {
            lore.add(
                0, randomEnchantTag.children(
                    listOf(
                        space().color(TextColor.color(value)),
                        text().content(value.toString()).color(NamedTextColor.BLUE)
                            .decoration(TextDecoration.ITALIC, false).build(),
                        text().content(" 레벨").color(NamedTextColor.BLUE)
                            .decoration(TextDecoration.ITALIC, false).build()
                    )
                )
            )
        }

        lore(lore)
    }

var ItemStack.transcendLevel
    get() = displayName().children().firstOrNull()?.color()?.value() ?: 0
    set(value) {
        var display = transcendBook

        if (value > 0) {
            display = display.children(listOf(space().color(TextColor.color(value)),
                text()
                    .content(value.toRomanNumerals())
                    .color(NamedTextColor.YELLOW)
                    .decoration(TextDecoration.ITALIC, false).build()
            ))
        }

        itemMeta = itemMeta.apply {
            displayName(display)
        }
    }

private fun Int.toRomanNumerals() = when (this) {
    1 -> "I"
    2 -> "II"
    3 -> "III"
    4 -> "IV"
    5 -> "V"
    6 -> "VI"
    7 -> "VII"
    8 -> "VIII"
    9 -> "IX"
    10 -> "X"
    else -> toString()
}
