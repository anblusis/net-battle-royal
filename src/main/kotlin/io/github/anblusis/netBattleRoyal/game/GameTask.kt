package io.github.anblusis.netBattleRoyal.game

import io.github.anblusis.netBattleRoyal.data.Region

data class GameTask(
    val game: Game,
    val task: Runnable,
    val displayName: String,
    var tick: Int,
    val maxTick: Int,
    val priority: Int,
    val canRestart: Boolean,
    val isVisible: Boolean,
    val regions: List<Region> = listOf()
) {
    constructor(
        game: Game,
        task: Runnable,
        displayName: String,
        tick: Int,
        priority: Int,
        canRestart: Boolean,
        isVisible: Boolean,
        regions: List<Region> = listOf()
    ) : this(game, task, displayName, tick, tick, priority, canRestart, isVisible, regions)

    fun run() {
        task.run()
    }
}