package net.noyark.www.game.minecraft.Config;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.SimpleConfig;

import java.util.ArrayList;
import java.util.List;

public class InitListenerConfig extends SimpleConfig{
    @SimpleConfig.Path(value = "Listeners")
    public List<String> list = new ArrayList<>();
    public InitListenerConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
        DefaultVar();
    }
    public void DefaultVar(){
        list.add("net.noyark.www.game.minecraft.Listener.BlockListener");
        list.add("net.noyark.www.game.minecraft.Listener.PlayerListener");
        list.add("net.noyark.www.game.minecraft.Listener.ServerListener");
    }
}
