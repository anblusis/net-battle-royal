package io.github.anblusis.netBattleRoyal.event

import org.bukkit.event.entity.ProjectileHitEvent

fun arrowHit(listener: EventManager, event: ProjectileHitEvent) {
    if (event.entity.getMetadata("explosionPower").isNotEmpty()) {
        val power = 0.5f + (event.entity.getMetadata("explosionPower")[0].asFloat() / 4)
        event.entity.world.createExplosion(event.entity.location, power)
        event.entity.remove()
    }
}
