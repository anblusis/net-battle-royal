package io.github.anblusis.netBattleRoyal.data

import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class BattleRoyalItemData(val item: ItemStack) {
    MAGIC_STICK(ItemStack(Material.STICK).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.DARK_PURPLE).content("debugStick")
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    SUPER_POTION(ItemStack(Material.POTION).apply {
        itemMeta = (itemMeta as org.bukkit.inventory.meta.PotionMeta).apply {
            displayName(
                text()
                    .color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false)
                    .content("슈퍼 포션").build()
            )
            listOf(
                PotionEffectType.SPEED,
                PotionEffectType.JUMP_BOOST,
                PotionEffectType.REGENERATION
            ).forEach {
                addCustomEffect(PotionEffect(it, 400, 1), true)
            }
            color = Color.LIME
        }
    }),
    CALORIE_COMPRESSED_POTION(ItemStack(Material.POTION).apply {
        itemMeta = (itemMeta as org.bukkit.inventory.meta.PotionMeta).apply {
            displayName(
                text().color(NamedTextColor.WHITE).content("열랑 압축 포션").decoration(TextDecoration.ITALIC, false).build()
            )
            addCustomEffect(PotionEffect(PotionEffectType.SATURATION, 100, 0), true)
            color = Color.RED
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
                        .decoration(TextDecoration.ITALIC, false)
                        .content("초월 시키고자 하는 장비를 반대 손에 두고 좌클릭 시").build(),
                    text().color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                        .content("책을 사용해 장비에 초월 인첸트를 부여합니다.").build()
                )
            )
        }
    }),
    RANDOM_ENCHANT_BOOK(ItemStack(Material.BOOK).apply {
        itemMeta = itemMeta.apply {
            displayName(text().content("랜덤 인첸트북")
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.YELLOW).build())
            lore(
                listOf(
                    text().color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                        .content("제작에 소모한 금 주괴 1개당 인첸트 레벨 3 증가").build(),
                    text().color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                        .content("제작 후 우클릭으로 사용").build()
                )
            )
        }
        enchantValue = 9
    }),
    SUPER_EXP_BOTTLE(ItemStack(Material.EXPERIENCE_BOTTLE).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.GREEN).content("슈퍼 경험치 병").decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    SIGNAL_GENERATOR(ItemStack(Material.YELLOW_DYE).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.GOLD).content("신호 발생기").decoration(TextDecoration.ITALIC, false).build()
            )
        }
    }),
    SIGNAL_FIREWORK(ItemStack(Material.FIREWORK_ROCKET).apply {
        itemMeta = itemMeta.apply {
            displayName(
                text().color(NamedTextColor.YELLOW).content("신호 폭죽").decoration(TextDecoration.ITALIC, false).build()
            )
            lore(
                listOf(
                    text().color(NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                        .content("해당 위치에 보급 투하").build()
                )
            )

            addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
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

var ItemStack.transcendLevel: Int
    get() {
        return itemMeta.displayName()?.children()?.firstOrNull()?.color()?.value() ?: 0
    }
    set(value) {
        var display = transcendBook

        if (value > 0) {
            display = display.children(
                listOf(
                    space().color(TextColor.color(value)),
                    text()
                        .content(value.toRomanNumerals())
                        .color(NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false).build()
                )
            )
        }

        itemMeta = itemMeta.apply {
            displayName(display)
        }
    }

val explosionPowerTag = text().content("폭발력")
    .decoration(TextDecoration.ITALIC, false)
    .color(NamedTextColor.BLUE).build()

var ItemStack.hasExplosionPower
    get() = lore()?.find { lore ->
        lore is TextComponent && lore.content() == explosionPowerTag.content()
    }?.let { tag ->
        tag.children().firstOrNull()?.color()?.value()
    } ?: 0
    set(value) {
        val lore = lore() ?: ArrayList()
        lore.removeIf { it is TextComponent && it.content() == explosionPowerTag.content() }

        if (value > 0) {
            lore.add(
                0, explosionPowerTag.children(
                    listOf(
                        space().color(TextColor.color(value)),
                        text().content(value.toRomanNumerals()).color(NamedTextColor.BLUE)
                            .decoration(TextDecoration.ITALIC, false).build()
                    )
                )
            )
        }

        lore(lore)
    }

val magneticPowerTag = text().content("자속 상태")
    .decoration(TextDecoration.ITALIC, false)
    .color(NamedTextColor.BLUE).build()

var ItemStack.hasMagneticPower
    get() = lore()?.find { lore ->
        lore is TextComponent && lore.content() == magneticPowerTag.content()
    }?.let { true } ?: false
    set(value) {
        val lore = lore() ?: ArrayList()
        lore.removeIf { it is TextComponent && it.content() == magneticPowerTag.content() }

        if (value) {
            lore.add(
                0, magneticPowerTag
            )
        }

        lore(lore)
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
