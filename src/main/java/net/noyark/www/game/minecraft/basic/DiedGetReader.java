package net.noyark.www.game.minecraft.basic;

import cn.nukkit.utils.Config;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.Listener.PlayerListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DiedGetReader {
    public void read(){
        File file = new File("./plugins/MBasicAggregate/died.txt");
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file),
                                    "UTF-8"));
            String s = null;
            Config c = InitDynamicConfig.initDiedConfig();
            while((s=reader.readLine())!=null){
                String[] str = s.split("=%1s ");
                if(str.length==2){
                    c.set(str[0], PlayerListener.PLAYER_NAME+str[1]);
                    c.save();
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
