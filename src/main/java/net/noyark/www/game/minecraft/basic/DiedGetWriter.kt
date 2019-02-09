package net.noyark.www.game.minecraft.basic

import java.io.*
import java.lang.Exception;

class DiedGetWriter{
    public fun write() {
        val file = File("./plugins/MBasicAggregate/died.txt")
        var writer: PrintWriter? = null
        try {
            writer = PrintWriter(
                    BufferedWriter(
                            OutputStreamWriter(
                                    FileOutputStream(
                                            file
                                    ), "UTF-8"
                            )
                    ), true
            )
        } catch (e: Exception) {
        }

        if (writer != null) {
            var s = "s";
            writer.write("""death-attack-anvil=%1$s 被坠落的铁砧压扁了
death-attack-arrow=%1$s 被 %2$s 射杀
death-attack-arrow-item=%1$s 被 %2$s 用 %3$s 射杀
death-attack-cactus=%1$s 被戳死了
death-attack-cactus-player=%1$s 在试图逃离 %2$s 时撞入了仙人掌中
death-attack-drown=%1$s 淹死了
death-attack-drown-player=%1$s 在试图逃离 %2$s 时淹死了
death-attack-explosion=%1$s 爆炸了
death-attack-explosion-player=%1$s 被 %2$s 炸死了
death-attack-fall=%1$s 落地过猛
death-attack-fallingBlock=%1$s 被坠落的方块压扁了
death-attack-fireball=%1$s 被 %2$s 用火球烤死了
death-attack-fireball-item=%1$s 被 %2$s 用 %3$s 发射的火球烤死了
death-attack-generic=%1$s 死了
death-attack-inFire=%1$s 浴火焚身
death-attack-inFire-player=%1$s 在与 %2$s 战斗中不慎走入了火中
death-attack-inWall=%1$s 在墙里窒息而亡
death-attack-indirectMagic=%1$s 被 %2$s 使用的魔法杀死了
death-attack-indirectMagic-item=%1$s 被 %2$s 用 %3$s 杀死了
death-attack-lava=%1$s 试图在岩浆里游泳
death-attack-lava-player=%1$s 在逃离 %2$s 时试图在岩浆里游泳
death-attack-lightningBolt=%1$s 被闪电击中
death-attack-magic=%1$s 被魔法杀死了
death-attack-mob=%1$s 被 %2$s 杀死了
death-attack-onFire=%1$s 被烧死了
death-attack-onFire-player=%1$s 在试图与 %2$s 战斗时被烤的酥脆
death-attack-outOfWorld=%1$s 掉出了这个世界
death-attack-player=%1$s 被 %2$s 杀死了
death-attack-player-item=%1$s 被 %2$s 用 %3$s 杀死了
death-attack-starve=%1$s 饿死了
death-attack-thorns=%1$s 在试图伤害 %2$s 时被杀
death-attack-thrown=%1$s 被 %2$s 给砸死了
death-attack-thrown-item=%1$s 被 %2$s 用 %3$s 给砸死了
death-attack-wither=%1$s 凋零了
death-fell-accident-generic=%1$s 从高处摔了下来
death-fell-accident-ladder=%1$s 从梯子上摔了下来
death-fell-accident-vines=%1$s 从一些藤蔓上摔了下来
death-fell-accident-water=%1$s 从水中掉了下来
death-fell-assist=%1$s 因为 %2$s 注定要摔死
death-fell-assist-item=%1$s 因为 %2$s 使用了 %3$s 注定要摔死
death-fell-finish=%1$s 摔伤得太重并被 %2$s 完结了生命
death-fell-finish-item=%1$s 摔伤得太重并被 %2$s 用 %3$s 完结了生命
death-fell-killer=%1$s 注定要摔死
        """)
            writer.close();
        }

    }

}
