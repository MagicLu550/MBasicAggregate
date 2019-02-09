package net.noyark.www.game.minecraft.Log;

import cn.nukkit.Player;
import cn.gulesberry.www.Extend.core.log.Log;
import cn.gulesberry.www.Extend.core.log.LogBase;
import net.noyark.www.game.minecraft.basic.MFunCore;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BreakLog implements Log {
    FileOutputStream writer;
    public void Logging(Player player,String name){
        try {
            String fold = MFunCore.LOG_FILE;
            File folder = new File(fold+"/playerBreak");
            folder.mkdirs();
            File file = new File(fold+"/playerBreak/"+player.getName() + ".log");
            if(!file.exists()){
                file.createNewFile();
                writer = LogBase.getWriter(file);
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
            String time = sdf.format(date);
            String breakLog= time+"x:"+player.getX()+" y:"+player.getY()+" z:"+player.getZ()+" block: "+name+"\n";
            writer.write(breakLog.getBytes());
            writer.flush();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
