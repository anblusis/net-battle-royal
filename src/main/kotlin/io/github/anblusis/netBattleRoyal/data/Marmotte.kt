package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.event.playerChangeMainHandItem
import io.github.anblusis.netBattleRoyal.event.playerChangeOffHandItem
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.game.GamePhase
import io.github.anblusis.netBattleRoyal.game.GameState
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*


data class Marmotte(val player: Player, val game: Game) {
    private val bossBar: BossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID).apply { addPlayer(player) }
    private var tick: Int = 0
    val stat: EnumMap<CustomAttribute, Double> = EnumMap(CustomAttribute::class.java)
    private var mainHandItem: ItemStack = ItemStack(Material.AIR)
    private var offHandItem: ItemStack = ItemStack(Material.AIR)

    val region: Region?
        get() {
            val regions = game.regions.filter { game.isInRegion(it, player.location) }
            return regions.maxByOrNull { it.priority }
        }

    init {
        // player.sendMessage("게임에 참가했습니다.")
        CustomAttribute.entries.filter { it.attribute == null }.forEach { stat[it] = 0.0 }
        player.exp = 0f
        player.level = 0

        /* 이거 가끔 버그 걸렸을 때 Attribute 초기화용
        for (attribute in Attribute.values()) {
            val attributeInstance = player.getAttribute(attribute)
            
            if (attributeInstance != null) {
                val modifiers = ArrayList(attributeInstance.modifiers)
                
                for (modifier in modifiers) {
                    attributeInstance.removeModifier(modifier!!)
                }
            }
        }
         */

        CustomRecipe.entries.forEach {
            player.discoverRecipe(it.key)
        }
    }

    private fun updateBossBar() {
        val regionPlayerCount = if (game.phase == GamePhase.NIGHT) "?"
            else (game.marmottes.count { it.region == region && it.player.gameMode != GameMode.SPECTATOR } - 1).toString()

        val tasks = game.tasks.filter { it.regions.isEmpty() || it.regions.contains(region) }
        val firstTask = tasks.minByOrNull { it.tick - it.priority * 1200 }

        if (firstTask != null) {
            bossBar.setTitle("${game.day}일차 ${game.phaseDisplayName} | ${region?.displayName ?: "지역 없음"} (${regionPlayerCount}명)  | ${firstTask.displayName}")
            bossBar.progress = firstTask.tick.toDouble() / firstTask.maxTick
        } else {
            bossBar.setTitle("${game.day}일차 ${game.phaseDisplayName} | ${region?.displayName ?: "지역 없음"} (${regionPlayerCount}명)")
            bossBar.progress = game.phaseProgress
        }
        bossBar.color = game.phase.barColor
    }

    fun update() {
        updateBossBar()
        updateWeather()
        if (player.gameMode == GameMode.SPECTATOR) return
        updateHandItem()

        if (game.state == GameState.READYING)
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 3, 1, false, false))

        if (tick++ >= 20) {
            tick = 0
            plugin.server.dispatchCommand(
                plugin.server.consoleSender,
                "psychics mana ${player.name} add ${stat[CustomAttribute.MANA_REGEN]}"
            )
            val maxHealth = player.getAttribute(Attribute.MAX_HEALTH)!!.value
            player.health = (player.health + (stat[CustomAttribute.HEALTH_REGEN] ?: 0.0)).coerceAtMost(maxHealth)
        }
    }

    private fun updateHandItem() {
        val newMainHandItem = player.inventory.itemInMainHand
        val newOffHandItem = player.inventory.itemInOffHand

        if (!mainHandItem.equalsDisplayName(newMainHandItem)) {
            playerChangeMainHandItem(player, mainHandItem.clone(), newMainHandItem.clone())
            mainHandItem = newMainHandItem
        }

        if (!offHandItem.equalsDisplayName(newOffHandItem)) {
            playerChangeOffHandItem(player, offHandItem.clone(), newOffHandItem.clone())
            offHandItem = newOffHandItem
        }
    }

    private fun updateWeather() {
        val weather = region?.gameWeather?.weather ?: game.worldDefaultWeather.weather
        if (weather != player.playerWeather) player.setPlayerWeather(weather)
    }

    fun remove() {
        bossBar.removeAll()
        game.marmottes.remove(this)
        DataManager.removeMarmotte(this)
    }
}