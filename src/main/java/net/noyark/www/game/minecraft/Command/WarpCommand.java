package net.noyark.www.game.minecraft.Command;


import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import net.noyark.www.game.minecraft.basic.MFunCore;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;


public class WarpCommand{
    MFunCore core = MFunCore.getMFunCore();
    /**
     * 随机传送
     * 定义随机坐标传送
     */
    public double limtX;//地面水平的传送点的最大宽度
    public double limtZ;//地面水平的传送点的最大长度
    public int Y;//地面以上坐标
    //主城坐标
    public double x;//坐标
    public double y;//坐标
    public double z;//坐标
    public Config warp;
    public String allWorld;
    public WarpCommand(){
        //获取范围
        limtX = core.getCfg().limtX;
        limtZ = core.getCfg().limtZ;
        warp = InitDynamicConfig.initWarpConfig();
        warp.set("cant-add-world","(lobby|neither)");
        if(!warp.check()){
            warp.save();
        }
        allWorld = warp.getString("cant-add-world");
    }
    //针对传送别人 1个参数 /mwarp playername
    //针对传送自己　0个参数/mwarp
    public synchronized void warp(String[] args,CommandSender sender){
            if(args.length==0){
                Player player =core.getServer().getPlayer(sender.getName());
                String where = player.getLevel().getName();
                tpWhere(player,where,sender);
            }else if(args.length==1){
                Player player =core.getServer().getPlayer(args[0]);
                String where = player.getLevel().getName();
                tpWhere(player,where,sender);
            }
    }
    private void tpWhere(Player player,String where,CommandSender sender){
        if(warp.get(where+".world")!=null){
            sender.sendMessage(core.getCfg().warpMessage);
            //获取出生点的位置
            x = warp.getDouble(where+".x");
            y = warp.getDouble(where+".y");
            z = warp.getDouble(where+".z");
            double randxLeft = x-0.5*limtX;
            double randzLeft = y-0.5*limtZ;
            double randxRight = x+0.5*limtX;
            double randzRight = y+0.5*limtZ;
            double toX = randxLeft+Math.random()*(randxRight-randxLeft+1);
            double toZ = randzLeft+Math.random()*(randzRight-randzLeft+1);
            double x =player.getX();
            double y =player.getY();
            double z =player.getZ();
            Vector3 pos = new Vector3(x,y,z);
            while(player.getLevel().getBlock(pos).getId()!=0) {
                player.move(toX, y, toZ);
                y+=1;
            }
            sender.sendMessage(core.getCfg().warpSuccessMessage.replaceAll("money",core.getCfg().warpMoney+""));
            EconomyAPI.getInstance().addMoney(player,core.getCfg().warpMoney);
        }else{
            sender.sendMessage("这个世界没有被设置过");
        }
    }
    //setmw
    public void setWarp(String[] args,CommandSender sender){
        //args[0][世界名称]　args[1] x args[2] y args[3] z
        if(args.length==3){
            Player player = core.getServer().getPlayer(sender.getName());
            String world = player.getLevel().getName();
            if(world.matches(allWorld)){
                warp.set(world+".x",args[1]);
                warp.set(world+".y",args[2]);
                warp.set(world+".z",args[3]);
                warp.set(world+".world",world);
                warp.save();
            }
        }else if(args.length==4){
            if(args[0].matches(allWorld)){
                warp.set(args[0]+".x",args[1]);
                warp.set(args[0]+".y",args[2]);
                warp.set(args[0]+".z",args[3]);
                warp.set(args[0]+".world",args[0]);
                warp.save();
            }
        }else{
            sender.sendMessage("指令不合法");
        }

    }
}
