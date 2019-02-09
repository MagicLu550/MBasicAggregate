package net.noyark.www.game.minecraft.Config;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.SimpleConfig;

public class InitWordsConfig extends SimpleConfig{
    @Path(value = "bad-words")
    public String[] words = {};
    public InitWordsConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
    }
}
