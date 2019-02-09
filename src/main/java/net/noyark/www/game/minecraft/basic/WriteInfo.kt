package net.noyark.www.game.minecraft.basic

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.FileOutputStream

/**
 * <br>
 * InfoThings类是由<strong>kotlin</strong>语言编写
 * 向详细介绍文档中书写内容
 *@author magiclu550
 */
class WriteInfo {
    /**
     * 向文档中书写内容
     */
    public fun info() {
        var info: Info = Info()
        val file = info.initFile()
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

        writer!!.write("Hello,大家好，欢迎您使用这个插件")
        writer.write("MFun是magiclu550的第一款沙雕nk插件，该插件目的就是整合一些常用的功能，省的腐竹再出去找")
        writer.write("另外，插件由于编写急促。不可避免的出现一些bug，但是不至于给您删掉服务器")
        writer.write("服务器的常用指令大家可以输入MHelp前来查看，基本设置都在set.yml")
        writer.write("作者qq843983728，博客magic.noyark.net,欢迎捐送")
        writer.write("功能列表")
        writer.write("""
           v0.1
        >服务器指令使用检测 (updated)0.1.1
        >进服提示 (updated)0.1.2
        >进服标题头 (updated error)0.1.3
        >进服小标题头 (updated)0.1.4
        >检测玩家飞行(updated)0.1.4.1
        >TPS检测(Updated)0.1.4.2
        >QQ获取(Updated)0.1.4.3
    v0.1.5
        >打开和关闭方块更新(Updated)0.1.5.1
        >飞行拽回采用配置文件方式（UPDATED)0.1.5.2
        >广播(Updated)0.1.6
        >用tip将服务器进入信息发送个每个人(Updated)0.1.7
        >屏蔽敏感词语<updated>0.1.8
        >称号,名字空格可以用&nbsp;代替（Updated)0.1.8.1
        """)
        writer.close();
    }
}