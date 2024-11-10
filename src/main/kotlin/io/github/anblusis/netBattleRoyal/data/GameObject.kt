package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.game.Game
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustTransition
import org.bukkit.entity.Entity

abstract class GameObject(private val game: Game, val entity: Entity) {
    open fun onUpdate() {
        if (!entity.isValid) onRemove()
    }

    open fun onRemove() {
        game.objects.remove(this)
    }
}

class ExplosionArrow(game: Game, entity: Entity): GameObject(game, entity) {
    override fun onUpdate() {
        super.onUpdate()
        entity.world.spawnParticle(Particle.DUST_COLOR_TRANSITION, entity.location, 1, 0.0, 0.0, 0.0, 0.0, DustTransition(Color.RED, Color.RED, 1.0f))
    }
}

class MagneticArrow(game: Game, entity: Entity): GameObject(game, entity) {
    override fun onUpdate() {
        super.onUpdate()
        entity.world.spawnParticle(Particle.DUST_COLOR_TRANSITION, entity.location, 1, 0.0, 0.0, 0.0, 0.0, DustTransition(Color.BLUE, Color.BLUE, 1.0f))
    }
}