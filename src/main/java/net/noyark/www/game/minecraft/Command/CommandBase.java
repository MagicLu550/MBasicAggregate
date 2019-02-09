package net.noyark.www.game.minecraft.Command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import net.noyark.www.game.minecraft.basic.Info;
import net.noyark.www.game.minecraft.basic.MFunCore;
import net.noyark.www.game.minecraft.threadBase.PlayerNameTitle;

public class CommandBase {
    static MFunCore core;
    private static net.noyark.www.game.minecraft.Command.RedBagCommand redBag;
    private static SignInCommand signIn;
    private static net.noyark.www.game.minecraft.Command.WarpCommand warp;
    static{
        core = MFunCore.getMFunCore();
        redBag = new RedBagCommand();
        signIn = core.getSignInCommand();
        warp = new WarpCommand();
    }
    /**
     * 关闭和打开指令检测
     * @param sender
     * @param s1
     * @param check
     * @return
     */

    public void controlCheck(CommandSender sender, String s1, boolean check){
        if(core.getCfg().isCheck == check){
            sender.sendMessage("指令检测已经被"+s1);
        }else{
            core.getCfg().isCheck = check;
            core.getCfg().save();
            sender.sendMessage("指令检测"+s1+"成功");
        }
    }

    /**
     * 修改进服提示
     */
    public void modMsg(String[] args){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<args.length;i++) {
            builder.append(args[i]+" ");
        }
        core.getCfg().welcomeMessage = builder.toString();
        core.getCfg().save();
    }

    /**
     * 进服标题
     * @param args
     */
    public void modTitle(String[] args){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<args.length;i++) {
            builder.append(args[i]+" ");
        }
        core.getCfg().title = builder.toString();
        core.getCfg().save();
    }

    /**
     * 进服小标题
     * @param args
     */
    public void modTip(String[] args){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<args.length;i++) {
            builder.append(args[i]+" ");
        }
        core.getCfg().tip = builder.toString();
        core.getCfg().save();
    }

    /**
     * 取消飞行检测
     */
    public void controlFly(CommandSender sender,boolean fly,String s1){

        if(core.getCfg().isFly == fly){
            sender.sendMessage("飞行检测已经被"+s1);
        }else{
            core.getCfg().isFly = fly;
            core.getCfg().save();
            sender.sendMessage("飞行检测"+s1+"成功");
        }
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        /*
        checkon指令
         */
        if(command.getName().equals("checkon")) {
            controlCheck(sender,"开启", true);
            return true;
        }
        /*
        checkoff指令
         */
        if(command.getName().equals("checkoff")) {
            controlCheck(sender,  "关闭", false);
            return true;
        }
        /*
         *修改进服提示指令
         */
        if(command.getName().equals("modmsg")){
            modMsg(args);
            return true;
        }
        /*
        修改进服标题
         */
        if(command.getName().equals("modtitle")){
            modTitle(args);
            return true;
        }
        if(command.getName().equals("modTip")){
            modTip(args);
            return true;
        }
        if(command.getName().equals("flyoff")){
            controlFly(sender,false,"关闭");
            return true;
        }
        if(command.getName().equals("flyon")){
            controlFly(sender,true,"开启");
            return true;
        }
        if(command.getName().equals("tps")){
            float tps = core.getServer().getTicksPerSecond();
            sender.sendMessage("tps:"+tps);
            return true;
        }
        if(command.getName().equals("openbc")){
            core.getCfg().bcOpen = true;
            sender.sendMessage("打开成功");
            core.getCfg().save();
            return true;
        }
        if(command.getName().equals("closebc")){
            core.getCfg().bcOpen= false;
            sender.sendMessage("关闭成功");
            core.getCfg().save();
            return true;
        }
        if(command.getName().equals("mhelp")){
            Info.commandHelp(sender);
            return true;
        }
        if(command.getName().equals("qq")){
            Config QQConfig = core.getQQConfig();
            core.getQq().setQQ(sender,args,QQConfig);
            return true;
        }
        if(command.getName().equals("takeTitle")){
            try {
                PlayerNameTitle.takeTitle(args[0].replaceAll("&nbsp;"," "), args[1].replaceAll("&nbsp;"," "));
            }catch(Exception e){

            }
            return true;
        }
        if(command.getName().equals("rb")){
            if(args.length==2){
                return redBag.sendBag(sender,args);// /rb money number
            }else if(args.length ==1){
                if(args[0].equals("list")){//查看所有红包
                    return redBag.listBag(sender);
                }else{
                    return redBag.getBag(sender, args);// /rb id
                }
            }else{
                sender.sendMessage("非法指令");
            }
        }
        if(command.getName().equals("sign")){
            signIn.signIn(sender);
            return true;
        }
        if(command.getName().equals("mwarp")){
            warp.warp(args,sender);
            return true;
        }
        if(command.getName().equals("setmw")){
            warp.setWarp(args,sender);
        }
        return false;
    }
}
