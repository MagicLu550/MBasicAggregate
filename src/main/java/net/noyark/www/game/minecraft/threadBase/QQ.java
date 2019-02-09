package net.noyark.www.game.minecraft.threadBase;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.Listener.PlayerListener;
import net.noyark.www.game.minecraft.basic.MFunCore;

import static net.noyark.www.game.minecraft.Listener.PlayerListener.LF;

public class QQ {
    MFunCore core = MFunCore.getMFunCore();
    public void getQQ(PlayerJoinEvent event){
        Config c = InitDynamicConfig.initQQConfig();
        Player player = event.getPlayer();
        String name = player.getName();
        if(c.getString(name)=="") {
            player.sendMessage(core.getCfg().getQQMessage.replaceAll(PlayerListener.PLAYER_NAME,player.getName()).replaceAll(LF,"\n"));
        }else{
            player.sendMessage(core.getCfg().modQQMessage.replaceAll(PlayerListener.PLAYER_NAME,player.getName()).replaceAll(LF,"\n"));
        }

    }

    /**
     * 单个玩家设置qq
     * @param sender
     * @param args
     */
    public void setQQ(CommandSender sender,String[] args,Config c){
       try{
            String name = sender.getName();
            core.getLogger().info(c.getString(name));
            if (c.getString(name).equals("")) {
                setThis(sender, args,c);
                sender.sendMessage("设置成功");
            }
            if (args[0].equals("true")) {
                setThis(sender, args,c);
                sender.sendMessage("修改成功");
            } else if (args[0].equals("false")) {
                sender.sendMessage("确认不修改成功");
            }
        }catch(Exception e){
            sender.sendMessage(TextFormat.RED+"非法指令");
        }
    }
    public void setThis(CommandSender sender,String[] args,Config c){
        c.set(sender.getName(),args[0]);
        c.save();
    }
}
