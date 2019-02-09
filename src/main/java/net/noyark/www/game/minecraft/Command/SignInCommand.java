package net.noyark.www.game.minecraft.Command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.basic.MFunCore;
import net.noyark.www.game.minecraft.Listener.PlayerListener;

import java.util.Date;

/**
 * 签到插件思路，当一个人签到后，就保存在配置文件里
 * 会获得xxx个游戏币，连续满七天可以获得游戏币和物品
 * 单个玩家签到
 * player:
 *     name:
 *     id:
 *     date:
 *     times:
 *     date-had:
 * 每天只能签到一次．
 */
public class SignInCommand {
    static MFunCore core;
    public static long ONCE_HOURS;
    static{
        core = MFunCore.getMFunCore();
    }
    private int id;//签到的排名
    private String name;//签到的玩家名字
    private Config config;
    private long times;
    private int days;
    public SignInCommand(){
        config = InitDynamicConfig.initSignInConfig();
        ONCE_HOURS = core.getCfg().hours;
        setVar();
    }
    public void setVar(){
        config.set("id",1);
        config.set("days",1);
        config.set("id",config.get("id"));
        config.set("days",config.get("days"));
        config.save();
    }
    public void signIn(CommandSender sender){
        int dy = config.getInt("days");
        if(config.getInt(name+".date-had")!=dy||config.getInt(name+".date-had")==0){
            sender.sendMessage(core.getCfg().finish.replaceAll(PlayerListener.PLAYER_NAME,sender.getName()));
            name = sender.getName();
            config.set(name+".id",++id);
            Date date = new Date();
            config.set(name+".date",date.getTime());
            config.set(name+".times",++times);
            EconomyAPI.getInstance().addMoney(sender.getName(),core.getCfg().prize);
            config.save();
            if(config.getInt(name+".times")==7){
                Player player = core.getServer().getPlayer(name);
                player.dropItem(new Item(core.getCfg().dropPrize));
            }
            config.set(name+".date-had",dy);
        }else{
            sender.sendMessage(core.getCfg().haveSign);
        }
    }
    public int getDays(){
        return days;
    }
    public void addDays(){
        days++;
    }
}
