package net.noyark.www.game.minecraft.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.network.protocol.SetTitlePacket;
import cn.nukkit.utils.Config;
import cn.gulesberry.www.Extend.core.project.Beta;
import cn.gulesberry.www.Extend.core.project.ParseBeta;
import cn.gulesberry.www.Extend.core.project.Progress;
import net.noyark.www.game.minecraft.Config.InitDynamicConfig;
import net.noyark.www.game.minecraft.basic.MFunCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

/**
 * 关于玩家的监听器
 */
public class PlayerListener implements Listener {

    static MFunCore core = MFunCore.getMFunCore();
    private boolean isFlying;
    private int playerOnline;
    private static final double X = core.getCfg().x;
    private static final double Y = core.getCfg().y;
    private static final double Z = core.getCfg().z;
    public static final int MAX = core.getServer().getMaxPlayers();
    public static final String PLAYER_NAME = "player";
    public static final String LIST_TOP= "list";
    public static final String REASON = "reason";
    public static final String MAX_STRING = "MAX";
    public static final String IP = "ip";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String PLAYER_ONLINE = "people";
    public static final String LF = "\\\\n";
    public int players;
    private Player player;
    private boolean isOp;
//    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
//    public void onPlayerLogin(PlayerLoginEvent event){
//        synchronized (this){
//            player = event.getPlayer();
//            isOp = player.isOp();
//            if(isOp){
//                player.setOp(false);
//            }
//        }
//    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
        sendMessage(event);
        core.getQq().getQQ(event);
//        synchronized (this){
//            if(isOp){
//                player.setOp(true);
//            }
//        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerFly(PlayerToggleFlightEvent event){
        check(event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerExit(@Nullable PlayerQuitEvent event){
        subPeople();
        String name = event.getPlayer().getName();
        String exit = core.getCfg().exitMessage
                .replaceAll(PLAYER_NAME,name)
                .replaceAll(LF,"\n");
        String reason = event.getReason();
        String exitMsg = exit
                .replaceAll(PLAYER_NAME,name)
                .replaceAll(REASON,reason)
                .replaceAll(LF,"\n");
        core.getServer().broadcastMessage(exitMsg);
        core.getLogger().info(exitMsg);
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(@NotNull PlayerChatEvent event){
        event.setCancelled();
        String name = event.getPlayer().getName();
        String ip = event.getPlayer().getAddress();
        String oldMessage = event.getMessage();
        //屏蔽脏话
        Config c = new Config(core.getDataFolder()+"/badWord.yml");
        c.save();
        Config c1 = new Config(core.getDataFolder()+"/badSentence.yml");
        c1.save();
        setBadWordAndChat(oldMessage,c1,c,name,ip);
    }
    /**
     * 自定义死亡
     * @param event
     * 未开始
     */
    @Beta(where = "all",progress= Progress.FINISH)
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerAction(@Nullable PlayerDeathEvent event){
        try {
            ParseBeta.ControlBeta(this, "onPlayerAction", PlayerDeathEvent.class);
            if(core.getCfg().state){
                sendDeathMessage(event);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendMessage(@NotNull PlayerJoinEvent event){
        String name = event.getPlayer().getName();
        event.getPlayer().sendMessage(core.getCfg().signMessage.replaceAll(LF,"\n")
               .replaceAll(PLAYER_NAME,name));
        addPeople();
        //玩家第一次进入欢迎提示
        //先确认有没有这个玩家，获取player文件夹
        boolean exist = false;
        File players = new File("players");
        File[] files = players.listFiles();
        for(File file:files){
            String FileName = file.toString();
            String getPlayerName = FileName.substring(FileName.indexOf("/")+1,FileName.indexOf("."));
            if(name.toLowerCase().equals(getPlayerName)){
                exist = true;
            }
            this.players++;
        }
        if(!exist){
            event.setJoinMessage(core.getCfg().fristComeServerMessage
                    .replaceAll(PLAYER_NAME,name));
            event.getPlayer().sendMessage(core.getCfg().fristCome
                    .replaceAll(PLAYER_NAME,name)
                    .replaceAll(LF,"\n"));
            event.getPlayer().sendMessage(core.getCfg().list
                    .replaceAll(LIST_TOP,this.players+"")
                    .replaceAll(LF,"\n"));

        }else{
            //玩家第二次进入欢迎提示
            event.getPlayer().sendMessage(core.getCfg().welcomeMessage.replaceAll(PLAYER_NAME,name).replaceAll(LF,"\n"));
        }
        //玩家大标题提示
        int fateIn = core.getCfg().in;
        int fateOut = core.getCfg().out;
        int duration = core.getCfg().dura;
        event.getPlayer().sendTitle(core.getCfg().title
                .replaceAll(PLAYER_NAME,name).replaceAll(LF,"\n")
                ,core.getCfg().sub,fateIn,duration,fateOut);
        //玩家小标题提示
        event.getPlayer().sendTip(core.getCfg().tip
                .replaceAll(PLAYER_NAME,name).replaceAll(LF,"\n"));
        //获取服务端最大人数，和当前在线人数，向服务器个人反馈，向全服反馈进入者
        event.setJoinMessage(core.getCfg().welcomeMessageContainsPeople
                .replaceAll(PLAYER_NAME,name).replaceAll(MAX_STRING,MAX+"")
                .replaceAll(PLAYER_ONLINE,playerOnline+"").replaceAll(LF,"\n"));
        Set<Map.Entry<UUID,Player>> set = core.getServer().getOnlinePlayers().entrySet();
        //将进入服务器信息发送给每个玩家
        for(Map.Entry<UUID,Player> e:set){
            e.getValue().sendTip(core.getCfg().peopleTip
                    .replaceAll(PLAYER_NAME,name).replaceAll(LF,"\n"));
        }
    }
    public void sendDeathMessage(@Nullable PlayerDeathEvent event){
        Config c = InitDynamicConfig.initDiedConfig();
        String s = event.getDeathMessage().getText().replaceAll("\\.","-");
        event.setDeathMessage(c.getString(s).replaceAll(PLAYER_NAME,event.getEntity().getName()).replaceAll(LF,"\n"));
    }
    public void addPeople(){
        //人数增多
        playerOnline++;
    }
    private void subPeople(){
        playerOnline--;
    }
    public int getPlayerOnline(){
        return playerOnline;
    }
    private void check(PlayerToggleFlightEvent event){
        /*
        判断飞行，将其拽回出生点
         */
        boolean isOp;
        isOp = event.getPlayer().isOp();
        isFlying = event.isFlying();
        while(core.getCfg().isFly&&isFlying&&!isOp) {
            Player player = event.getPlayer();
            core.getLogger().info(Thread.currentThread().toString());
            core.getLogger().info("正在："+isFlying);
            core.getLogger().warning(player.getName() + "可疑飞行,被拖回");
            event.getPlayer().move(X,Y,Z);
        }
    }
    private void setBadWordAndChat(String oldMessage,Config c1,Config c,String name,String ip){
        Player player = core.getServer().getPlayer(name);
        String thatMessage = oldMessage;
//        try{
//            logChat.Logging(player,thatMessage);
//        }catch(Exception e){
//        }
//        if(!logChat.getPlayers().contains(player)){
            if(core.getCfg().modChat){
                List<String> badWords = c.getList("bad-words");
                boolean isBad = false;
                String message = oldMessage;
                if(core.getCfg().badwordCheck) {
                    if (!badWords.isEmpty()) {
                        for (String s : badWords) {
                            core.getLogger().info(s);
                            if (!(oldMessage.indexOf(s) == -1) && !s.equals("")) {
                                String newMessage = oldMessage
                                        .replaceAll(s, "***");
                                message = newMessage;
                                isBad = true;
                                Date date = new Date();
                                c1.set("bad-sentences", "[" + date + "]" + name + ":" + oldMessage);//记录脏话
                                c1.save();
                                break;
                            }
                        }
                    }
                }
                Set<Map.Entry<UUID,Player>> set = core.getServer().getOnlinePlayers().entrySet();
                //将进入服务器信息发送给每个玩家
                String title = InitDynamicConfig.initTitleConfig().getString(name,"Player");
                String mes = core.getCfg().chatFormat.replaceAll(PLAYER_NAME,name)
                        .replaceAll(IP,ip)
                        .replaceAll(TITLE,title)
                        .replaceAll(MESSAGE,message)
                        .replaceAll(LF,"\n");
                for(Map.Entry<UUID,Player> e:set){
                    e.getValue().sendMessage(mes);
                }
                core.getLogger().info(mes);

            }
//        }else{
//            player.sendMessage("您被暂时禁言了");
//        }
    }
}
