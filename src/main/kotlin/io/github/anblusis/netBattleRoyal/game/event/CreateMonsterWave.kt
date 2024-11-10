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
                        isCustomNameVisible = true
                        removeWhenFarAway = false

                        game.entities.add(this)

                        plugin.ticker.runTask({
                            game.entities.remove(this)
                            if (!isDead) {
                                world.spawnParticle(Particle.SMOKE_NORMAL, location, 10, 0.5, 0.5, 0.5, 0.1)
                                world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.1f)
                                remove()
                            }
                        }, 1200L)
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
    ZOMBIE("좀비", EntityType.ZOMBIE, 300),
    SKELETON("스켈레톤", EntityType.SKELETON, 300),
    CREEPER("크리퍼", EntityType.CREEPER, 400),
    WITCH("마녀", EntityType.WITCH, 500),
    SLIME("슬라임", EntityType.SLIME, 250),
    PHANTOM("팬텀", EntityType.PHANTOM, 400),
    SILVERFISH("좀벌레", EntityType.SILVERFISH, 120),
    PILLAGER("약탈자", EntityType.PILLAGER, 350),
    VINDICATOR("변명자", EntityType.VINDICATOR, 700),
}