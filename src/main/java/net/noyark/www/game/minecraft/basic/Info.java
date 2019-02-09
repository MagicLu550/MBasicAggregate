package net.noyark.www.game.minecraft.basic;

import cn.nukkit.command.CommandSender;

import java.io.*;

/**
 * Info类主要创建插件详细文档和提供详细命令提示
 * @author magiclu550
 * @since JDK1.8
 * @since Nukkit api 1.7
 */
public class Info {
    static net.noyark.www.game.minecraft.basic.MFunCore core;
    static{
        core = MFunCore.getMFunCore();
    }

    /**
     * 创建文档
     * @return
     */
    public File initFile(){
        File file;
        file = new File(core.getDataFolder()+"readMe.txt");
        try {

            file.createNewFile();
            core.getLogger().info("已经创建文件readMe.txt");
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 提供命令提示
     * @param sender
     */
    public static void commandHelp(CommandSender sender){
        sender.sendMessage("1./MHelp-获取帮助"
                +"\n2./checkon-打开指令检测" +
                "\n3./checkoff-关闭指令检测" +
                "\n4./modmsg args-修改进入服务器提示信息" +
                "\n5./modtitle args-修改进入服务器的大标题提示" +
                "\n6./modtip args-修改服务器的小标题提示" +
                "\n7./flyoff-关闭飞行检测" +
                "\n8./flyon-打开飞行检测" +
                "\n9./tps-检测tps" +
                "\n10./closebc-打开服务器广播" +
                "\n11./openbc-关闭服务器广播" +
                "\n12./takeTitle player title-设置玩家称号" +
                "\n13./rb list-列出所有红包" +
                "\n14./rb id-通过id来领取红包" +
                "\n15./rb money number-发红包" +
                "\n16./sign-每日签到" +
                "\n17./mwarp-随机传送" +
                "\n18./setmw [world] x y z-设置随机传送的初始坐标" +
                "");
    }
}
