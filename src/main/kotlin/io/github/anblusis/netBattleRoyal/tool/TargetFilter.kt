package io.github.anblusis.netBattleRoyal.tool

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import java.util.function.Predicate

/*
Monun 님의 Psychics에 있는 TargetFilter 클래스를 가져왔습니다.
 */

class TargetFilter(
    private val entity: LivingEntity,
    private val team: Team? = Bukkit.getScoreboardManager().mainScoreboard.getEntityTeam(entity)
) : Predicate<Entity> {
    private var hostile = true

    override fun test(t: Entity): Boolean {
        if (t === entity) return false

        if (t is LivingEntity && t.isValid && t.health > 0.0) {
            if (t is Player) {
                val gameMode = t.gameMode
                if (gameMode == GameMode.SPECTATOR || gameMode == GameMode.CREATIVE) return false
            }

            val team = team ?: return hostile
            val result = team.hasEntity(t)
            return if (hostile) !result else result
        }

        return false
    }

    fun friendly() {
        hostile = false
    }
}

fun LivingEntity.friendlyFilter() = TargetFilter(this).apply { friendly() }

fun LivingEntity.hostileFilter() = TargetFilter(this)