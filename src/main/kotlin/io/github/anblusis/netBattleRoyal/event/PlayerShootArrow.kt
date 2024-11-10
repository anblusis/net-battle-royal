package io.github.anblusis.netBattleRoyal.event

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.metadata.FixedMetadataValue

fun playerShootArrow(listener: EventManager, event: EntityShootBowEvent) {
    event.consumable?.let { item ->
        val game = DataManager.getMarmotte(event.entity as Player)?.game ?: return

        if (item.hasExplosionPower > 0) {
            game.objects.add(ExplosionArrow(game, event.projectile))
            event.projectile.setMetadata(
                "explosionPower",
                FixedMetadataValue(plugin, item.hasExplosionPower)
            )
        }
        if (item.hasMagneticPower) {
            game.objects.add(MagneticArrow(game, event.projectile))
            event.projectile.setMetadata("magneticPower", FixedMetadataValue(plugin, true))
            event.projectile.setGravity(false)
            event.projectile.velocity.multiply(1.2)
        }
    }

}
