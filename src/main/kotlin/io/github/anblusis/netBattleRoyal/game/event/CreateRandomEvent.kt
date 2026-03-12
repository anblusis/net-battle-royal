package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameTask
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Sound
import kotlin.random.Random

class CreateRandomEvent(
    private val game: Game,
    private val tick: Int,
    private val events: Array<RandomGameEvent>
) : Runnable {
    override fun run() {
        game.marmottes.forEach {
            it.player.playSound(it.player.location, Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2.0f)
        }
        val randomEvent = RandomGameEvent.weightedRandom(events) ?: return
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
                        ).append(text(" 지역에 "))
                            .append(
                                text("상자")
                                    .color(NamedTextColor.LIGHT_PURPLE)
                                    .decorate(TextDecoration.BOLD)
                                    .hoverEvent(HoverEvent.showText(text("에픽 상자가 해당 지역에 떨어집니다.\n낙하 지점은 빔으로 표시됩니다.")))
                            )
                            .append(text("가 떨어집니다."))
                            .color(NamedTextColor.GOLD)
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
                selectedRegions.add(Region("default", "기본", game.worldBorderCenter, 0.0, 0.0, 0))
                selectedRegions.addAll(selectRegions(regions, 1, 5))
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
                val selectedRegions = selectRegions(regions, 2, 4)
                val selectedWave = MonsterWave.entries.random()
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 "))
                            .append(
                                text(selectedWave.displayName)
                                    .color(NamedTextColor.LIGHT_PURPLE)
                                    .decorate(TextDecoration.BOLD)
                                    .hoverEvent(HoverEvent.showText(text("해당 지역에 몬스터들을 소환합니다.\n몬스터들은 스폰 후 일정 시간이 지나면 사라집니다.")))
                            ).append(text("들이 소환됩니다.")).color(NamedTextColor.GOLD)
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
                val selectedRegions = selectRegions(regions, 1, 2)
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 "))
                            .append(
                                text("TNT 비")
                                    .color(NamedTextColor.LIGHT_PURPLE)
                                    .decorate(TextDecoration.BOLD)
                                    .hoverEvent(HoverEvent.showText(text("잠시동안 하늘에서 TNT가 떨어집니다.")))
                            ).append(text("가 내립니다.")).color(NamedTextColor.GOLD)
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

            RandomGameEvent.WANDERING_TRADER -> {
                val regions = game.regions.filter { game.isInWorldBorder(it.center, true) }.shuffled()
                if (regions.isEmpty()) return
                val selectedRegions = selectRegions(regions, 1, 3)
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegions.joinToString(", ") { it.displayName }).decorate(
                                TextDecoration.BOLD
                            )
                        ).append(text(" 지역에 "))
                            .append(
                                text("떠돌이 상인")
                                    .color(NamedTextColor.LIGHT_PURPLE)
                                    .decorate(TextDecoration.BOLD)
                                    .hoverEvent(HoverEvent.showText(text("해당 지역에 떠돌이 상인이 출현합니다.\n떠돌이 상인은 여러 재료를 에메랄드와 교환합니다.\n떠돌이 상인은 스폰 후 일정 시간이 지나면 사라집니다.")))
                            ).append(text("이 출현합니다.")).color(NamedTextColor.GOLD)
                    )
                }
                game.tasks.add(
                    GameTask(
                        game,
                        CreateWanderingTrader(game, selectedRegions),
                        "떠돌이 상인",
                        tick,
                        1,
                        false,
                        selectedRegions
                    )
                )
            }

            RandomGameEvent.BOSS_SPAWN -> {
                val regions = game.regions.filter { game.isInWorldBorder(it.center, true) }.shuffled()
                if (regions.isEmpty()) return
                val selectedRegion = regions.first()
                val boss = BossType.entries.random()
                game.marmottes.forEach { marmotte ->
                    val player = marmotte.player
                    player.sendMessage(
                        text("${tick / 20}초 후에 ").append(
                            text(selectedRegion.displayName).decorate(TextDecoration.BOLD)
                        ).append(text(" 지역에 "))
                            .append(
                                text(boss.displayName)
                                    .color(NamedTextColor.LIGHT_PURPLE)
                                    .decorate(TextDecoration.BOLD)
                                    .hoverEvent(HoverEvent.showText(text("해당 지역에 보스가 등장합니다.\n보스는 죽을 시 희귀 아이템을 드랍합니다.")))
                            ).append(text("가 등장합니다.")).color(NamedTextColor.GOLD)
                    )
                }
                game.tasks.add(
                    GameTask(
                        game,
                        SpawnBoss(game, selectedRegion, boss),
                        "보스 등장",
                        tick,
                        1,
                        false,
                        listOf(selectedRegion)
                    )
                )
            }
        }
    }

    private fun selectRegions(regions: List<Region>, minCount: Int, maxCount: Int): List<Region> {
        if (regions.isEmpty()) return listOf()

        val count = Random.nextInt(minCount, maxCount + 1).coerceAtMost(regions.size)
        return regions.take(count)
    }
}