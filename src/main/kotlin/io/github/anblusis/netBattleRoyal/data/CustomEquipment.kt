package io.github.anblusis.netBattleRoyal.data

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import io.github.anblusis.netBattleRoyal.item.*
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
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import java.util.*

enum class CustomEquipment(
    val item: ItemStack,
    val stat: Map<CustomAttribute, Double>,
    val itemSlot: EquipmentSlot,
    val system: CustomEquipmentSystem = object : CustomEquipmentSystem() {
        override val players = mutableListOf<Player>()
    }
) {
    RAIN_CHESTPLATE(
        ItemStack(Material.LEATHER_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("비의 조끼").decoration(TextDecoration.ITALIC, false).build()
                )
                setColor(Color.AQUA)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("비가 내리는 상황에서 신속 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 5.0
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 5.0
        ),
        EquipmentSlot.CHEST,
        RainChestplate
    ),
    RAIN_LEGGINGS(
        ItemStack(Material.LEATHER_LEGGINGS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("비의 바지").decoration(TextDecoration.ITALIC, false).build()
                )
                setColor(Color.AQUA)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("비가 내리는 상황에서 재생 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 4.0
                    ), EquipmentSlot.LEGS
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 4.0
        ),
        EquipmentSlot.LEGS,
        RainLeggings
    ),
    RAIN_HELMET(
        ItemStack(Material.LEATHER_HELMET).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("비의 모자").decoration(TextDecoration.ITALIC, false).build()
                )
                setColor(Color.AQUA)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 1.5,
                        CustomAttribute.MANA_REGEN to 0.2
                    ), EquipmentSlot.HEAD
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.MANA_REGEN to 0.2
        ),
        EquipmentSlot.HEAD
    ),
    RAIN_BOOTS(
        ItemStack(Material.LEATHER_BOOTS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("비의 장화").decoration(TextDecoration.ITALIC, false).build()
                )
                setColor(Color.AQUA)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 1.5,
                        CustomAttribute.MOVEMENT_SPEED to 0.02
                    ), EquipmentSlot.FEET
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.MOVEMENT_SPEED to 0.02
        ),
        EquipmentSlot.FEET
    ),
    RAIN_DROP(
        ItemStack(Material.LIGHT_BLUE_DYE).apply item@{
            itemMeta = itemMeta.apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("빗방울").decoration(TextDecoration.ITALIC, false).build()
                )
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("비가 내리는 상황에서 힘 부여").build()
                    )
                )
                makeAttribute(mapOf(), EquipmentSlot.OFF_HAND)
            }
        },
        mapOf(),
        EquipmentSlot.OFF_HAND,
        RainDrop
    ),
    BONE_HELMET(
        ItemStack(Material.LEATHER_HELMET).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("골때리는 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.WHITE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR_TOUGH to 5.0
                    ), EquipmentSlot.HEAD
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR_TOUGH to 5.0
        ),
        EquipmentSlot.HEAD
    ),
    BONE_CHESTPLATE(
        ItemStack(Material.LEATHER_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("골때리는 흉갑").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.WHITE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR_TOUGH to 10.0
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR_TOUGH to 10.0
        ),
        EquipmentSlot.CHEST
    ),
    BONE_LEGGINGS(
        ItemStack(Material.LEATHER_LEGGINGS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("골때리는 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.WHITE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR_TOUGH to 8.0
                    ), EquipmentSlot.LEGS
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR_TOUGH to 8.0
        ),
        EquipmentSlot.LEGS
    ),
    BONE_BOOTS(
        ItemStack(Material.LEATHER_BOOTS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("골때리는 부츠").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.WHITE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR_TOUGH to 5.0
                    ), EquipmentSlot.FEET
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR_TOUGH to 5.0
        ),
        EquipmentSlot.FEET
    ),
    SLIME_HELMET(
        ItemStack(Material.LEATHER_HELMET).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("슬라임 모자").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.LIME)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대에게 구속 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 1.5,
                        CustomAttribute.HEALTH_STEAL to 1.0
                    ), EquipmentSlot.HEAD
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 1.5,
            CustomAttribute.HEALTH_STEAL to 1.0
        ), EquipmentSlot.HEAD, SlimeHelmet
    ),
    SLIME_CHESTPLATE(
        ItemStack(Material.LEATHER_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("슬라임 조끼").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.LIME)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대에게 구속 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 4.0,
                        CustomAttribute.HEALTH_STEAL to 1.5
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 4.0,
            CustomAttribute.HEALTH_STEAL to 1.5
        ), EquipmentSlot.CHEST, SlimeChestplate
    ),
    SLIME_LEGGINGS(
        ItemStack(Material.LEATHER_LEGGINGS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("슬라임 바지").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.LIME)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대에게 구속 부여").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 3.0,
                        CustomAttribute.HEALTH_STEAL to 1.5
                    ), EquipmentSlot.LEGS
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.HEALTH_STEAL to 1.5
        ), EquipmentSlot.LEGS, SlimeLeggings
    ),
    SLIME_BOOTS(
        ItemStack(Material.LEATHER_BOOTS).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("슬라임 장화").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.LIME)
                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대에게 구속 부여").build(),
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("낙하 시 튀어오름").build()
                    )
                )
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 1.5,
                        CustomAttribute.HEALTH_STEAL to 1.0
                    ), EquipmentSlot.FEET
                )
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 1.5,
            CustomAttribute.HEALTH_STEAL to 1.0
        ), EquipmentSlot.FEET, SlimeBoots
    ),
    ALLOY_HELMET(
        ItemStack(Material.IRON_HELMET).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("합금 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.GOLD, TrimPattern.SILENCE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 3.0,
                        CustomAttribute.ARMOR_TOUGH to 1.0,
                        CustomAttribute.MOVEMENT_SPEED to -0.005
                    ), EquipmentSlot.HEAD
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.01
        ), EquipmentSlot.HEAD
    ),
    ALLOY_CHESTPLATE(
        ItemStack(Material.IRON_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("합금 흉갑").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.GOLD, TrimPattern.SILENCE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 8.0,
                        CustomAttribute.ARMOR_TOUGH to 1.0,
                        CustomAttribute.MOVEMENT_SPEED to -0.005
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 8.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.01
        ), EquipmentSlot.CHEST
    ),
    ALLOY_LEGGINGS(
        ItemStack(Material.IRON_LEGGINGS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("합금 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.GOLD, TrimPattern.SILENCE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 6.0,
                        CustomAttribute.ARMOR_TOUGH to 1.0,
                        CustomAttribute.MOVEMENT_SPEED to -0.005
                    ), EquipmentSlot.LEGS
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 6.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.01
        ), EquipmentSlot.LEGS
    ),
    ALLOY_BOOTS(
        ItemStack(Material.IRON_BOOTS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("합금 부츠").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.GOLD, TrimPattern.SILENCE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 3.0,
                        CustomAttribute.ARMOR_TOUGH to 1.0,
                        CustomAttribute.MOVEMENT_SPEED to -0.005
                    ), EquipmentSlot.FEET
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.01
        ), EquipmentSlot.FEET
    ),
    COMET_HELMET(
        ItemStack(Material.IRON_HELMET).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("혜성 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.EYE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 5.0,
                        CustomAttribute.ARMOR_TOUGH to 2.0,
                        CustomAttribute.MANA_REGEN to 0.1
                    ), EquipmentSlot.HEAD
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 5.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.MANA_REGEN to 0.1
        ), EquipmentSlot.HEAD
    ),
    COMET_CHESTPLATE(
        ItemStack(Material.IRON_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("혜성 흉갑").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.EYE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 9.0,
                        CustomAttribute.ARMOR_TOUGH to 2.0,
                        CustomAttribute.MANA_REGEN to 0.3,
                        CustomAttribute.HEALTH_REGEN to 0.2
                    ), EquipmentSlot.CHEST
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 9.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.MANA_REGEN to 0.3,
            CustomAttribute.HEALTH_REGEN to 0.2
        ), EquipmentSlot.CHEST
    ),
    COMET_LEGGINGS(
        ItemStack(Material.IRON_LEGGINGS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("혜성 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.EYE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 7.0,
                        CustomAttribute.ARMOR_TOUGH to 2.0,
                        CustomAttribute.MANA_REGEN to 0.2,
                        CustomAttribute.HEALTH_REGEN to 0.1
                    ), EquipmentSlot.LEGS
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 7.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.MANA_REGEN to 0.2,
            CustomAttribute.HEALTH_REGEN to 0.1
        ), EquipmentSlot.LEGS
    ),
    COMET_BOOTS(
        ItemStack(Material.IRON_BOOTS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("혜성 부츠").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.EYE)
                makeAttribute(
                    mapOf(
                        CustomAttribute.ARMOR to 5.0,
                        CustomAttribute.ARMOR_TOUGH to 2.0,
                        CustomAttribute.HEALTH_REGEN to 0.1
                    ), EquipmentSlot.FEET
                )
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 5.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.HEALTH_REGEN to 0.1
        ), EquipmentSlot.FEET
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

    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

    lore(lore()?.plus(attributeLore) ?: attributeLore)
    attributeModifiers = attributes
}

enum class CustomAttribute(
    val displayName: String,
    val attribute: Attribute?,
    val isPercentage: Boolean,
    val isStatic: Boolean
) {
    ARMOR("방어", Attribute.GENERIC_ARMOR, false, false),
    ARMOR_TOUGH("방어 강도", Attribute.GENERIC_ARMOR_TOUGHNESS, false, false),
    ATTACK_DAMAGE("공격 피해", Attribute.GENERIC_ATTACK_DAMAGE, false, true),
    ATTACK_SPEED("공격 속도", Attribute.GENERIC_ATTACK_SPEED, false, true),
    MOVEMENT_SPEED("이동 속도", Attribute.GENERIC_MOVEMENT_SPEED, false, false),
    KNOCKBACK_RESISTANCE("밀치기 저항", Attribute.GENERIC_KNOCKBACK_RESISTANCE, false, false),
    MANA_REGEN("마나 재생", null, false, false),
    HEALTH_REGEN("체력 재생", null, false, false),
    HEALTH_STEAL("흡혈", null, false, false),
    DEFENSE_PENETRATION("방어 관통", null, true, false),
}