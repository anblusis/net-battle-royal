package io.github.anblusis.netBattleRoyal.data

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import io.github.anblusis.netBattleRoyal.item.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
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

enum class CustomEquipment(
    private val itemWithoutAttribute: ItemStack,
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
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 4.0
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
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0
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
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 1.0,
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
                addItemFlags(ItemFlag.HIDE_DYE)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 1.0,
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
            }
        },
        mapOf(),
        EquipmentSlot.OFF_HAND,
        RainDrop
    ),
    MANA_ACCELERATOR(
        ItemStack(Material.LIGHT_BLUE_DYE).apply item@{
            itemMeta = itemMeta.apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("마나 가속기").decoration(TextDecoration.ITALIC, false).build()
                )
            }
        },
        mapOf(
            CustomAttribute.MANA_REGEN to 0.5
        ),
        EquipmentSlot.OFF_HAND
    ),
    BONE_HELMET(
        ItemStack(Material.LEATHER_HELMET).apply item@{
            itemMeta = (itemMeta as LeatherArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("골때리는 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                setColor(Color.WHITE)
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.05
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 8.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.05
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 6.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.05
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.MOVEMENT_SPEED to -0.05
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 9.0,
            CustomAttribute.ARMOR_TOUGH to 3.0,
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 6.0,
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
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.HEALTH_REGEN to 0.1
        ), EquipmentSlot.FEET
    ),
    PHOENIX_HELMET(
        ItemStack(Material.GOLDEN_HELMET).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("피닉스 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.COPPER, TrimPattern.DUNE)
                addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)

                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대를 1초간 불태움").build()
                    )
                )
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 3.0
        ), EquipmentSlot.HEAD, PhoenixHelmet
    ),
    PHOENIX_CHESTPLATE(
        ItemStack(Material.GOLDEN_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("피닉스 흉갑").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.COPPER, TrimPattern.DUNE)
                addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)

                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대를 1초간 불태움").build()
                    )
                )
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 6.0
        ), EquipmentSlot.CHEST, PhoenixChestplate
    ),
    PHOENIX_LEGGINGS(
        ItemStack(Material.GOLDEN_LEGGINGS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("피닉스 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.COPPER, TrimPattern.DUNE)
                addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)

                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대를 1초간 불태움").build()
                    )
                )
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 4.0
        ), EquipmentSlot.LEGS, PhoenixLeggings
    ),
    PHOENIX_BOOTS(
        ItemStack(Material.GOLDEN_BOOTS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("피닉스 부츠").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.COPPER, TrimPattern.DUNE)
                addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)

                lore(
                    listOf(
                        text()
                            .color(NamedTextColor.GOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .content("피격 시 때린 상대를 1초간 불태움").build()
                    )
                )
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 2.0
        ), EquipmentSlot.FEET, PhoenixBoots
    ),
    IRON_GOLEM_HELMET(
        ItemStack(Material.IRON_HELMET).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("철골렘 투구").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.IRON, TrimPattern.RIB)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 2.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.KNOCKBACK_RESISTANCE to 2.5,
            CustomAttribute.MOVEMENT_SPEED to -0.05
        ), EquipmentSlot.HEAD
    ),
    IRON_GOLEM_CHESTPLATE(
        ItemStack(Material.IRON_CHESTPLATE).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("철골렘 흉갑").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.IRON, TrimPattern.RIB)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 6.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.KNOCKBACK_RESISTANCE to 2.5,
            CustomAttribute.MOVEMENT_SPEED to -0.05
        ), EquipmentSlot.CHEST
    ),
    IRON_GOLEM_LEGGINGS(
        ItemStack(Material.IRON_LEGGINGS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("철골렘 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.IRON, TrimPattern.RIB)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 5.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.KNOCKBACK_RESISTANCE to 2.5,
            CustomAttribute.MOVEMENT_SPEED to -0.05
        ), EquipmentSlot.LEGS
    ),
    IRON_GOLEM_BOOTS(
        ItemStack(Material.IRON_BOOTS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("철골렘 부츠").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.IRON, TrimPattern.RIB)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 2.0,
            CustomAttribute.ARMOR_TOUGH to 1.0,
            CustomAttribute.KNOCKBACK_RESISTANCE to 2.5,
            CustomAttribute.MOVEMENT_SPEED to -0.05
        ), EquipmentSlot.FEET
    ),
    BINOCULARS_HELMET(
        ItemStack(Material.IRON_HELMET).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("쌍안경 헬멧").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.EYE)
                lore(listOf(
                    text("10m 이상 거리에 있는 적에게서"),
                    text("거리가 1m 멀어질 때 마다 주는 피해 1% 증가 (최대 20%)")
                ).map {
                    it.color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)
                })
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 2.0
        ), EquipmentSlot.HEAD, BinocularHelmet
    ),
    ASSASSIN_LEGGINGS(
        ItemStack(Material.IRON_LEGGINGS).apply item@{
            itemMeta = (itemMeta as ArmorMeta).apply {
                displayName(
                    text().color(NamedTextColor.WHITE).content("암살자의 레깅스").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                trim = ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SENTRY)
                addEnchant(Enchantment.SWIFT_SNEAK, 5, true)
                addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 5.0
        ), EquipmentSlot.LEGS
    ),
    KNIGHT_CAPE(
        ItemStack(Material.ELYTRA).apply item@{
            itemMeta = itemMeta.apply {
                displayName(
                    text().content("기사의 망토").decoration(TextDecoration.ITALIC, false)
                        .build()
                )
                lore(listOf(
                    text("폭죽 사용 불가").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)
                ))
                addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
            }
        },
        mapOf(
            CustomAttribute.ARMOR to 8.0,
            CustomAttribute.ARMOR_TOUGH to 2.0,
            CustomAttribute.MOVEMENT_SPEED to 0.08
        ), EquipmentSlot.CHEST
    )


    ;

    // by lazy와 =의 차이: by lazy는 처음 접근할 때 초기화, =는 즉시 초기화
    val item: ItemStack by lazy {
        itemWithoutAttribute.apply {
            itemMeta = itemMeta.apply {
                makeAttribute(stat, itemSlot)
            }
        }
    }
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
        else -> "착용 시:"
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
                    .content(" ${value + if (key.attribute == Attribute.ATTACK_DAMAGE) 1.0 else 0.0} ${key.displayName}")
                    .color(NamedTextColor.DARK_GREEN)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        } else if (value < 0) {
            attributeLore.add(
                text()
                    .content("${if (key.isPercentage) "${(value*100).toInt()}%" else value.toString().removeSuffix(".0")} ${key.displayName}")
                    .color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        } else {
            attributeLore.add(
                text()
                    .content("+${if (key.isPercentage) "${(value*100).toInt()}%" else value.toString().removeSuffix(".0")} ${key.displayName}")
                    .color(NamedTextColor.BLUE)
                    .decoration(TextDecoration.ITALIC, false).build()
            )
        }
        if (key.attribute != null) {
            val namespacedKey = NamespacedKey(plugin, key.name.lowercase())
            attributes.put(
                key.attribute,
                AttributeModifier(
                    namespacedKey,
                    if (itemSlot.isHand && key.attribute == Attribute.ATTACK_SPEED)
                        value - 4.0
                    else if (key.attribute == Attribute.KNOCKBACK_RESISTANCE)
                        value / 10.0
                    else
                        value,
                    if (key.isPercentage)
                        AttributeModifier.Operation.MULTIPLY_SCALAR_1 // ADD_SCALAR 하고 동일
                    else
                        AttributeModifier.Operation.ADD_NUMBER
                )
            )
        }
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
    ARMOR("방어", Attribute.ARMOR, false, false),
    ARMOR_TOUGH("방어 강도", Attribute.ARMOR_TOUGHNESS, false, false),
    ATTACK_DAMAGE("공격 피해", Attribute.ATTACK_DAMAGE, false, true),
    ATTACK_SPEED("공격 속도", Attribute.ATTACK_SPEED, false, true),
    MOVEMENT_SPEED("속도", Attribute.MOVEMENT_SPEED, true, false),
    KNOCKBACK_RESISTANCE("밀치기 저항", Attribute.KNOCKBACK_RESISTANCE, false, false),
    MANA_REGEN("마나 재생", null, false, false),
    HEALTH_REGEN("체력 재생", null, false, false),
    HEALTH_STEAL("흡혈", null, false, false),
    DEFENSE_PENETRATION("방어 관통", null, true, false),
}