package net.noyark.www.game.minecraft.Config;

import cn.nukkit.utils.Config;
import net.noyark.www.game.minecraft.basic.MFunCore;
import net.noyark.www.game.minecraft.threadBase.BroadCast;

public class InitDynamicConfig {
    static MFunCore core;
    static BroadCast cast;
    static {
        core = MFunCore.getMFunCore();
        cast = new BroadCast();
    }
    public static Config initQQConfig(){
        Config c =new Config(core.getDataFolder()+"/"+"qq.yml");
        return c;
    }
    /**
     * 存储玩家称号信息
     */
    public static Config initTitleConfig(){
        Config c = new Config(core.getDataFolder()+"/title.yml",Config.YAML);
        return c;
    }
    public static Config iniBroadCastConfig(String file){
        Config c = new Config(core.getDataFolder()+"/"+file,Config.YAML);
        cast.writeMessage(c);
        return c;
    }
    public static Config initListenerConfig(){
        Config listenerConfig = new Config(core.getDataFolder()+"/Listener.yml",Config.YAML);
        return listenerConfig;
    }
    public static Config initDiedConfig(){
        Config died = new Config(core.getDataFolder()+"/diedMessage.yml",Config.YAML);
        return died;
    }
    public static Config initRedBagConfig(){
        Config bag = new Config(core.getDataFolder()+"/redBag.yml",Config.YAML);
        return bag;
    }
    public static Config initSignInConfig(){
        Config sign = new Config(core.getDataFolder()+"/SignIn.yml",Config.YAML);
        return sign;
    }
    public static Config initWarpConfig(){
        Config warp = new Config(core.getDataFolder()+"/warp.yml",Config.YAML);
        return warp;
    }
    public static Config initChatLogConfig(String fileName){
        Config chat = new Config(fileName,Config.YAML);
        return chat;
    }
}
