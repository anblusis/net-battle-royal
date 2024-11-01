package io.github.anblusis.netBattleRoyal.world

import io.github.anblusis.netBattleRoyal.data.*
import io.github.anblusis.netBattleRoyal.game.GameWeather
import io.github.anblusis.netBattleRoyal.main.NetBattleRoyal.Companion.plugin
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType
import java.io.InputStream

object City : WorldData {
    private val world = plugin.server.getWorld("world")

    override fun getCenter(): Location =
        Location(world, -291.50, 0.0, 821.50)

    override fun getWorldDefaultWeather(): GameWeather =
        GameWeather.SUNNY

    override fun getWorldBorderSize(): Double =
        500.0

    override fun getChestCount(): Int =
        1000

    override fun getChestLocations(): List<ChestData> =
        listOf(
            ChestData(
                Location(world, -282.0, 35.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -89.0, 21.0, 926.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -326.0, 54.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 48.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -205.0, 22.0, 968.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -310.0, 21.0, 780.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 39.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -393.0, 22.0, 877.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 58.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 29.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 34.0, 869.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 53.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -237.0, 22.0, 760.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -273.0, 25.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -84.0, 39.0, 942.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -79.0, 44.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 20.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 48.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -63.0, 17.0, 815.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 68.0, 744.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -317.0, 35.0, 784.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -489.0, 36.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 34.0, 871.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -492.0, 24.0, 837.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -419.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -160.0, 35.0, 714.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -328.0, 20.0, 796.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -518.0, 54.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -417.0, 34.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -426.0, 21.0, 1027.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -244.0, 22.0, 848.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -331.0, 29.0, 884.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 48.0, 922.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -196.0, 23.0, 864.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -128.0, 33.0, 1008.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 37.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -157.0, 29.0, 882.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 44.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -190.0, 45.0, 980.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 68.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 38.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 38.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -389.0, 21.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -256.0, 31.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -466.0, 21.0, 1031.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 25.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -420.0, 21.0, 1008.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -106.0, 14.0, 928.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 29.0, 889.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -466.0, 21.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 58.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -251.0, 26.0, 701.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 38.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -168.0, 28.0, 841.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -474.0, 48.0, 922.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -157.0, 22.0, 757.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -299.0, 15.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 37.0, 921.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -244.0, 30.0, 910.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -320.0, 38.0, 752.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -242.0, 59.0, 976.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -48.0, 15.0, 698.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -67.0, 18.0, 844.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -239.0, 23.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 23.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -61.0, 17.0, 675.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -492.0, 24.0, 813.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -127.0, 22.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -426.0, 21.0, 931.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 18.0, 724.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -424.0, 81.0, 851.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -222.0, 22.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -250.0, 47.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -398.0, 26.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -63.0, 17.0, 870.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 33.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -272.0, 22.0, 998.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -106.0, 14.0, 978.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -275.0, 50.0, 880.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -101.0, 33.0, 971.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -382.0, 21.0, 1031.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 58.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -149.0, 29.0, 980.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -50.0, 15.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -271.0, 73.0, 739.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -249.0, 33.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -260.0, 25.0, 980.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 23.0, 871.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -140.0, 35.0, 714.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -348.0, 22.0, 604.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -223.0, 29.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -120.0, 48.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -282.0, 47.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -433.0, 28.0, 864.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -132.0, 24.0, 730.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -120.0, 36.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -188.0, 22.0, 949.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -84.0, 21.0, 942.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 59.0, 837.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -180.0, 28.0, 983.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 30.0, 870.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -412.0, 42.0, 792.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 28.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -127.0, 27.0, 836.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -323.0, 117.0, 946.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -210.0, 58.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -387.0, 27.0, 694.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 48.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -217.0, 31.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -433.0, 58.0, 743.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -522.0, 22.0, 848.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -266.0, 34.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -265.0, 31.0, 1021.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -428.0, 40.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 28.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 36.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 53.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 22.0, 735.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -410.0, 18.0, 774.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -489.0, 43.0, 921.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 53.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -92.0, 21.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -301.0, 15.0, 878.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -223.0, 22.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 68.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 23.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 58.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -423.0, 20.0, 793.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -275.0, 32.0, 760.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 28.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 22.0, 863.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 22.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 22.0, 743.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -262.0, 26.0, 713.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -496.0, 22.0, 683.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -262.0, 22.0, 851.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 28.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 58.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -139.0, 32.0, 1064.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 48.0, 843.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -541.0, 22.0, 1054.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -422.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -225.0, 58.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -464.0, 28.0, 1031.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -94.0, 30.0, 723.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -78.0, 34.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -275.0, 34.0, 871.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -416.0, 29.0, 720.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -59.0, 22.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -529.0, 63.0, 869.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -352.0, 18.0, 774.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 21.0, 1008.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -228.0, 50.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -411.0, 34.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -340.0, 24.0, 787.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 38.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -122.0, 22.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 27.0, 836.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 29.0, 880.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -318.0, 35.0, 724.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -322.0, 28.0, 751.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -266.0, 25.0, 974.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 45.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -515.0, 47.0, 892.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -228.0, 39.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -115.0, 48.0, 859.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -277.0, 58.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -220.0, 23.0, 846.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 38.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 26.0, 1008.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -167.0, 28.0, 812.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 38.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -127.0, 21.0, 593.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -84.0, 27.0, 942.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -50.0, 16.0, 644.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -120.0, 37.0, 716.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -43.0, 21.0, 572.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -211.0, 22.0, 945.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -222.0, 22.0, 889.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -498.0, 39.0, 886.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -232.0, 31.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -496.0, 48.0, 877.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -430.0, 27.0, 726.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 30.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -410.0, 26.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -168.0, 33.0, 686.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -234.0, 22.0, 760.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -217.0, 30.0, 840.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -335.0, 20.0, 712.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 45.0, 886.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -246.0, 26.0, 685.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -501.0, 45.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 28.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -90.0, 21.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 43.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -330.0, 29.0, 950.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -318.0, 23.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 43.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -501.0, 22.0, 715.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 31.0, 989.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -433.0, 22.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -415.0, 28.0, 797.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -541.0, 22.0, 1050.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -155.0, 43.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -192.0, 22.0, 949.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 43.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -149.0, 32.0, 587.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 23.0, 847.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 22.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -398.0, 26.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -266.0, 23.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -130.0, 30.0, 733.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -62.0, 17.0, 760.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -495.0, 22.0, 712.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -206.0, 19.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 39.0, 886.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -324.0, 101.0, 979.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -250.0, 41.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -127.0, 43.0, 730.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -105.0, 50.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 43.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -244.0, 47.0, 584.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 56.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -105.0, 50.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -352.0, 27.0, 694.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -397.0, 51.0, 652.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -491.0, 43.0, 964.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -326.0, 20.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 58.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 32.0, 889.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -114.0, 54.0, 888.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -422.0, 81.0, 849.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -541.0, 21.0, 934.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -408.0, 26.0, 1023.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -437.0, 21.0, 651.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -277.0, 68.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 23.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 44.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -168.0, 24.0, 730.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -206.0, 19.0, 986.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 33.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -137.0, 28.0, 944.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -376.0, 22.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -136.0, 27.0, 808.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -254.0, 23.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -160.0, 22.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -133.0, 30.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -81.0, 14.0, 942.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -396.0, 21.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -381.0, 58.0, 695.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -331.0, 112.0, 955.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -331.0, 117.0, 953.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -379.0, 18.0, 716.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -242.0, 22.0, 964.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -135.0, 36.0, 889.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -499.0, 28.0, 690.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 53.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -424.0, 21.0, 978.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -226.0, 22.0, 988.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 21.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -167.0, 30.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 36.0, 1023.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -59.0, 27.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -166.0, 22.0, 763.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -410.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -489.0, 55.0, 1031.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -242.0, 26.0, 962.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -190.0, 30.0, 951.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -170.0, 22.0, 757.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 22.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -487.0, 22.0, 878.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 23.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -508.0, 53.0, 977.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -404.0, 26.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -384.0, 21.0, 999.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 27.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -155.0, 27.0, 808.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -53.0, 16.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 58.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 58.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -279.0, 50.0, 972.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 36.0, 889.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -139.0, 28.0, 925.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 63.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -239.0, 38.0, 974.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 43.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 22.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 53.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 43.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -392.0, 34.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -229.0, 63.0, 733.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -331.0, 30.0, 796.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -325.0, 22.0, 983.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -501.0, 28.0, 715.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 53.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -327.0, 21.0, 741.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -323.0, 117.0, 962.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -433.0, 34.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -173.0, 22.0, 750.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 36.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -331.0, 23.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 32.0, 716.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -411.0, 47.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -229.0, 25.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -503.0, 38.0, 820.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -192.0, 45.0, 1006.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -52.0, 16.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -522.0, 32.0, 851.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -152.0, 49.0, 926.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -541.0, 22.0, 1052.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -408.0, 21.0, 716.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -531.0, 49.0, 885.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -419.0, 34.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -179.0, 47.0, 1062.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -282.0, 41.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 48.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -280.0, 22.0, 838.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -193.0, 52.0, 1025.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -115.0, 22.0, 748.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 43.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 37.0, 990.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -390.0, 34.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -502.0, 23.0, 915.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -429.0, 34.0, 847.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 52.0, 843.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -416.0, 72.0, 844.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 23.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -276.0, 34.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -509.0, 43.0, 921.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -157.0, 22.0, 881.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -117.0, 27.0, 808.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -131.0, 22.0, 735.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 22.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 28.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 52.0, 857.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -84.0, 44.0, 942.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -321.0, 29.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -516.0, 63.0, 853.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -530.0, 24.0, 818.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -102.0, 27.0, 926.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 30.0, 885.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -319.0, 29.0, 783.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -515.0, 28.0, 669.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 29.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -413.0, 38.0, 646.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -125.0, 22.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -503.0, 33.0, 671.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -323.0, 107.0, 953.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -520.0, 21.0, 576.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -499.0, 34.0, 699.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -271.0, 73.0, 781.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -427.0, 28.0, 856.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -55.0, 22.0, 640.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -514.0, 22.0, 888.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 22.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -274.0, 42.0, 868.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 28.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -306.0, 29.0, 895.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -434.0, 22.0, 787.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 22.0, 704.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -347.0, 15.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -51.0, 15.0, 1023.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -394.0, 22.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 42.0, 912.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -186.0, 33.0, 882.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -396.0, 21.0, 1002.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -141.0, 33.0, 925.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -166.0, 28.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 28.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -384.0, 28.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -523.0, 23.0, 915.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -509.0, 37.0, 921.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -504.0, 63.0, 838.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -480.0, 23.0, 896.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 28.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -510.0, 45.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -177.0, 37.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -379.0, 18.0, 774.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -134.0, 22.0, 937.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -384.0, 28.0, 1031.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -177.0, 30.0, 733.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -317.0, 35.0, 752.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -352.0, 18.0, 747.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -389.0, 47.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 38.0, 820.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -278.0, 38.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -204.0, 62.0, 652.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 33.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 39.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -501.0, 39.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -235.0, 70.0, 613.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 44.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -419.0, 21.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -388.0, 34.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 48.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -180.0, 37.0, 706.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -489.0, 37.0, 921.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 43.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 38.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 53.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 34.0, 874.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 68.0, 1020.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -259.0, 43.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -326.0, 29.0, 717.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -331.0, 29.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -236.0, 51.0, 648.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -345.0, 25.0, 633.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 38.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -120.0, 37.0, 706.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -317.0, 22.0, 575.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -277.0, 33.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -272.0, 25.0, 980.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -483.0, 47.0, 913.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 23.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -498.0, 45.0, 886.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 48.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -229.0, 63.0, 787.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -326.0, 35.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 33.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -400.0, 18.0, 824.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -78.0, 39.0, 985.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 36.0, 877.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -69.0, 18.0, 891.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 48.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -43.0, 15.0, 842.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 22.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 30.0, 704.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 29.0, 877.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -537.0, 49.0, 1037.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -507.0, 39.0, 1042.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -187.0, 22.0, 881.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -272.0, 22.0, 690.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -61.0, 33.0, 976.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -408.0, 21.0, 1023.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -278.0, 53.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -220.0, 23.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -417.0, 18.0, 789.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 29.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 45.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -165.0, 27.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 33.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -190.0, 45.0, 951.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 25.0, 993.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -404.0, 21.0, 794.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 23.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -352.0, 18.0, 716.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -226.0, 23.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -522.0, 47.0, 913.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -173.0, 39.0, 704.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -433.0, 28.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 43.0, 921.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -169.0, 22.0, 735.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -159.0, 23.0, 837.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -421.0, 25.0, 765.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -206.0, 49.0, 976.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 33.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 28.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -501.0, 28.0, 671.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -231.0, 49.0, 1022.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -411.0, 18.0, 747.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -268.0, 22.0, 743.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 68.0, 776.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -517.0, 24.0, 811.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -522.0, 39.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 23.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 29.0, 740.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -413.0, 21.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -251.0, 21.0, 1044.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 63.0, 745.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -68.0, 19.0, 916.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 22.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 39.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -423.0, 38.0, 671.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -150.0, 22.0, 719.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -517.0, 23.0, 673.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -432.0, 29.0, 775.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -279.0, 49.0, 986.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 28.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 23.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -427.0, 28.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 37.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -199.0, 28.0, 960.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 42.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -90.0, 27.0, 977.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -384.0, 21.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -482.0, 45.0, 1001.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -222.0, 34.0, 1055.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 23.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -509.0, 32.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -120.0, 29.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -157.0, 36.0, 882.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -379.0, 28.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -331.0, 23.0, 884.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -412.0, 40.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -392.0, 26.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -392.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -395.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -178.0, 55.0, 1021.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -175.0, 29.0, 815.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -498.0, 39.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -413.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -142.0, 23.0, 684.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 28.0, 960.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -45.0, 15.0, 755.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -420.0, 26.0, 1008.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -324.0, 46.0, 743.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -522.0, 45.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -88.0, 33.0, 971.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -382.0, 34.0, 874.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -270.0, 37.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -213.0, 37.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 53.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 68.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -339.0, 24.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 63.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -518.0, 54.0, 828.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -173.0, 43.0, 730.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -342.0, 117.0, 946.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 47.0, 893.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -79.0, 50.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 38.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 22.0, 704.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -250.0, 23.0, 877.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -149.0, 42.0, 653.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -389.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -59.0, 16.0, 794.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -473.0, 22.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -460.0, 21.0, 986.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -271.0, 31.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -155.0, 38.0, 1007.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -273.0, 22.0, 945.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -256.0, 23.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -200.0, 49.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -519.0, 21.0, 632.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 23.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -518.0, 54.0, 844.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -278.0, 43.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -94.0, 30.0, 869.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -513.0, 58.0, 1000.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -181.0, 22.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -226.0, 23.0, 834.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -166.0, 33.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -538.0, 21.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -133.0, 22.0, 871.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 46.0, 967.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 30.0, 704.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 36.0, 911.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -63.0, 17.0, 718.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -120.0, 43.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -43.0, 21.0, 1070.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -157.0, 22.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -274.0, 34.0, 882.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -147.0, 23.0, 940.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 22.0, 882.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -263.0, 20.0, 977.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -236.0, 22.0, 995.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -416.0, 72.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -421.0, 58.0, 768.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -503.0, 54.0, 812.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 23.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 68.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -389.0, 40.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -187.0, 27.0, 890.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -510.0, 22.0, 848.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -423.0, 22.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 33.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -426.0, 81.0, 853.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -400.0, 28.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -331.0, 23.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 43.0, 722.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -95.0, 34.0, 1009.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -406.0, 26.0, 706.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 31.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -91.0, 15.0, 946.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -331.0, 29.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -401.0, 34.0, 873.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -373.0, 28.0, 846.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -119.0, 43.0, 888.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -373.0, 46.0, 800.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -357.0, 21.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -203.0, 39.0, 1026.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -307.0, 21.0, 616.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -94.0, 22.0, 573.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 38.0, 661.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -68.0, 18.0, 793.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 33.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -500.0, 39.0, 829.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -101.0, 34.0, 981.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -477.0, 21.0, 935.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -327.0, 21.0, 771.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -409.0, 34.0, 876.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -416.0, 26.0, 966.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -237.0, 87.0, 611.0),
                ChestType.EPIC
            ),
            ChestData(
                Location(world, -319.0, 33.0, 668.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -56.0, 27.0, 972.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -174.0, 22.0, 796.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -412.0, 30.0, 821.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -212.0, 25.0, 977.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -421.0, 22.0, 1003.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -166.0, 43.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 21.0, 1023.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -342.0, 36.0, 946.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -206.0, 25.0, 978.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -499.0, 38.0, 682.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -464.0, 28.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 64.0, 868.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -49.0, 14.0, 1023.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -78.0, 21.0, 985.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -388.0, 34.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 39.0, 885.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 22.0, 882.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -340.0, 38.0, 800.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -411.0, 21.0, 953.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 32.0, 889.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -434.0, 27.0, 748.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -328.0, 66.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -321.0, 91.0, 981.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -47.0, 15.0, 812.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -121.0, 21.0, 1063.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -498.0, 22.0, 814.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -326.0, 42.0, 984.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -404.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -250.0, 53.0, 858.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -168.0, 29.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -503.0, 24.0, 814.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -106.0, 27.0, 926.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -225.0, 43.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -425.0, 28.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -107.0, 21.0, 956.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -475.0, 48.0, 862.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -123.0, 21.0, 962.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -44.0, 15.0, 782.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -219.0, 83.0, 623.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -242.0, 22.0, 945.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -382.0, 21.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -46.0, 15.0, 670.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -277.0, 63.0, 798.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -497.0, 46.0, 810.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -238.0, 40.0, 888.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -249.0, 48.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -373.0, 28.0, 807.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -268.0, 22.0, 777.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -169.0, 37.0, 729.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -510.0, 32.0, 851.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -338.0, 22.0, 948.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -407.0, 22.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 64.0, 843.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -242.0, 32.0, 686.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -225.0, 33.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -47.0, 14.0, 1022.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -340.0, 20.0, 808.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -522.0, 53.0, 1030.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -208.0, 35.0, 893.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -45.0, 22.0, 934.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -473.0, 22.0, 899.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -118.0, 43.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -246.0, 29.0, 887.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -242.0, 32.0, 969.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -207.0, 38.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -127.0, 39.0, 704.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -334.0, 46.0, 791.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -352.0, 58.0, 695.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -520.0, 32.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -495.0, 23.0, 895.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -335.0, 20.0, 720.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -114.0, 22.0, 859.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -155.0, 43.0, 884.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -87.0, 21.0, 1058.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -504.0, 66.0, 1019.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -530.0, 54.0, 812.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -407.0, 26.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -398.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -483.0, 42.0, 914.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -278.0, 63.0, 775.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -126.0, 22.0, 763.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 48.0, 799.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 34.0, 873.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -259.0, 63.0, 721.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -416.0, 21.0, 964.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -160.0, 30.0, 910.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 34.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -270.0, 20.0, 982.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -224.0, 40.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -166.0, 38.0, 927.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -84.0, 33.0, 942.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 60.0, 843.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -207.0, 45.0, 892.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -157.0, 29.0, 884.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -54.0, 19.0, 921.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -433.0, 22.0, 872.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 23.0, 983.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 28.0, 734.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 29.0, 867.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -135.0, 29.0, 889.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -316.0, 20.0, 716.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -414.0, 40.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -313.0, 15.0, 866.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -81.0, 50.0, 939.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -204.0, 42.0, 577.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -386.0, 34.0, 853.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -373.0, 40.0, 875.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -55.0, 31.0, 1017.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -112.0, 36.0, 859.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -242.0, 54.0, 991.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -268.0, 46.0, 871.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -368.0, 40.0, 843.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -141.0, 22.0, 803.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -402.0, 23.0, 987.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -379.0, 18.0, 747.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -498.0, 39.0, 713.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -249.0, 53.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -282.0, 53.0, 860.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -373.0, 28.0, 844.0),
                ChestType.RARE
            ),
            ChestData(
                Location(world, -225.0, 48.0, 786.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -321.0, 85.0, 981.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -228.0, 37.0, 973.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -257.0, 30.0, 820.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -330.0, 21.0, 616.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -93.0, 33.0, 643.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -510.0, 39.0, 855.0),
                ChestType.NORMAL
            ),
            ChestData(
                Location(world, -425.0, 22.0, 1003.0),
                ChestType.NORMAL
            ),
        )


