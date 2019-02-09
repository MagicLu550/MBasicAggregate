package net.noyark.www.game.minecraft.Command;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import cn.gulesberry.www.Extend.core.log.LogBase;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.basic.MFunCore;
import net.noyark.www.game.minecraft.Listener.PlayerListener;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RedBagCommand {
    public static final String ID = "id";
    public static final int ADD = 0;
    public static final int SUB = 1;
    private EconomyAPI api;
    private Config bag;
    private MFunCore core;
    private long id = 0;
    private double money;
    private int number;
    private String name;
    private Random rand;
    private String getName;
    private String sendName;
    private Config bagBase;
    public static final String RED_FILE;
    static {
        RED_FILE = LogBase.getLogFolder("MBasicAggregate")+"/redBag";
    }

    /**
     * 目前实现的功能为
     * 玩家发红包　－指令/sendBag money number
     * -然后存在配置文件里，并定义一个ｉｄ
     * －玩家金币会减少
     * －可以用listBag来看看红包和相关金额
     * 玩家收红包
     *－指令　/getBag id
     * 使用后Bag金币减少
     * 得到钱数的计算公式　money/number(+/-)random(100);
     * 当钱还剩最后一点时　money/number(+/-)random(100)<money,则钱数为money
     */
    public RedBagCommand(){
        bag = InitDynamicConfig.initRedBagConfig();
        core = MFunCore.getMFunCore();
        rand = new Random();
        api = new EconomyAPI();
        File redDirs = new File(RED_FILE);
        redDirs.mkdirs();//创建玩家红包文件夹
    }
    public boolean sendBag(@NotNull CommandSender sender, @NotNull String[]args){
        try{
            createMoney(sender, args);
            setBag(sender);
            sendBagSendMessage(sender);
        }catch(Exception e){
            sender.sendMessage("没有这种红包");
        }
        return true;
    }
    public boolean getBag(CommandSender sender,String[] args){
        try{
            getInformation(args,sender);
            if(money>0){
                getMoney(sender);
            }else if(number ==1){
                sendNoneMessage(sender);
            }
        }catch(Exception e){
            sender.sendMessage("你已经抢过该红包了");
        }
        return true;
    }
    public boolean listBag(CommandSender sender){
        sender.sendMessage("all-money和all-number为红包原来的钱数和数量");
        try{
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(
                                            core.getDataFolder()
                                                    +"/redBag.yml")));
            String s = null;
            while((s = reader.readLine())!=null){
                sender.sendMessage(s);
            }
        }catch(Exception e){

        }
        return true;
    }
    private void getInformation(String[] args,CommandSender sender){
        id = Long.parseLong(args[0]);//获得id
        getName = sender.getName();//得到红包者姓名
        sendName = bag.getString(id+".name");//发红包者姓名
        money = bag.getDouble(id+".money");
        number =  bag.getInt(id+".number");
        if(money == 0){
            sender.sendMessage("没有这个红包");
        }
    }
    private void getMoney(CommandSender sender)throws Exception{
        bagBase = new Config(RED_FILE+"/"+(id-1)+".yml");
        if(!bagBase.getString(getName).equals("")){
           throw new Exception();
        }
//        List<String> peoples = rp.getList(id+".people");
//        List<String> moneys = rp.getList(id+".money");
//        boolean have = false;
//        if(peoples.size()!=0){
//            for(String p:peoples){
//                if(sender.getName().equals(p)){
//                    have = true;
//                    throw new Exception();
//                }
//            }
//        }else{
//            have = true;
//        }
//        if(!have){
//            peoples.add(getName);
//            rp.set(id+".people",peoples);
//            rp.save();
//        }

        int subOrAdd = rand.nextInt(2);
        double randGet = 0;
        double allMoney = bag.getDouble(id+".all-money");
        double allBag = bag.getInt(id+".all-number");
        double oneBag = allMoney/allBag;
        switch (subOrAdd){
            case ADD:
                randGet = oneBag+rand.nextInt((int)((allMoney/allBag)*100)/100);
                countMoney(randGet,sender);
                break;
            case SUB:
                randGet = oneBag-rand.nextInt((int)((allMoney/allBag)*100)/100);
                if(randGet<=0){
                    randGet = 0.1;
                }
                countMoney(randGet,sender);
                break;
        }
//        if(!have){
//            moneys.add(randGet+"");
//            rp.set(id+".money",moneys);
//            rp.save();
//        }
        bagBase.set(getName,randGet);
        bagBase.save();
    }
    private void countMoney(double randGet,CommandSender sender){
        double get;
        if(number>0){
            if(randGet<money){
                get = randGet;
            }else {
                get = money;
            }
            number--;
            money -= get;
            //把number和money设置
            bag.set(id+".money",money);
            bag.set(id+".number",number);
            bag.save();
            EconomyAPI.getInstance().addMoney(getName,get);
            sender.sendMessage(core.getCfg().bagGetSend.replaceAll("money",get+""));
        }
    }
    private void sendNoneMessage(CommandSender sender){
        if(sendName!=""){
            sender.sendMessage(core.getCfg()
                    .bagNoneMessage
                    .replaceAll(net.noyark.www.game.minecraft.Listener.PlayerListener.PLAYER_NAME,sendName)
                    .replaceAll(ID,id+""));
            bag.remove(name);
            bag.save();
        }
        try{
            BufferedReader getPeople =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(
                                            RED_FILE
                                                    +"/"+id+".yml")));
            List<String> all = new ArrayList<>();
            String pm = null;
            int i = 0;
            while((pm = getPeople.readLine())!=null){
                all.add(pm);
            }
            //进行排序
            Collections.sort(all,
                    (String o1,String o2)->{
                        String[] s1 = o1.split(":");
                        String[] s2 = o2.split(":");
                        int moneyBetween = (int)(Double.parseDouble(s1[1])-Double.parseDouble(s2[1]));
                        return moneyBetween;
                    });
            core.getServer().broadcastMessage("运气王已诞生:"+all.get(0));
        }catch(Exception e){

        }

    }
    private void createMoney(@NotNull CommandSender sender, @NotNull String[] args) throws Exception{
        id = bag.getInt("id");
        money = Double.parseDouble(args[0]);//总钱数
        number = Integer.parseInt(args[1]);//红包数
        if(number<=0){
            throw new Exception("没有数量为０的红包");
        }
        bagBase = new Config(RED_FILE+"/"+id+".yml",Config.YAML);
    }
    private void setBag(CommandSender sender){
        //设置红包属性
        name = sender.getName();
        bag.set(id+1 +".money",money);
        bag.set(id+1 +".number",number);
        bag.set(id+1 +".id",id);
        bag.set(id+1 +".name",name);
        bag.set(id+1 +".all-money",money);
        bag.set(id+1 +".all-number",number);
        bag.set("id",id+1);
        bag.save();
    }
    private void sendBagSendMessage(CommandSender sender){
        //发出信息
        core.getServer().broadcastMessage(
                core.getCfg().bagSendMessage
                        .replaceAll(PlayerListener.PLAYER_NAME,name)
                        .replaceAll(ID,id+1 +""));
        api.addMoney(sender.getName(),-money);
    }
}
