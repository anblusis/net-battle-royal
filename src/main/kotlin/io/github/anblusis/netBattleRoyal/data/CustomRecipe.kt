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
    RAIN_ARMOR("rain_armor",
        CustomEquipment.RAIN_ARMOR.item,
        listOf("A A", "ABA", "AAA"),
        listOf("A A", "ABA", "AAA"),
        mapOf('A' to ItemStack(Material.LEATHER), 'B' to ItemStack(Material.WATER_BUCKET)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    ),
    AMETHYST_SWORD("amethyst_sword",
        CustomEquipment.AMETHYST_SWORD.item,
        listOf("A", "B"),
        listOf(" A ", " B ", "   "),
        mapOf('A' to ItemStack(Material.AMETHYST_SHARD), 'B' to ItemStack(Material.STICK)),
        mapOf(),
        CustomRecipeType.SHAPED,
        listOf()
    )
    ;

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