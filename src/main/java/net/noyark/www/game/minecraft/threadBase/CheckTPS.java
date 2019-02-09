package net.noyark.www.game.minecraft.threadBase;

import cn.nukkit.utils.TextFormat;
import net.noyark.www.game.minecraft.basic.MFunCore;

public class CheckTPS implements Runnable{
    private MFunCore core = MFunCore.getMFunCore();
    private float tps = core.getServer().getTicksPerSecond();
    public void run(){
        serverTPS();
    }
    /**
     * 获取服务器tps
     */
    public void serverTPS() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            if (tps < 20) {
                core.getLogger().warning(TextFormat.YELLOW + "TPS小于20了");
                core.getLogger().info("tps:" + tps);
            } else if (tps < 10) {
                tpsReload();//重启
            }
        }
    }
    public void tpsReload(){
        int ms = core.getCfg().reloadMs;
        core.getLogger().warning(TextFormat.RED + "TPS小于10了");
        core.getLogger().warning("tps" + tps);
        core.getServer().broadcastMessage("TPS过低，将在"+ms+"后重启");
        for(int i = ms;i>0;i++){
            core.getServer().broadcastMessage("服务器将在"+i+"重启...");
            try {
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        core.getServer().reload();
    }
}
