package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameTask
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import java.time.Duration
import kotlin.random.Random

class StartTntRain(
    private val game: Game,
    private val regions: List<Region>
) : Runnable {
    override fun run() {
        regions.forEach { region ->
            region.isTntRaining = true
            game.tasks.plusAssign(
                listOf(
                    GameTask(
                        game = game,
                        task = StopTntRain(game, region),
                        displayName = "TNT 비 종료",
                        tick = Random.nextInt(800, 1200),
                        priority = 0,
                        canRestart = false,
                        isVisible = true,
                        regions = listOf(region)
                    ),
                    GameTask(
                        game = game,
                        task = CreateTntRain(game, region),
                        displayName = "TNT 비 생성",
                        tick = 100,
                        priority = -999,
                        canRestart = true,
                        isVisible = false,
                        regions = listOf(region)
                    )
                )
            )
        }

        game.marmottes.filter { it.region in regions }.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("TNT 비가 내리기 시작합니다.").color(NamedTextColor.GOLD),
                    Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(2),
                        Duration.ofMillis(500)
                    )
                )
            )
        }
    }
}