    override fun getRegions(): List<Region> =
        listOf(
            Region("school", "학교", Location(world, -228.0, 0.0, 960.0), 108.0, 83.0, 2),
            Region("baseballStadium", "야구장", Location(world, -371.5, 0.0, 750.5), 129.0, 119.0, 2),
            Region("apartment", "아파트", Location(world, -246.0, 0.0, 742.0), 66.0, 132.0, 2),
            Region("mansion", "대저택", Location(world, -149.0, 0.0, 732.5), 70.0, 69.0, 2),
            Region("sandBeach", "모래사장", Location(world, -48.0, 0.0, 789.0), 46.0, 269.0, 1),
            Region("hospital", "병원", Location(world, -133.5, 0.0, 874.0), 49.0, 34.0, 2),
            Region("mart", "마트", Location(world, -92.0, 0.0, 954.0), 32.0, 64.0, 2),
            Region("store", "상가", Location(world, -242.5, 0.0, 863.0), 79.0, 64.0, 2),
            Region("prison", "교도소", Location(world, -423.0, 0.0, 989.5), 86.0, 89.0, 2),
            Region("cityHall", "시청", Location(world, -400.5, 0.0, 864.0), 69.0, 44.0, 2),
            Region("culturalCenter", "문화 센터", Location(world, -501.5, 0.0, 866.0), 59.0, 114.0, 2),
        )

