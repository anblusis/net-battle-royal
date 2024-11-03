package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import java.time.Duration

class StopTntRain(
    private val game: Game,
    private val region: Region
) : Runnable {
    override fun run() {
        region.isTntRaining = false
        game.tasks.remove(
            game.tasks.find { it.displayName == "TNT 비 생성" && it.regions == listOf(region) }
        )
        game.marmottes.filter { it.region == region }.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("TNT 비가 그쳤습니다.").color(NamedTextColor.GOLD),
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