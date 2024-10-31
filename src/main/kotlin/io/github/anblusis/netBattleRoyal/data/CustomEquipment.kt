package io.github.anblusis.netBattleRoyal.data

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import io.github.anblusis.netBattleRoyal.item.CustomEquipmentSystem
import io.github.anblusis.netBattleRoyal.item.RainArmor
import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

enum class CustomEquipment(val item: ItemStack, val stat: Map<CustomAttribute, Double>, val itemSlot: EquipmentSlot, val system: CustomEquipmentSystem = object : CustomEquipmentSystem() { override val players = mutableListOf<Player>() }) {
    RAIN_ARMOR(
        ItemStack(Material.LEATHER_CHESTPLATE).apply item@ {
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("비의 흉갑").decoration(TextDecoration.ITALIC, false).build()
                )
                setColor(Color.AQUA)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GRAY)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("비가 내리는 상황에서 신속 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 10.0,
                        CustomAttribute.ARMOR_TOUGH to 0.1,
                        CustomAttribute.ATTACK_DAMAGE to 2.0,
                        CustomAttribute.MOVEMENT_SPEED to 0.02,
                        CustomAttribute.HEALTH_STEAL to 3.0,
                        CustomAttribute.DEFENSE_PENETRATION to 15.0
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 10.0,
            CustomAttribute.ARMOR_TOUGH to 0.1,
            CustomAttribute.ATTACK_DAMAGE to 2.0,
            CustomAttribute.MOVEMENT_SPEED to 0.02,
            CustomAttribute.HEALTH_STEAL to 3.0,
            CustomAttribute.DEFENSE_PENETRATION to 15.0
        ),
        EquipmentSlot.CHEST,
        RainArmor
    ),
    AMETHYST_SWORD(
        ItemStack(Material.AMETHYST_SHARD).apply item@ {
            itemMeta = itemMeta.apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("자수정 단검").decoration(TextDecoration.ITALIC, false).build()
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ATTACK_DAMAGE to 5.0,
                        CustomAttribute.ATTACK_SPEED to 2.5
                    ), EquipmentSlot.HAND
                )
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            }
        },
        mapOf(
            CustomAttribute.ATTACK_DAMAGE to 5.0,
            CustomAttribute.ATTACK_SPEED to 2.5
        ),
        EquipmentSlot.HAND
    ),
}

fun ItemMeta.makeAttribute(stat: Map<CustomAttribute, Double>, itemSlot: EquipmentSlot) {
    val attributeLore = arrayListOf(space())
    val attributes: Multimap<Attribute, AttributeModifier> = ArrayListMultimap.create()

    val useExplain = when (itemSlot) {
        EquipmentSlot.HEAD -> "머리에 있을 때:"
        EquipmentSlot.CHEST -> "몸에 있을 때:"
        EquipmentSlot.LEGS -> "다리에 있을 때:"
        EquipmentSlot.FEET -> "발에 있을 때:"
        EquipmentSlot.HAND -> "주로 사용하는 손에 있을 때:"
        EquipmentSlot.OFF_HAND -> "주로 사용하지 않는 손에 있을 때"
    }
    attributeLore.add(
        text()
            .content(useExplain)
            .color(NamedTextColor.GRAY)
            .decoration(TextDecoration.ITALIC, false).build()
    )
    stat.forEach { (key, value) ->
        if (itemSlot == EquipmentSlot.HAND && key.isStatic) {
            attributeLore.add(
                text()
                    .content(" ${value + if (key.attribute == Attribute.GENERIC_ATTACK_DAMAGE) 1.0 else 0.0} ${key.displayName}")
                    .color(NamedTextColor.DARK_GREEN)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        } else if (value < 0) {
            attributeLore.add(
                text()
                    .content("$value${if (key.isPercentage) "%" else ""} ${key.displayName}")
                    .color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        } else {
            attributeLore.add(
                text()
                    .content("+$value${if (key.isPercentage) "%" else ""} ${key.displayName}")
                    .color(NamedTextColor.BLUE)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        }
        if (key.attribute != null)
            attributes.put(
                key.attribute,
                AttributeModifier(
                    UUID.randomUUID(),
                    key.displayName,
                    if (itemSlot.isHand && key.attribute == Attribute.GENERIC_ATTACK_SPEED)
                        value - 4.0
                    else
                        value,
                    AttributeModifier.Operation.ADD_NUMBER,
                    itemSlot
                )
            )
    }

    lore(lore()?.plus(attributeLore) ?: attributeLore)
    attributeModifiers = attributes
}

enum class CustomAttribute(val displayName: String, val attribute: Attribute?, val isPercentage: Boolean, val isStatic: Boolean) {
    ARMOR("방어", Attribute.GENERIC_ARMOR, false, false),
    ARMOR_TOUGH("방어 강도", Attribute.GENERIC_ARMOR_TOUGHNESS, false, false),
    ATTACK_DAMAGE("공격 피해", Attribute.GENERIC_ATTACK_DAMAGE, false, true),
    ATTACK_SPEED("공격 속도", Attribute.GENERIC_ATTACK_SPEED, false, true),
    MOVEMENT_SPEED("이동 속도", Attribute.GENERIC_MOVEMENT_SPEED, false, false),
    KNOCKBACK_RESISTANCE("밀치기 저항", Attribute.GENERIC_KNOCKBACK_RESISTANCE, false, false),
    MANA_REGEN("마나 재생", null, false, false),
    HEALTH_STEAL("흡혈", null, false, false),
    DEFENSE_PENETRATION("방어 관통", null, true, false),
}