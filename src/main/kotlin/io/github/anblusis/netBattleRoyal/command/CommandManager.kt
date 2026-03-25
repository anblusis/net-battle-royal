package io.github.anblusis.netBattleRoyal.command

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.event.CreateRandomEvent
import io.github.anblusis.netBattleRoyal.game.event.FightStart
import io.github.anblusis.netBattleRoyal.game.event.RandomGameEvent
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import xyz.icetang.lib.invfx.openFrame
import io.github.monun.kommand.PluginKommand
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object CommandManager {
    fun register(kommand: PluginKommand) {
        kommand.register("netbattleroyal", "netbr") {
            val battleRoyalItemArgument = dynamic { _, input ->
                if (input in BattleRoyalItemData.entries
                        .map { it.name }
                ) BattleRoyalItemData.valueOf(input).item.clone()
                else CustomEquipment.valueOf(input).item.clone()
            }.apply {
                suggests {
                    suggest(BattleRoyalItemData.entries.map { it.name })
                    suggest(CustomEquipment.entries.map { it.name })
                }
            }
            val worldArgument = dynamic { _, input ->
                plugin.server.getWorld(input)
            }.apply {
                suggests {
                    suggest(plugin.server.worlds.map { it.name })
                }
            }

            then("debug") {
                requires { isOp }
                then("createRandomEvent") {
                    executes {
                        plugin.games.forEach { game ->
                            CreateRandomEvent(game, 100, RandomGameEvent.entries.toTypedArray()).run()
                        }
                    }
                }
                then("stopInvincibleTime") {
                    executes {
                        plugin.games.forEach { game ->
                            game.tasks.removeIf { it.task is FightStart }
                            FightStart(game).run()
                        }
                    }
                }
                then("skipGamePhase") {
                    executes {
                        plugin.games.forEach { game ->
                            game.skipToNextPhase()
                        }
                    }
                }
            }

            then("givebattleroyalitem") {
                requires { isOp }
                then("players" to players()) {
                    then("item" to battleRoyalItemArgument) {
                        executes {
                            giveBattleRoyalItem(it["players"], it["item"], 1)
                        }
                        then("count" to int()) {
                            executes {
                                giveBattleRoyalItem(it["players"], it["item"], it["count"])
                            }
                        }
                    }
                }
            }
            then("map") {
                executes {
                    require(sender is Player)
                    makeBattleRoyalMap(sender as Player)
                }
                then("player" to player()) {
                    requires { isOp }
                    executes {
                        makeBattleRoyalMap(it["player"])
                    }
                }
            }
            then("ui") {
                executes {
                    require(sender is Player)
                    showBattleRoyalUI(sender as Player)
                }
                then("player" to player()) {
                    requires { isOp }
                    executes {
                        showBattleRoyalUI(it["player"])
                    }
                }
            }
            then("printchestsdata") {
                requires { isOp }
                executes {
                    printChestsData()
                }
            }
            then("expresschestsdata") {
                requires { isOp }
                then("block") {
                    executes {
                        expressChestsData(true)
                    }
                }
                then("effect") {
                    executes {
                        expressChestsData(false)
                    }
                }
            }
            then("syncchestsdata") {
                requires { isOp }
                then("player") {
                    then("player" to player()) {
                        executes {
                            synchronizeChestsData(DataManager.getMarmotte(it["player"])?.game)
                        }
                    }
                }
                then("world") {
                    then("world" to worldArgument) {
                        executes {
                            synchronizeChestsData(plugin.games.find { game -> game.world == it["world"] })
                        }
                    }
                }
            }
            then("createbattleroyal") {
                then("map" to string()) {
                    then("world" to worldArgument) {
                        then("mode" to int()) {
                            then("players" to players()) {
                                executes {
                                    createBattleRoyal(it["map"], it["world"], it["mode"], it["players"])
                                }
                            }
                            executes {
                                createBattleRoyal(it["map"], it["world"], it["mode"], listOf())
                            }
                        }
                    }
                }
            }
            then("joinbattleroyal") {
                then("world" to worldArgument) {
                    then("players" to players()) {
                        executes {
                            joinBattleRoyal(plugin.games.find { game -> game.world == it["world"] }, it["players"])
                        }
                    }
                }
            }
            then("removebattleroyal") {
                then("player") {
                    then("player" to player()) {
                        executes {
                            removeBattleRoyal(DataManager.getMarmotte(it["player"])?.game)
                        }
                    }
                }
                then("world") {
                    then("world" to worldArgument) {
                        executes {
                            removeBattleRoyal(plugin.games.find { game -> game.world == it["world"] })
                        }
                    }
                }
            }
        }
    }

    private fun giveBattleRoyalItem(players: List<Player>, item: ItemStack, count: Int) {
        players.forEach { player ->
            player.inventory.addItem(item.apply {
                amount = count
            })
        }
    }

    private fun makeBattleRoyalMap(player: Player) {
        DataManager.getMarmotte(player)?.let {
            player.inventory.addItem(it.game.mapItem)
        }
    }

    private fun showBattleRoyalUI(player: Player) {
        DataManager.getMarmotte(player)?.game?.let {
            player.openFrame(it.mainInv)
        }
    }

    private fun printChestsData() {
        val codeSnippet = StringBuilder("listOf(\n")

        plugin.debugChestData.forEach { chestData ->
            val location = chestData.location
            val type = chestData.type

            codeSnippet.append(
                """
                |    ChestData(
                |        Location(world, ${location.x}, ${location.y}, ${location.z}),
                |        ChestType.$type
                |    ),
            """.trimMargin()
            ).append("\n")
        }

        codeSnippet.append(")")

        plugin.server.broadcast(text(codeSnippet.toString()))

        val directory = File("plugins/netBattleRoyale")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val filePath = Paths.get(directory.path, "chestsData.txt")
        Files.write(filePath, codeSnippet.toString().toByteArray(), StandardOpenOption.CREATE)
    }

    private fun createBattleRoyal(map: String, world: World, mode: Int, players: List<Player>) {
        plugin.games.add(Game(map, world, mode, players.toMutableList()))
    }

    private fun joinBattleRoyal(game: Game?, players: List<Player>) {
        game?.let {
            players.forEach { player ->
                it.marmottes.add(
                    DataManager.addMarmotte(player, game)
                )
            }
        }
    }

    private fun removeBattleRoyal(game: Game?) {
        game?.remove()
    }

    private fun expressChestsData(makeBlock: Boolean) {
        plugin.debugChestData.forEach { chestData ->
            val location = chestData.location.clone().apply {
                x += 0.5
                y += 0.5
                z += 0.5
            }

            if (makeBlock) {
                location.block.type = chestData.type.material
            } else {
                val dustOptions = when (chestData.type) {
                    ChestType.NORMAL -> Particle.DustOptions(org.bukkit.Color.WHITE, 5.0f)
                    ChestType.RARE -> Particle.DustOptions(org.bukkit.Color.GREEN, 5.0f)
                    ChestType.EPIC -> Particle.DustOptions(org.bukkit.Color.PURPLE, 5.0f)
                }
                location.world.spawnParticle(Particle.DUST, location, 1, 0.0, 0.0, 0.0, 0.0, dustOptions)
            }
        }
    }

    private fun synchronizeChestsData(game: Game?) {
        plugin.debugChestData.clear()
        val chests = game?.chests ?: return

        chests.forEach { chest ->
            plugin.debugChestData.add(chest.chestData)
        }
    }
}
