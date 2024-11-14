package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameTask
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Sound
import kotlin.random.Random

class CreateRandomEvent(
    private val game: Game,
    private val tick: Int,
    private val events: Array<RandomGameEvent>
) : Runnable {
    override fun run() {
        val randomEvent = events.random()
        when (randomEvent) {
            RandomGameEvent.EPIC_CHEST -> {
                val regions = game.regions.filter { game.isInWorldBorder(it.center, true) }
                if (regions.isEmpty()) return
                val randomRegion = regions.random()
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(randomRegion.displayName).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 상자가 떨어집니다.")).color(NamedTextColor.GOLD)
                    )
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2.0f)
                }
                game.tasks.add(
                    GameTask(
                        game,
                        CreateEpicChest(game, randomRegion),
                        "에픽 상자",
                        tick,
                        2,
                        false,
                        listOf(randomRegion)
                    )
                )
            }

            RandomGameEvent.CHANGE_WEATHER -> {
                val regions = game.regions.filter { game.isInWorldBorder(it.center, true) }.shuffled()
                if (regions.isEmpty()) return
                val selectedRegions = mutableListOf<Region>()
                repeat(Random.nextInt(1, 6)) {
                    selectedRegions.add(regions[it])
                }
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역의 날씨가 변합니다.")).color(NamedTextColor.GOLD)
                    )
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2.0f)
                }
                game.tasks.add(
                    GameTask(
                        game,
                        ChangeWeather(game, selectedRegions),
                        "날씨 변경",
                        tick,
                        1,
                        false,
                        selectedRegions
                    )
                )
            }

            RandomGameEvent.MONSTER_WAVE -> {
                val regions = game.regions.filter { game.isInWorldBorder(it.center, true) }.shuffled()
                if (regions.isEmpty()) return
                val selectedRegions = mutableListOf<Region>()
                repeat(Random.nextInt(1, 4)) {
                    selectedRegions.add(regions[it])
                }
                val selectedWave = MonsterWave.values().random()
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 ${selectedWave.displayName}들이 소환됩니다.")).color(NamedTextColor.GOLD)
                    )
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2.0f)
                }
                game.tasks.add(
                    GameTask(
                        game,
                        CreateMonsterWave(game, selectedRegions, selectedWave),
                        "${selectedWave.displayName} 웨이브",
                        tick,
                        1,
                        false,
                        selectedRegions
                    )
                )
            }

            RandomGameEvent.TNT_RAIN -> {
                val regions =
                    game.regions.filter { game.isInWorldBorder(it.center, true) && !it.isTntRaining }.shuffled()
                if (regions.isEmpty()) return
                val selectedRegions = mutableListOf<Region>()
                repeat(Random.nextInt(1, 3)) {
                    selectedRegions.add(regions[it])
                }
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 TNT 비가 내립니다.")).color(NamedTextColor.GOLD)
                    )
                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2.0f)
                }
                game.tasks.add(
                    GameTask(
                        game,
                        StartTntRain(game, selectedRegions),
                        "TNT 비",
                        tick,
                        1,
                        false,
                        selectedRegions
                    )
                )
            }
        }
    }
}