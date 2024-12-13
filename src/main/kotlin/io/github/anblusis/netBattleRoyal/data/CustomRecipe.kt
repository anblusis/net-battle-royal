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
        mapOf('A' to ItemStack(Material.GUNPOWDER), 'B' to RecipeChoice.MaterialChoice(
            Material.ARROW, Material.TIPPED_ARROW, Material.SPECTRAL_ARROW
        )),
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
        mapOf('A' to ItemStack(Material.REDSTONE), 'B' to RecipeChoice.MaterialChoice(
            Material.ARROW, Material.TIPPED_ARROW, Material.SPECTRAL_ARROW
        )),
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
    COMET_CHESTPLATE(
        "comet_chestplate",
        CustomEquipment.COMET_CHESTPLATE.item,
        listOf("A A", "BCB", "AAA"),
        listOf("A A", "BCB", "AAA"),
        mapOf('A' to ItemStack(Material.IRON_INGOT), 'B' to ItemStack(Material.AMETHYST_SHARD), 'C' to ItemStack(Material.NETHER_STAR)),
        mapOf(),
        CustomRecipeType.SHAPED
    ),
    ;

    private val key = NamespacedKey(plugin, recipeName)

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
    )
}