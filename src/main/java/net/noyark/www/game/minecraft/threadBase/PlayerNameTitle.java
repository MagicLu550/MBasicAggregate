package net.noyark.www.game.minecraft.threadBase;

import cn.nukkit.utils.Config;

import static net.noyark.www.game.minecraft.Config.InitDynamicConfig.initTitleConfig;

public class PlayerNameTitle {
    public static void takeTitle(String name,String title){
        Config c = initTitleConfig();
        c.set(name,title);
    }
}
