package io.github.anblusis.netBattleRoyal.game.event

import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.monun.tap.task.TickerTask
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.random.Random

class WorldBorderDecrease(
    private val game: Game,
    private val tick: Int
) : Runnable {
    override fun run() {
        val startCenter = game.worldBorderCenter.clone()
        val targetCenter = game.targetWorldBorderCenter.clone()
        val totalOffset = targetCenter.toVector().subtract(startCenter.toVector())

        game.worldBorder.changeSize(game.targetWorldBorderSize, tick.toLong())
        var moveTick = 0
        game.worldBorderMoveTask?.cancel()
        lateinit var task: TickerTask
        task = plugin.ticker.runTaskTimer({
            moveTick++
            val progress = (moveTick.toDouble() / tick).coerceIn(0.0, 1.0)
            game.worldBorder.center = startCenter.clone().add(
                totalOffset.x * progress,
                totalOffset.y * progress,
                totalOffset.z * progress
            )
            if (moveTick >= tick) {
                task.cancel()
                game.worldBorderMoveTask = null
            }
        }, 0L, 1L)
        game.worldBorderMoveTask = task
    }

    companion object {
        fun planNext(game: Game) {
            val sizeDecrease = game.worldBorderSize * Random.nextDouble(0.25, 0.35)
            val size = game.worldBorderSize - sizeDecrease
            val randomVector = Vector(
                Random.nextDouble(-sizeDecrease / 2, sizeDecrease / 2),
                0.0,
                Random.nextDouble(-sizeDecrease / 2, sizeDecrease / 2)
            )

            game.targetWorldBorderCenter = game.worldBorderCenter.clone().add(randomVector)
            game.targetWorldBorderSize = size
        }

        fun announceNightPreview(game: Game, tick: Int) {
            val message = mutableListOf<Component>()
            message.add(
                text("${game.day}일차 밤이 시작되었습니다. ")
                    .color(NamedTextColor.DARK_AQUA)
                    .append(text("다음 낮에 월드보더가 ${tick / 20}초에 걸쳐 감소합니다.").color(NamedTextColor.AQUA))
            )
            message.addAll(createBorderLines(game, game.targetWorldBorderCenter, game.targetWorldBorderSize))
            message.add(
                text("밤 동안 몬스터가 출현합니다.")
                    .color(NamedTextColor.RED)
            )
            broadcast(game, message)
        }

        fun announceDayStart(game: Game, tick: Int) {
            val message = mutableListOf<Component>()
            message.add(
                text("${game.day}일차 낮이 시작되었습니다. ")
                    .color(NamedTextColor.GOLD)
                    .append(text("월드보더가 ${tick / 20}초에 걸쳐 감소합니다.").color(NamedTextColor.YELLOW))
            )
            message.addAll(createBorderLines(game, game.targetWorldBorderCenter, game.targetWorldBorderSize))
            broadcast(game, message)
        }

        private fun createBorderLines(game: Game, center: Location, size: Double): List<Component> {
            val movedDistance = center.distance(game.worldBorderCenter).toInt()
            val sizeDecrease = (game.worldBorderSize - size).toInt()

            return listOf(
                text("중심 위치: ")
                    .append(text("${center.blockX}, ${center.blockZ} ").color(NamedTextColor.GREEN))
                    .append(text("(현재 중심에서 ${movedDistance} 블록 거리)")),
                text("경계 크기: ")
                    .append(text("${size.toInt()} ").color(NamedTextColor.GREEN))
                    .append(text("(현재 크기에서 ${sizeDecrease} 블록 감소)"))
            )
        }

        private fun broadcast(game: Game, messages: List<Component>) {
            game.marmottes.forEach { marmotte ->
                messages.forEach(marmotte.player::sendMessage)
            }
        }
    }
}