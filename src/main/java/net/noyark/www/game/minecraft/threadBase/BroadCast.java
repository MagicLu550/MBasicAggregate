package net.noyark.www.game.minecraft.threadBase;

import cn.nukkit.utils.Config;
import net.noyark.www.game.minecraft.basic.MFunCore;

import static net.noyark.www.game.minecraft.Config.InitDynamicConfig.iniBroadCastConfig;
import static net.noyark.www.game.minecraft.Listener.PlayerListener.LF;

/**
 * 广播类
 * 定期广播
 */
public class BroadCast implements Runnable{
    MFunCore core = MFunCore.getMFunCore();
    public void run(){
        sendBroadCast();
    }

    /**
     * 该方法用于定义公告数量和公告信息
     *USING XML
     */
    public void sendBroadCast(){
        /**
         * 生成配置文件
         */
        Config c = iniBroadCastConfig("broad.yml");

        /**
         * 播放
         */
        int i = 0;
        while(true) {
            if (core.getCfg().bcOpen == true) {
                String s = c.getString("broadcast" + i);
                if (i == core.getCfg().broadcastNumber) {
                    i = 0;
                }
                core.getServer().broadcastMessage(s.replaceAll(LF,"\n"));
                i++;
                try {
                    Thread.sleep(core.getCfg().broadcastSecond);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void writeMessage(Config c){
        //读取文件算是tm获取到这种办法了，先get文件，然后再塞进去，完美解决一重启就重置问题
        for(int i = 1; i< core.getCfg().broadcastNumber+1; i++) {
            String get = c.getString("broadcast"+i);
            c.set("broadcast" + i,get);
                c.save();
        }
    }


}
