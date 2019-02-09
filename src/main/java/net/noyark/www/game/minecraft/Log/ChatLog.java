package net.noyark.www.game.minecraft.Log;

import cn.gulesberry.www.Extend.core.log.Log;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.basic.MFunCore;


import java.io.File;
import java.util.Date;

public class ChatLog implements Log {
    MFunCore core = MFunCore.getMFunCore();
    public void Logging(Player player,String message){
        String folderFileName = MFunCore.LOG_FILE+"/ChatLog";
        File folder = new File(folderFileName);
        folder.mkdirs();
        Config playerConfig = InitDynamicConfig.initChatLogConfig(folderFileName+"/"+player.getName()+".yml");
        long beforeTime = playerConfig.getLong("now-time");
        playerConfig.set("before-time",beforeTime);
        Date date = new Date();
        long nowTime = date.getTime();
        playerConfig.set("now-time",nowTime);
        int kickTimes = playerConfig.getInt("kick-times");
        playerConfig.save();
        if((nowTime-beforeTime)<core.getCfg().chatSecond*1000){
            player.kick();
            playerConfig.set("kick-times",kickTimes+1);
            playerConfig.save();
        }
    }
}
