package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.*

enum class CustomRecipe(
    recipeName: String,
    val result: ItemStack,
    private val shape: List<String>,
    private val displayShape: List<String>,
    private val ingredients: Map<Char, Any>,
    private val counts: Map<Char, Int>,
    private val type: CustomRecipeType,
    val resultFunction: (Array<ItemStack?>) -> ItemStack? = { result }
) {
    RAIN_HELMET(
        "rain_helmet",
        CustomEquipment.RAIN_HELMET.item,
        listOf("ABA", "A A"),
        listOf("ABA", "A A", "   "),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    RAIN_CHESTPLATE(
        "rain_armor",
        CustomEquipment.RAIN_CHESTPLATE.item,
        listOf("A A", "ABA", "AAA"),
        listOf("A A", "ABA", "AAA"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    RAIN_LEGGINGS(
        "rain_leggings",
        CustomEquipment.RAIN_LEGGINGS.item,
        listOf("ABA", "A A", "A A"),
        listOf("ABA", "A A", "A A"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    RAIN_BOOTS(
        "rain_boots",
        CustomEquipment.RAIN_BOOTS.item,
        listOf("A A", "A A", "B B"),
        listOf("A A", "A A", "B B"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    RAIN_DROP(
        "rain_drop",
        CustomEquipment.RAIN_DROP.item,
        listOf(" A ", "ABA", " A "),
        listOf(" A ", "ABA", " A "),
        mapOf('A' to ItemStack(Material.WATER_BUCKET), 'B' to ItemStack(Material.DIAMOND)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    BONE_HELMET(
        "bone_helmet",
        CustomEquipment.BONE_HELMET.item,
        listOf("AAA", "A A"),
        listOf("AAA", "A A", "   "),
        mapOf('A' to ItemStack(Material.BONE)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    BONE_CHESTPLATE(
        "bone_chestplate",
        CustomEquipment.BONE_CHESTPLATE.item,
        listOf("A A", "AAA", "AAA"),
        listOf("A A", "AAA", "AAA"),
        mapOf('A' to ItemStack(Material.BONE)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    BONE_LEGGINGS(
        "bone_leggings",
        CustomEquipment.BONE_LEGGINGS.item,
        listOf("AAA", "A A", "A A"),
        listOf("AAA", "A A", "A A"),
        mapOf('A' to ItemStack(Material.BONE)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    BONE_BOOTS(
        "bone_boots",
        CustomEquipment.BONE_BOOTS.item,
        listOf("A A", "A A"),
        listOf("A A", "A A", "   "),
        mapOf('A' to ItemStack(Material.BONE)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    EXPLOSION_ARROW(
        "explosion_arrow",
        ItemStack(Material.ARROW).apply {
            hasExplosionPower = 1
            amount = 2
        },
        listOf(" A ", "ABA", " A "),
        listOf(" A ", "ABA", " A "),
        mapOf('A' to ItemStack(Material.GUNPOWDER), 'B' to RecipeChoice.ExactChoice(ItemStack(Material.ARROW, 1), ItemStack(Material.TIPPED_ARROW, 1), ItemStack(Material.SPECTRAL_ARROW, 1))),
        mapOf(),
        CustomRecipeType.SHAPED,
        {
            val arrow = it[4]!!.clone()
            if (arrow.hasExplosionPower >= 3) null
            else arrow.apply { amount = 2; hasExplosionPower += 1 }
        }
    ),
    MAGNETIC_ARROW(
        "magnetic_arrow",
        ItemStack(Material.ARROW).apply {
            hasMagneticPower = true
            amount = 2
        },
        listOf(" A ", "ABA", " A "),
        listOf(" A ", "ABA", " A "),
        mapOf('A' to ItemStack(Material.REDSTONE), 'B' to RecipeChoice.ExactChoice(ItemStack(Material.ARROW, 1), ItemStack(Material.TIPPED_ARROW, 1), ItemStack(Material.SPECTRAL_ARROW, 1))),
        mapOf(),
        CustomRecipeType.SHAPED,
        {
            val arrow = it[4]!!.clone()
            if (arrow.hasMagneticPower) null
            else arrow.apply { amount = 2; hasMagneticPower = true }
        }
    ),
    SLIME_HELMET(
        "slime_helmet",
        CustomEquipment.SLIME_HELMET.item,
        listOf("AAA", "A A"),
        listOf("AAA", "A A", "   "),
        mapOf('A' to ItemStack(Material.SLIME_BALL)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    SLIME_CHESTPLATE(
        "slime_chestplate",
        CustomEquipment.SLIME_CHESTPLATE.item,
        listOf("A A", "AAA", "AAA"),
        listOf("A A", "AAA", "AAA"),
        mapOf('A' to ItemStack(Material.SLIME_BALL)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    SLIME_LEGGINGS(
        "slime_leggings",
        CustomEquipment.SLIME_LEGGINGS.item,
        listOf("AAA", "A A", "A A"),
        listOf("AAA", "A A", "A A"),
        mapOf('A' to ItemStack(Material.SLIME_BALL)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    SLIME_BOOTS(
        "slime_boots",
        CustomEquipment.SLIME_BOOTS.item,
        listOf("A A", "A A"),
        listOf("A A", "A A", "   "),
        mapOf('A' to ItemStack(Material.SLIME_BALL)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ALLOY_HELMET(
        "alloy_helmet",
        CustomEquipment.ALLOY_HELMET.item,
        listOf("BCB", "A A"),
        listOf("BCB", "A A", "   "),
        mapOf('A' to ItemStack(Material.COPPER_INGOT), 'B' to ItemStack(Material.IRON_INGOT), 'C' to ItemStack(Material.GOLD_INGOT)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ALLOY_CHESTPLATE(
        "alloy_chestplate",
        CustomEquipment.ALLOY_CHESTPLATE.item,
        listOf("B B", "ACA", "BAB"),
        listOf("B B", "ACA", "BAB"),
        mapOf('A' to ItemStack(Material.COPPER_INGOT), 'B' to ItemStack(Material.IRON_INGOT), 'C' to ItemStack(Material.GOLD_INGOT)),
        mapOf(),
        CustomRecipeType.SHAPED,
    ),
    ALLOY_LEGGINGS(
        "alloy_leggings",
        CustomEquipment.ALLOY_LEGGINGS.item,
        listOf("ACA", "B B", "A A"),
        listOf("ACA", "B B", "A A"),
        mapOf('A' to ItemStack(Material.COPPER_INGOT), 'B' to ItemStack(Material.IRON_INGOT), 'C' to ItemStack(Material.GOLD_INGOT)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ALLOY_BOOTS(
        "alloy_boots",
        CustomEquipment.ALLOY_BOOTS.item,
        listOf("B B", "A A"),
        listOf("B B", "A A", "   "),
        mapOf('A' to ItemStack(Material.COPPER_INGOT), 'B' to ItemStack(Material.IRON_INGOT)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    COMET_HELMET(
        "comet_helmet",
        CustomEquipment.COMET_HELMET.item,
        listOf("AAA", "B B"),
        listOf("AAA", "B B", "   "),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.AMETHYST_SHARD)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    COMET_CHESTPLATE(
        "comet_chestplate",
        CustomEquipment.COMET_CHESTPLATE.item,
        listOf("A A", "BCB", "AAA"),
        listOf("A A", "BCB", "AAA"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.AMETHYST_SHARD), 'C' to ItemStack(Material.NETHER_STAR)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    COMET_LEGGINGS(
        "comet_leggings",
        CustomEquipment.COMET_LEGGINGS.item,
        listOf("ACA", "B B", "A A"),
        listOf("ACA", "B B", "A A"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.AMETHYST_SHARD), 'C' to ItemStack(Material.QUARTZ)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    COMET_BOOTS(
        "comet_boots",
        CustomEquipment.COMET_BOOTS.item,
        listOf("B B", "A A"),
        listOf("B B", "A A", "   "),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.AMETHYST_SHARD)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    PHOENIX_HELMET(
        "phoenix_helmet",
        CustomEquipment.PHOENIX_HELMET.item,
        listOf("BAB", "A A"),
        listOf("BAB", "A A", "   "),
        mapOf('A' to ItemStack(Material.GOLD_INGOT), 'B' to ItemStack(Material.GLOWSTONE_DUST)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    PHOENIX_CHESTPLATE(
        "phoenix_chestplate",
        CustomEquipment.PHOENIX_CHESTPLATE.item,
        listOf("A A", "BAB", "ABA"),
        listOf("A A", "BAB", "ABA"),
        mapOf('A' to ItemStack(Material.GOLD_INGOT), 'B' to ItemStack(Material.GLOWSTONE_DUST)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    PHOENIX_LEGGINGS(
        "phoenix_leggings",
        CustomEquipment.PHOENIX_LEGGINGS.item,
        listOf("ABA", "B B", "A A"),
        listOf("ABA", "B B", "A A"),
        mapOf('A' to ItemStack(Material.GOLD_INGOT), 'B' to ItemStack(Material.GLOWSTONE_DUST)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    PHOENIX_BOOTS(
        "phoenix_boots",
        CustomEquipment.PHOENIX_BOOTS.item,
        listOf("B B", "A A"),
        listOf("B B", "A A", "   "),
        mapOf('A' to ItemStack(Material.GOLD_INGOT), 'B' to ItemStack(Material.GLOWSTONE_DUST)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    IRON_GOLEM_HELMET(
        "iron_golem_helmet",
        CustomEquipment.IRON_GOLEM_HELMET.item,
        listOf("ABA", "A A"),
        listOf("ABA", "A A", "   "),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.IRON_BLOCK)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    IRON_GOLEM_CHESTPLATE(
        "iron_golem_chestplate",
        CustomEquipment.IRON_GOLEM_CHESTPLATE.item,
        listOf("A A", "ABA", "AAA"),
        listOf("A A", "ABA", "AAA"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.IRON_BLOCK)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    IRON_GOLEM_LEGGINGS(
        "iron_golem_leggings",
        CustomEquipment.IRON_GOLEM_LEGGINGS.item,
        listOf("ABA", "A A", "A A"),
        listOf("ABA", "A A", "A A"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.IRON_BLOCK)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    IRON_GOLEM_BOOTS(
        "iron_golem_boots",
        CustomEquipment.IRON_GOLEM_BOOTS.item,
        listOf("A A", "B B"),
        listOf("A A", "B B", "   "),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.IRON_BLOCK)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    TRANSCEND_BOOK(
        "transcend_book",
        BattleRoyalItemData.TRANSCEND_BOOK.item.clone().apply {
            transcendLevel = 1
        },
        listOf(),
        listOf("ABB", "BCC", "C  "),
        mapOf('A' to ItemStack(Material.BOOK), 'B' to ItemStack(Material.LAPIS_LAZULI), 'C' to ItemStack(Material.EMERALD)),
        mapOf('A' to 1, 'B' to 3, 'C' to 3),
        CustomRecipeType.SHAPELESS
    ),
    RANDOM_ENCHANT_BOOK(
        "random_enchant_book",
        BattleRoyalItemData.RANDOM_ENCHANT_BOOK.item.clone(),
        listOf(),
        listOf("AB ", "   ", "   "),
        mapOf('A' to ItemStack(Material.BOOK), 'B' to ItemStack(Material.GOLD_INGOT)),
        mapOf('A' to 1, 'B' to 1),
        CustomRecipeType.SHAPELESS,
        {
            val items = it.filterNotNull()
            val goldCount = items.count { item -> item.type == Material.GOLD_INGOT }

            BattleRoyalItemData.RANDOM_ENCHANT_BOOK.item.clone().apply {
                enchantValue = 3 * (goldCount + 2)
            }
        }
    ),
    AMETHYST_SHARD(
        "amethyst_shard",
        ItemStack(Material.AMETHYST_SHARD, 2),
        listOf(),
        listOf("ABB", "BB ", "   "),
        mapOf('A' to ItemStack(Material.EMERALD), 'B' to ItemStack(Material.GLOWSTONE_DUST)),
        mapOf('A' to 1, 'B' to 4),
        CustomRecipeType.SHAPELESS
    ),
    NETHER_STAR(
        "nether_star",
        ItemStack(Material.NETHER_STAR),
        listOf("ABA", "BCB", "ABA"),
        listOf("ABA", "BCB", "ABA"),
        mapOf('A' to ItemStack(Material.DIAMOND), 'B' to ItemStack(Material.EMERALD), 'C' to ItemStack(Material.QUARTZ)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    BINOCULARS_HELMET(
        "binoculars_helmet",
        CustomEquipment.BINOCULARS_HELMET.item,
        listOf("ABA", "ACA"),
        listOf("ABA", "ACA", "   "),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.ECHO_SHARD), 'C' to ItemStack(Material.GLASS)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ASSASSIN_LEGGINGS(
        "assassin_leggings",
        CustomEquipment.ASSASSIN_LEGGINGS.item,
        listOf("CBC", "A A", "A A"),
        listOf("CBC", "A A", "A A"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.ECHO_SHARD), 'C' to ItemStack(Material.REDSTONE)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ECHO_SHARD(
        "echo_shard",
        ItemStack(Material.ECHO_SHARD, 2),
        listOf(),
        listOf("ABB", "BB ", "   "),
        mapOf('A' to ItemStack(Material.EMERALD), 'B' to ItemStack(Material.QUARTZ)),
        mapOf('A' to 1, 'B' to 4),
        CustomRecipeType.SHAPELESS
    ),
    SIGNAL_FIREWORK(
        "signal_firework",
        BattleRoyalItemData.SIGNAL_FIREWORK.item.clone(),
        listOf(),
        listOf("ABB", "BCC", "CD "),
        mapOf(
            'A' to BattleRoyalItemData.SIGNAL_GENERATOR.item,
            'B' to ItemStack(Material.GLOWSTONE_DUST),
            'C' to ItemStack(Material.GUNPOWDER),
            'D' to ItemStack(Material.PAPER)
        ),
        mapOf('A' to 1, 'B' to 3, 'C' to 3, 'D' to 1),
        CustomRecipeType.SHAPELESS
    ),
    CALORIE_COMPRESSED_POTION(
        "calorie_compressed_potion",
        BattleRoyalItemData.CALORIE_COMPRESSED_POTION.item.clone(),
        listOf(),
        listOf("ABB", "BCC", "C  "),
        mapOf(
            'A' to ItemStack(Material.GLASS_BOTTLE),
            'B' to ItemStack(Material.BREAD),
            'C' to ItemStack(Material.COOKED_BEEF)
            ),
        mapOf('A' to 1, 'B' to 3, 'C' to 3),
        CustomRecipeType.SHAPELESS
    ),
    MANA_ACCELERATOR(
        "mana_accelerator",
        CustomEquipment.MANA_ACCELERATOR.item.clone(),
        listOf("ABA", "BAB", "ABA"),
        listOf("ABA", "BAB", "ABA"),
        mapOf('A' to ItemStack(Material.DIAMOND), 'B' to ItemStack(Material.LAPIS_LAZULI)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    NETHERITE_UPGRADE_SMITHING_TEMPLATE(
        "netherite_upgrade_smithing_template",
        ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
        listOf("AAA", "ABA", "AAA"),
        listOf("AAA", "ABA", "AAA"),
        mapOf(
            'A' to ItemStack(Material.COBBLESTONE),
            'B' to ItemStack(Material.DIAMOND),
        ),
        mapOf(),
        CustomRecipeType.SHAPED
    )
    ;

    val key = NamespacedKey(plugin, recipeName)

    fun toBukkitRecipe(): Recipe {
        when (type) {
            CustomRecipeType.SHAPED -> {
                val recipe = ShapedRecipe(key, result)
                recipe.shape(*shape.toTypedArray())
                ingredients.forEach { (key, item) ->
                    if (item is ItemStack) recipe.setIngredient(key, item)
                    else if (item is RecipeChoice) recipe.setIngredient(key, item)
                }
                return recipe
            }

            CustomRecipeType.SHAPELESS -> {
                val recipe = ShapelessRecipe(key, result)
                ingredients.forEach { (key, item) ->
                    if (item is ItemStack) recipe.addIngredient(counts[key]!!, item)
                }
                return recipe
            }
        }
    }

    fun toItemShape(): List<ItemStack?> = displayShape.flatMap { it.toCharArray().toList() }.map {
        if (ingredients[it] is ItemStack) ingredients[it] as ItemStack
        else if (ingredients[it] is RecipeChoice) (ingredients[it] as RecipeChoice).itemStack
        else null
    }

    fun addToServer() {
        val recipe = toBukkitRecipe()
        plugin.server.addRecipe(recipe)
    }

    fun removeFromServer() {
        plugin.server.removeRecipe(key)
    }
}

enum class CustomRecipeType {
    SHAPED, SHAPELESS
}

enum class CustomRecipeSet(
    val displayName: String,
    val displayRecipe: CustomRecipe,
    val recipes: List<CustomRecipe>
) {
    RAIN(
        "비",
        CustomRecipe.RAIN_CHESTPLATE,
        listOf(
            CustomRecipe.RAIN_HELMET,
            CustomRecipe.RAIN_CHESTPLATE,
            CustomRecipe.RAIN_LEGGINGS,
            CustomRecipe.RAIN_BOOTS
        )
    ),
    BONE(
        "뼈",
        CustomRecipe.BONE_CHESTPLATE,
        listOf(
            CustomRecipe.BONE_HELMET,
            CustomRecipe.BONE_CHESTPLATE,
            CustomRecipe.BONE_LEGGINGS,
            CustomRecipe.BONE_BOOTS
        )
    ),
    SLIME(
        "슬라임",
        CustomRecipe.SLIME_CHESTPLATE,
        listOf(
            CustomRecipe.SLIME_HELMET,
            CustomRecipe.SLIME_CHESTPLATE,
            CustomRecipe.SLIME_LEGGINGS,
            CustomRecipe.SLIME_BOOTS
        )
    ),
    ALLOY(
        "합금",
        CustomRecipe.ALLOY_CHESTPLATE,
        listOf(
            CustomRecipe.ALLOY_HELMET,
            CustomRecipe.ALLOY_CHESTPLATE,
            CustomRecipe.ALLOY_LEGGINGS,
            CustomRecipe.ALLOY_BOOTS
        )
    ),
    COMET(
        "혜성",
        CustomRecipe.COMET_CHESTPLATE,
        listOf(
            CustomRecipe.COMET_HELMET,
            CustomRecipe.COMET_CHESTPLATE,
            CustomRecipe.COMET_LEGGINGS,
            CustomRecipe.COMET_BOOTS
        )
    ),
    PHOENIX(
        "피닉스",
        CustomRecipe.PHOENIX_CHESTPLATE,
        listOf(
            CustomRecipe.PHOENIX_HELMET,
            CustomRecipe.PHOENIX_CHESTPLATE,
            CustomRecipe.PHOENIX_LEGGINGS,
            CustomRecipe.PHOENIX_BOOTS
        )
    ),
    IRON_GOLEM(
        "철골렘",
        CustomRecipe.IRON_GOLEM_CHESTPLATE,
        listOf(
            CustomRecipe.IRON_GOLEM_HELMET,
            CustomRecipe.IRON_GOLEM_CHESTPLATE,
            CustomRecipe.IRON_GOLEM_LEGGINGS,
            CustomRecipe.IRON_GOLEM_BOOTS
        )
    )
}