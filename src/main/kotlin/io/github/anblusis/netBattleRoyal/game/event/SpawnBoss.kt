package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import java.time.Duration
import kotlin.random.Random

class SpawnBoss(
    private val game: Game,
    private val region: Region,
    private val boss: BossType
) : Runnable {
    override fun run() {
        game.marmottes.filter { it.region == region }.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("등장").color(NamedTextColor.GOLD),
                    Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(2),
                        Duration.ofMillis(500)
                    )
                )
            )
        }

        val loc = region.center.clone().apply {
            x += (Random.nextDouble() - 0.5) * region.width
            z += (Random.nextDouble() - 0.5) * region.height
            y = region.center.world.getHighestBlockYAt(this).toDouble() + 1.0
        }
        boss.spawnAt(game, loc)
    }
}
