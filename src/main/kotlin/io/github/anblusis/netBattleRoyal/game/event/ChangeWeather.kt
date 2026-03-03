package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameWeather
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import java.time.Duration

class ChangeWeather(
    private val game: Game,
    private val regions: List<Region>
) : Runnable {
    override fun run() {
        var defaultWeatherChanged = false
        regions.forEach { region ->
            if (region.name == "default") game.worldDefaultWeather = GameWeather.entries.filter { it != game.worldDefaultWeather }.random()
            else {
                region.gameWeather = GameWeather.entries.filter { it != region.gameWeather }.random()
                defaultWeatherChanged = true
            }
        }

        game.marmottes.filter { (it.region == null && defaultWeatherChanged) || regions.contains(it.region) }.forEach {
            val weather = if (it.region == null) game.worldDefaultWeather else it.region!!.gameWeather
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("날씨가 ${weather.displayName} 상태로 변화했습니다.").color(NamedTextColor.GOLD),
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