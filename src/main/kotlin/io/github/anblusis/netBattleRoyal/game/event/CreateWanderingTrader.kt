package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.BattleRoyalItemData
import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.WanderingTrader
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import java.time.Duration
import kotlin.collections.filter
import kotlin.random.Random

class CreateWanderingTrader(
    private val game: Game,
    private val regions: List<Region>
) : Runnable {
    override fun run() {
        fun randomTrades(): List<MerchantRecipe> {
            val recipes = mutableListOf<MerchantRecipe>()

            val buyingItems = TradeItems.entries.filter { it.canSell }.shuffled().take(Random.nextInt(2,5)).sortedBy { it.value }
            for (item in buyingItems) {
                val multiple = Random.nextDouble(0.7, 0.9)
                val count = item.count(multiple)
                val tradeValue = item.value * multiple * count
                val price = tradeValue.toInt().coerceAtLeast(1)
                val recipe = MerchantRecipe(ItemStack(Material.EMERALD, price), 9999)
                recipe.addIngredient((if (item.itemStack != null) item.itemStack.apply { amount = count } else ItemStack(item.material, count)))
                recipes.add(recipe)
            }

            val sellingItems = TradeItems.entries.toList().shuffled().take(Random.nextInt(3,7)).sortedBy { it.value }
            for (item in sellingItems) {
                val multiple = Random.nextDouble(1.1, 1.3)
                val count = item.count(multiple)
                val tradeValue = item.value * multiple * count
                val price = tradeValue.toInt().coerceAtLeast(1)

                val recipe = if (item.itemStack != null) MerchantRecipe(item.itemStack.apply { amount = count }, item.itemStack.amount)
                    else MerchantRecipe(ItemStack(item.material, count), 9999)
                recipe.addIngredient(ItemStack(Material.EMERALD, price))
                recipes.add(recipe)
            }

            return recipes
        }

        regions.forEach { region ->
            game.marmottes.filter { it.region == region }.forEach {
                val player = it.player
                player.showTitle(
                    Title.title(
                        text(""),
                        text("출현").color(NamedTextColor.GOLD),
                        Title.Times.times(
                            Duration.ofMillis(500),
                            Duration.ofSeconds(2),
                            Duration.ofMillis(500)
                        )
                    )
                )
            }
            val spawnLocation = region.center.clone().apply {
                x += (Random.nextDouble() - 0.5) * region.width
                z += (Random.nextDouble() - 0.5) * region.height
            }

            val ableHeightNumbers = mutableListOf<Int>()
            val maxY = region.center.world.getHighestBlockYAt(spawnLocation)
            var isAboveBlock = false

            for (i in game.minY..maxY) {
                val y = i + 1
                val currentLocation = spawnLocation.clone().apply { this.y = y.toDouble() }
                if (currentLocation.block.type.isSolid) {
                    isAboveBlock = true
                } else if (isAboveBlock) {
                    ableHeightNumbers.add(y)
                    isAboveBlock = false
                }
            }
            ableHeightNumbers.shuffle()

            spawnLocation.y = ableHeightNumbers.first().toDouble()

            game.world.spawn(spawnLocation, WanderingTrader::class.java).apply {
                customName(text("떠돌이 상인").color(NamedTextColor.GOLD))
                isCustomNameVisible = true
                removeWhenFarAway = false
                setCanDrinkPotion(false)
                recipes = randomTrades()

                game.entities.add(this)

                plugin.ticker.runTask({
                    game.entities.remove(this)
                    if (!isDead) {
                        world.spawnParticle(Particle.SMOKE, location, 10, 0.5, 0.5, 0.5, 0.1)
                        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.1f)
                        remove()
                    }
                }, 2800L)
            }
        }
    }
}

enum class TradeItems(val material: Material, val value: Double, val canSell: Boolean = false, val itemStack: ItemStack? = null) {
    NETHERITE_INGOT(Material.NETHERITE_INGOT, 20.0, true),
    DIAMOND(Material.DIAMOND, 4.0, true),
    GOLD_INGOT(Material.GOLD_INGOT, 2.0, true),
    IRON_INGOT(Material.IRON_INGOT, 0.4, true),
    COPPER_INGOT(Material.COPPER_INGOT, 0.15, true),
    COAL(Material.COAL, 0.12, true),
    LAPIS_LAZULI(Material.LAPIS_LAZULI, 0.3, true),
    GLOWSTONE_DUST(Material.GLOWSTONE_DUST, 0.2),
    GUNPOWDER(Material.GUNPOWDER, 0.2),
    REDSTONE(Material.REDSTONE, 0.16),
    SLIME_BALL(Material.SLIME_BALL, 0.1),
    BONE(Material.BONE, 0.1),
    LAVA_BUCKET(Material.LAVA_BUCKET, 8.0),
    QUARTZ(Material.QUARTZ, 0.5),
    PAPER(Material.PAPER, 0.2),
    LEATHER(Material.LEATHER, 0.15),
    ECHO_SHARD(Material.ECHO_SHARD, 1.0),
    AMETHYST_SHARD(Material.AMETHYST_SHARD, 0.6),
    NETHER_STAR(Material.NETHER_STAR, 10.0),
    SIGNAL_GENERATOR(Material.YELLOW_DYE, 10.0, false, BattleRoyalItemData.SIGNAL_GENERATOR.item.clone());

    val count: (Double) -> Int
        get() = { multiple ->
            val value = value * multiple
            var currentValue = value
            var count = 1
            while (currentValue % 1.0 > currentValue * 0.1 && currentValue % 1.0 < 1 - (currentValue * 0.1)) {
                currentValue += value
                count++
            }
            count.coerceAtMost(material.maxStackSize)
        }
}