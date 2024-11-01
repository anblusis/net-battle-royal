package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GameWeather
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.entity.EntityType
import java.time.Duration
import kotlin.random.Random

class CreateMonsterWave(
    private val game: Game,
    private val regions: List<Region>,
    private val wave: MonsterWave
): Runnable {
    override fun run() {
        regions.forEach { region ->
            repeat(wave.count) {
                val spawnLocation = region.center.clone().apply {
                    x += (Random.nextDouble() - 0.5) * region.width
                    z += (Random.nextDouble() - 0.5) * region.height
                }
                val ableHeightNumbers = mutableListOf<Int>()
                val maxY = region.center.world.getHighestBlockYAt(spawnLocation) + 1
                var isAboveBlock = false
                repeat(maxY) {
                    val currentLocation = spawnLocation.clone().apply { y = (it + 1).toDouble() }
                    if (currentLocation.block.type.isSolid) {
                        isAboveBlock = true
                    } else if (isAboveBlock) {
                        ableHeightNumbers.add(it + 1)
                    } else {
                        isAboveBlock = false
                    }
                }
                if (ableHeightNumbers.isNotEmpty()) spawnLocation.y = ableHeightNumbers.random().toDouble()
                else spawnLocation.y = maxY.toDouble()

                game.world.spawnEntity(spawnLocation, wave.type)
            }
        }

        game.marmottes.filter { it.region in regions }.forEach {
            val player = it.player
            player.showTitle(
                Title.title(
                    text(""),
                    text("몬스터가 스폰됩니다.").color(NamedTextColor.GOLD),
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

enum class MonsterWave(val displayName: String, val type: EntityType, val count: Int) {
    ZOMBIE("좀비", EntityType.ZOMBIE, 20),
    SKELETON("스켈레톤", EntityType.SKELETON, 20),
    CREEPER("크리퍼", EntityType.CREEPER, 20),
    WITCH("마녀", EntityType.WITCH, 15),
    SLIME("슬라임", EntityType.SLIME, 30),
    PHANTOM("팬텀", EntityType.PHANTOM, 15),
    SILVERFISH("좀벌레", EntityType.SILVERFISH, 50),
    PILLAGER("약탈자", EntityType.PILLAGER, 20),
    VINDICATOR("변명자", EntityType.VINDICATOR, 12),
}