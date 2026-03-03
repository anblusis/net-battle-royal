package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.data.Region
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Monster
import java.time.Duration
import kotlin.random.Random

class CreateMonsterWave(
    private val game: Game,
    private val regions: List<Region>,
    private val wave: MonsterWave
) : Runnable {
    override fun run() {
        regions.forEach { region ->
            repeat((region.width * region.height / wave.density).toInt()) {
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
                        isAboveBlock = false
                    }
                }
                ableHeightNumbers.shuffle()
                repeat(Random.nextInt(ableHeightNumbers.count()) + 1) {
                    spawnLocation.y = ableHeightNumbers[it].toDouble()
                    (game.world.spawnEntity(spawnLocation, wave.type) as Monster).apply {
                        customName(text(wave.displayName).color(NamedTextColor.RED))
                        removeWhenFarAway = false

                        game.entities.add(this)

                        plugin.ticker.runTask({
                            game.entities.remove(this)
                            if (!isDead) {
                                world.spawnParticle(Particle.SMOKE, location, 10, 0.5, 0.5, 0.5, 0.1)
                                world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.1f)
                                remove()
                            }
                        }, 2800L)
                    }
                }
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

enum class MonsterWave(val displayName: String, val type: EntityType, val density: Int) {
    ZOMBIE("좀비", EntityType.ZOMBIE, 200),
    SKELETON("스켈레톤", EntityType.SKELETON, 200),
    CREEPER("크리퍼", EntityType.CREEPER, 250),
    WITCH("마녀", EntityType.WITCH, 320),
    SLIME("슬라임", EntityType.SLIME, 160),
    PHANTOM("팬텀", EntityType.PHANTOM, 300),
    SILVERFISH("좀벌레", EntityType.SILVERFISH, 80),
    PILLAGER("약탈자", EntityType.PILLAGER, 230),
    VINDICATOR("변명자", EntityType.VINDICATOR, 350),
}