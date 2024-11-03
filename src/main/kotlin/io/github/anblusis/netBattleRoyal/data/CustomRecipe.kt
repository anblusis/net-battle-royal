package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

enum class CustomRecipe(
    recipeName: String,
    val result: ItemStack,
    private val shape: List<String>,
    private val displayShape: List<String>,
    private val ingredients: Map<Char, ItemStack>,
    private val counts: Map<Char, Int>,
    private val type: CustomRecipeType,
    val worlds: List<World>
) {
    RAIN_ARMOR(
        "rain_armor",
        CustomEquipment.RAIN_ARMOR.item,
        listOf("A A", "ABA", "AAA"),
        listOf("A A", "ABA", "AAA"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    RAIN_LEGGINGS(
        "rain_leggings",
        CustomEquipment.RAIN_LEGGINGS.item,
        listOf("ABA", "A A", "A A"),
        listOf("ABA", "A A", "A A"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    RAIN_HELMET(
        "rain_helmet",
        CustomEquipment.RAIN_HELMET.item,
        listOf("ABA", "A A"),
        listOf("ABA", "A A", "   "),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    RAIN_BOOTS(
        "rain_boots",
        CustomEquipment.RAIN_BOOTS.item,
        listOf("A A", "A A", "B B"),
        listOf("A A", "A A", "B B"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    RAIN_DROP(
        "rain_drop",
        CustomEquipment.RAIN_DROP.item,
        listOf(" A ", "ABA", " A "),
        listOf(" A ", "ABA", " A "),
        mapOf('A' to ItemStack(Material.WATER_BUCKET), 'B' to ItemStack(Material.DIAMOND)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    BONE_HELMET(
        "bone_helmet",
        CustomEquipment.BONE_HELMET.item,
        listOf("AAA", "A A"),
        listOf("AAA", "A A", "   "),
        mapOf('A' to ItemStack(Material.BONE)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),;

    private val key = NamespacedKey(plugin, recipeName)

    fun toBukkitRecipe(): Recipe {
        when (type) {
            CustomRecipeType.SHAPED -> {
                val recipe = ShapedRecipe(key, result)
                recipe.shape(*shape.toTypedArray())
                ingredients.forEach { (key, item) ->
                    recipe.setIngredient(key, item)
                }
                return recipe
            }

            CustomRecipeType.SHAPELESS -> {
                val recipe = ShapelessRecipe(key, result)
                ingredients.forEach { (key, item) ->
                    recipe.addIngredient(counts[key]!!, item)
                }
                return recipe
            }
        }
    }

    fun toItemShape(): List<ItemStack?> = displayShape.flatMap { it.toCharArray().toList() }.map { ingredients[it] }

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