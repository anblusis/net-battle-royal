package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameState
import io.github.anblusis.netBattleRoyal.game.GameTask
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import java.time.Duration

class FightStart(
    private val game: Game
) : Runnable {
    override fun run() {
        game.state = GameState.PLAYING
        game.marmottes.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("무적이 해제됩니다.").color(NamedTextColor.AQUA),
                    Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(2),
                        Duration.ofMillis(500)
                    )
                )
            )
        }
        game.startDayNightCycle()
        game.run {
            tasks.add(
                GameTask(
                    this,
                    CreateRandomEvent(this, 1200, RandomGameEvent.values()),
                    "무작위 사건 타이머",
                    2400,
                    2800,
                    -999,
                    true
                )
            )
        }
    }
}