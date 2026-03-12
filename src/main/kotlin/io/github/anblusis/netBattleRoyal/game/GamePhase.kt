package io.github.anblusis.netBattleRoyal.game

import org.bukkit.boss.BarColor

enum class GamePhase(
    val displayName: String,
    val barColor: BarColor,
    val startWorldTime: Long,
    val endWorldTime: Long
) {
    DAY("낮", BarColor.YELLOW, 1000L, 12000L),
    NIGHT("밤", BarColor.BLUE, 13000L, 23000L)
}
