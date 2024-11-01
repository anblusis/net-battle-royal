package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameTask
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import java.time.Duration
import kotlin.random.Random

class CreateTntRain(
    private val game: Game,
    private val region: Region
): Runnable {
    override fun run() {
        // sqrt(200) m^2당 tnt 1개
        repeat((region.height * region.width / 200).toInt()) {
            val maxY = region.center.world.maxHeight
            val spawnLocation = region.center.clone().apply {
                x += (Random.nextDouble() - 0.5) * region.width
                y = maxY.toDouble()
                z += (Random.nextDouble() - 0.5) * region.height
            }

            (game.world.spawnEntity(spawnLocation, EntityType.PRIMED_TNT) as TNTPrimed).apply {
                // 중력 가속도 0.08, 공기 저항 0.98: 0.08 = 0.02v, v = 4
                val finalVelocity = 4
                val distance = maxY - world.getHighestBlockYAt(spawnLocation)
                val tick = distance / finalVelocity * 20
                // 3초의 유예 시간
                fuseTicks = tick + 60
            }
        }
    }
}