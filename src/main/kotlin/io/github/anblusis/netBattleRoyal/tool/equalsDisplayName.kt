package io.github.anblusis.netBattleRoyal.tool

import net.kyori.adventure.text.TextComponent
import org.bukkit.inventory.ItemStack

fun ItemStack.equalsDisplayName(item: ItemStack): Boolean {
    var hasDisplay1 = false
    var hasDisplay2 = false

    if (this.hasItemMeta() && this.itemMeta.hasDisplayName()) hasDisplay1 = true
    if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) hasDisplay2 = true

    if (!hasDisplay1 && !hasDisplay2) return this.type == item.type // 둘다 display name이 없으면 type으로 비교

    if (!(hasDisplay1 && hasDisplay2)) return false // 둘 중 하나만 display name이 있으면 false

    val displayName1 = this.itemMeta.displayName() as TextComponent
    val displayName2 = item.itemMeta.displayName() as TextComponent

    return displayName1.content() == displayName2.content()
}