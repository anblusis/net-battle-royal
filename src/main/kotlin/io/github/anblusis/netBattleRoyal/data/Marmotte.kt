package io.github.anblusis.netBattleRoyal.data

import io.github.anblusis.netBattleRoyal.event.playerChangeMainHandItem
import io.github.anblusis.netBattleRoyal.event.playerChangeOffHandItem
import io.github.anblusis.netBattleRoyal.game.Game
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import io.github.anblusis.netBattleRoyal.tool.equalsDisplayName
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*


data class Marmotte(val player: Player, val game: Game) {
    private val bossBar: BossBar
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
        player.sendMessage("게임에 참가했습니다.")
        bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID).apply { addPlayer(player) }
        CustomAttribute.values().filter { it.attribute == null }.forEach { stat[it] = 0.0 }
        
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
    }


    private fun updateBossBar() {
        val allChestCount = game.chestRegionCount[region] ?: 0
        val leftChestCount = game.chests.count { it.region == region && !it.isOpened }

        val tasks = game.tasks.filter { it.regions.isEmpty() || it.regions.contains(region) }
        val firstTask = tasks.minByOrNull { it.tick - it.priority * 1200 }

        if (firstTask != null) {
            bossBar.setTitle("${region?.displayName ?: "지역 없음"} ($leftChestCount / $allChestCount) | ${firstTask.displayName}")
            bossBar.progress = firstTask.tick.toDouble() / firstTask.maxTick
        } else {
            bossBar.setTitle("${region?.displayName ?: "지역 없음"} ($leftChestCount / $allChestCount)")
            bossBar.progress = 1.0
        }
    }

    fun update() {
        updateBossBar()
        updateWeather()
        updateHandItem()
        if(tick++ >= 20) {
            tick = 0
            plugin.server.dispatchCommand(plugin.server.consoleSender, "psychics mana ${player.name} add ${stat[CustomAttribute.MANA_REGEN]}")
        }
    }

    private fun updateHandItem() {
        val newMainHandItem = player.inventory.itemInMainHand
        val newOffHandItem = player.inventory.itemInOffHand

        if (!mainHandItem.equalsDisplayName(newMainHandItem)) {
            player.sendMessage("Main hand item changed from ${mainHandItem.type} to ${newMainHandItem.type}")
            playerChangeMainHandItem(player, mainHandItem.clone(), newMainHandItem.clone())
            mainHandItem = newMainHandItem
        }

        if (!offHandItem.equalsDisplayName(newOffHandItem)) {
            player.sendMessage("Off hand item changed from ${offHandItem.type} to ${newOffHandItem.type}")
            playerChangeOffHandItem(player, offHandItem.clone(), newOffHandItem.clone())
            offHandItem = newOffHandItem.clone()
        }
    }

    private fun updateWeather() {
        val weather = region?.gameWeather?.weather ?: game.worldDefaultWeather.weather
        if (weather != player.playerWeather) player.setPlayerWeather(weather)
    }

    fun remove() {
        player.sendMessage("게임을 나갔습니다.")
        bossBar.removeAll()
        game.marmottes.remove(this)
        DataManager.removeMarmotte(this)
    }
}