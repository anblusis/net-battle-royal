package io.github.anblusis.netBattleRoyal.game.event

import kotlin.random.Random

enum class RandomGameEvent(val weight: Int) {
    EPIC_CHEST(18),
    CHANGE_WEATHER(18),
    MONSTER_WAVE(7),
    TNT_RAIN(12),
    WANDERING_TRADER(14),
    BOSS_SPAWN(9);

    companion object {
        fun weightedRandom(events: Array<RandomGameEvent>): RandomGameEvent? {
            if (events.isEmpty()) return null

            val totalWeight = events.sumOf { it.weight }
            if (totalWeight <= 0) return events.randomOrNull()

            var picked = Random.nextInt(totalWeight)
            events.forEach { event ->
                picked -= event.weight
                if (picked < 0) return event
            }

            return events.last()
        }
    }
}