    override fun getChestTables(): HashMap<ChestType, ChestLootTable> =
        hashMapOf(
            ChestType.NORMAL to
                    ChestLootTable(
                        listOf(
                            1..2,
                            2..3
                        ),
                        listOf(
                            ChestItemData(ItemStack(Material.OAK_PLANKS), 12..24, 1..2, 12.0),
                            ChestItemData(ItemStack(Material.COBBLESTONE), 8..12, 1..2, 10.0),
                            ChestItemData(ItemStack(Material.WHITE_WOOL), 16..16, 1..2, 6.0),
                            ChestItemData(ItemStack(Material.STICK), 12..16, 1..2, 7.0),

                            ChestItemData(ItemStack(Material.BREAD), 3..4, 2..3, 7.5),
                            ChestItemData(ItemStack(Material.APPLE), 1..2, 2..3, 3.0),
                            ChestItemData(ItemStack(Material.COOKED_BEEF), 2..5, 2..3, 4.5),
                            ChestItemData(ItemStack(Material.RAW_IRON), 4..6, 2..3, 4.0),
                            ChestItemData(ItemStack(Material.IRON_INGOT), 3..5, 2..3, 3.0),
                            ChestItemData(ItemStack(Material.DIAMOND), 1..3, 2..3, 1.0),
                            ChestItemData(ItemStack(Material.STRING), 3..8, 2..3, 6.0),
                            ChestItemData(ItemStack(Material.GOLD_INGOT), 4..6, 2..3, 2.5),
                            ChestItemData(ItemStack(Material.LEATHER), 4..8, 2..3, 7.0),
                            ChestItemData(ItemStack(Material.DAMAGED_ANVIL), 1..1, 2..3, 1.0),
                            ChestItemData(ItemStack(Material.STICK).apply {
                                addUnsafeEnchantment(Enchantment.KNOCKBACK, 2)
                            }, 1..1, 2..3, 1.0),
                            ChestItemData(ItemStack(Material.COAL), 2..12, 2..3, 4.5),
                            ChestItemData(ItemStack(Material.WATER_BUCKET), 1..1, 2..3, 2.0),
                            ChestItemData(ItemStack(Material.BOOK).apply {
                                enchantValue = 8
                            }, 1..1, 2..3, 1.5),
                            ChestItemData(ItemStack(Material.ARROW), 8..16, 2..3, 3.0),
                            ChestItemData(ItemStack(Material.FLINT_AND_STEEL), 1..1, 2..3, 2.0),
                            ChestItemData(ItemStack(Material.PAPER), 5..7, 2..3, 5.5),
                            ChestItemData(ItemStack(Material.GUNPOWDER), 3..8, 2..3, 5.5),
                            ChestItemData(ItemStack(Material.EXPERIENCE_BOTTLE), 3.. 9, 2..3, 4.0),
                            ChestItemData(ItemStack(Material.REDSTONE), 12..12, 2..3, 2.5),
                            *listOf(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS)
                                .map { ChestItemData(ItemStack(it), 1..1, 2..3, 3.0 / 4) }.toTypedArray(),
                        )
                    ),
            ChestType.RARE to
                    ChestLootTable(
                        listOf(
                            1..1,
                            3..5
                        ),
                        listOf(
                            ChestItemData(ItemStack(Material.DIAMOND), 2..8, 1..1, 2.0),
                            ChestItemData(ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 1..1, 1..1, 1.0),
                            ChestItemData(ItemStack(Material.ANVIL), 1..1, 1..1, 1.5),
                            ChestItemData(ItemStack(Material.CAKE).apply {
                                itemMeta = itemMeta.apply {
                                    displayName(text("진짜 멋진 케이크").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
                                }
                            }, 1..1, 1..1, 1.0),
                             ChestItemData(ItemStack(Material.BOOK).apply {
                                enchantValue = 16
                            }, 1..1, 1..1, 4.0),
                            ChestItemData(BattleRoyalItemData.TRANSCEND_BOOK.item.clone().apply {
                                transcendLevel = 1
                            }, 1..1, 1..1, 5.0),
                            ChestItemData(ItemStack(Material.POTION).apply {
                                itemMeta = (itemMeta as PotionMeta).apply {
                                    basePotionData = PotionData(PotionType.STRENGTH)
                                }
                            }, 1..1, 1..1, 2.5),
                            *listOf(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.IRON_SWORD, Material.BOW, Material.CROSSBOW)
                                .map { ChestItemData(ItemStack(it).apply { enchantValue = 20 }, 1..1, 1..1, 4.0 / 7) }.toTypedArray(),

                            ChestItemData(ItemStack(Material.ARROW), 16..16, 3..5, 6.0),
                            ChestItemData(ItemStack(Material.GOLDEN_APPLE), 1..1, 3..5, 3.0),
                            ChestItemData(ItemStack(Material.GOLDEN_CARROT), 3..6, 3..5, 5.5),
                            ChestItemData(ItemStack(Material.ENDER_PEARL),1..2, 3..5, 4.0),
                            *listOf(Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS)
                                .map { ChestItemData(ItemStack(it), 1..1, 3..5, 6.0 / 4) }.toTypedArray(),
                            *listOf(PotionType.SLOWNESS, PotionType.POISON, PotionType.WEAKNESS)
                                .map { ChestItemData(ItemStack(Material.SPLASH_POTION).apply {
                                    itemMeta = (itemMeta as PotionMeta).apply {
                                        basePotionData = PotionData(it)
                                    }
                                }, 1..1, 3..5, 2.5 / 3) }.toTypedArray(),
                            *listOf(PotionType.JUMP, PotionType.SPEED, PotionType.REGEN)
                                .map { ChestItemData(ItemStack(Material.POTION).apply {
                                    itemMeta = (itemMeta as PotionMeta).apply {
                                        basePotionData = PotionData(it)
                                    }
                                }, 1..1, 3..5, 2.5 / 3) }.toTypedArray(),
                            ChestItemData(ItemStack(Material.IRON_INGOT), 4..8, 3..5, 9.0),
                            ChestItemData(ItemStack(Material.GOLD_INGOT), 2..8, 3..5, 7.0),
                            ChestItemData(ItemStack(Material.DIAMOND), 1..2, 3..5, 2.5),
                            ChestItemData(ItemStack(Material.LAPIS_LAZULI), 3..9, 3..5, 4.0),
                            ChestItemData(ItemStack(Material.CHORUS_FRUIT), 1..3, 3..5, 3.0),
                            ChestItemData(BattleRoyalItemData.SUPER_EXP_BOTTLE.item, 1..4, 3..5, 3.5),
                            ChestItemData(ItemStack(Material.TNT), 2..6, 3..5, 3.0),
                            ChestItemData(ItemStack(Material.OBSIDIAN), 3..5, 3..5, 2.0)
                        )
                    ),
            ChestType.EPIC to
                    ChestLootTable(
                        listOf(
                            1..2,
                            4..4
                        ),
                        listOf(
                            ChestItemData(ItemStack(Material.IRON_INGOT), 12..16, 1..2, 4.0),
                            ChestItemData(ItemStack(Material.GOLD_INGOT), 9..12, 1..2, 3.0),
                            ChestItemData(BattleRoyalItemData.SUPER_EXP_BOTTLE.item, 9..12, 1..2, 2.0),

                            ChestItemData(ItemStack(Material.DIAMOND), 4..8, 4..4, 5.0),
                            ChestItemData(ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 1..2, 4..4, 4.0),
                            ChestItemData(ItemStack(Material.TRIDENT), 1..1, 4..4, 3.0),
                            ChestItemData(ItemStack(Material.TOTEM_OF_UNDYING), 1..2, 4..4, 2.5),
                            ChestItemData(ItemStack(Material.BOOK).apply {
                                enchantValue = 24
                            }, 1..2, 4..4, 4.0),
                            ChestItemData(BattleRoyalItemData.TRANSCEND_BOOK.item.clone().apply {
                                transcendLevel = 2
                            }, 1..2, 4..4, 3.5),
                            ChestItemData(BattleRoyalItemData.TRANSCEND_BOOK.item.clone().apply {
                                transcendLevel = 3
                            }, 1..2, 4..4, 2.0),
                            ChestItemData(ItemStack(Material.POTION).apply {
                                itemMeta = (itemMeta as PotionMeta).apply {
                                    basePotionData = PotionData(PotionType.STRENGTH, false, true)
                                }
                            }, 1..2, 4..4, 4.0),
                            ChestItemData(BattleRoyalItemData.SUPER_POTION.item, 1..2, 4..4, 4.0),
                            ChestItemData(ItemStack(Material.ELYTRA), 1..1, 4..4, 1.0),
                            *listOf(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.DIAMOND_SWORD, Material.BOW, Material.CROSSBOW)
                                .map { ChestItemData(ItemStack(it).apply { enchantValue = 30 }, 1..1, 4..4, 4.0 / 7) }.toTypedArray(),
                            ChestItemData(ItemStack(Material.DIAMOND_AXE).apply {
                                addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3)
                            }, 1..1, 4..4, 2.0),
                            ChestItemData(ItemStack(Material.NETHERITE_INGOT), 1..1, 4..4, 3.0,
                                subItems = listOf(ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE)))
                        )
                    )
        )

    override fun getMapColors(): List<Byte> {
        val data: InputStream = javaClass.getResourceAsStream("/datas/city.txt")
            ?: throw IllegalArgumentException("Resource not found: /datas/city.txt")

        return data.bufferedReader().use { reader ->
            val content = reader.readText().trim()

            content.split(",")
                .map { it.trim().toByte() }
        }
    }

    override fun getCustomRecipes(): List<CustomRecipe> {
        val recipes = CustomRecipe.values().filter { it.worlds.isEmpty() || it.worlds.contains(world) }
        return recipes
    }
}