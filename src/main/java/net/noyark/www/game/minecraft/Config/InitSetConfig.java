package net.noyark.www.game.minecraft.Config;

import cn.nukkit.utils.SimpleConfig;
import cn.nukkit.plugin.PluginBase;

public class InitSetConfig extends SimpleConfig {
    /*
    传送
     */
    @Path (value = "warp.length")
    public long limtX = 100;
    @Path (value = "warp.width")
    public long limtZ = 300;
    @Path (value = "warp.sub-money")
    public int warpMoney = 30;
    @Path (value = "warp.warp-message")
    public String warpMessage = " 传送中...";
    @Path (value = "warp.warp-success-message")
    public String warpSuccessMessage = " 传送成功，扣款money元";
    /*
    服务器
     */
    @Path (value = "server.check-command")
    public boolean isCheck = true;
    @Path (value = "server.tps-reload")
    public int reloadMs = 60;
    @Path (value = "server.broadcast-ifopen")
    public boolean bcOpen = true;
    @Path (value = "server.broadcast-number")
    public int broadcastNumber = 3;
    @Path (value = "server.broadcast-second-one")
    public int broadcastSecond = 10000;
    @Path (value = "server.people-tip")
    public String peopleTip = " player 欢迎来到服务器 ";
    @Path (value = "server.updated")
    public boolean updated =false;
    @Path (value = "server.reset")
    public boolean reset = true;
    @Path (value = "server.badword-check")
    public boolean badwordCheck = true;
    @Path (value = "server.mod-chat")
    public boolean modChat = true;
    @Path (value = "server.chat-check")
    public boolean chatCheck = true;
    @Path (value = "server.bag.send-message")
    public String bagSendMessage = " player 发出了红包,ID号码是id ";
    @Path (value = "server.bag.get-message")
    public String bagGetMessage = " player 抢到了 id 红包,红包还有 money元，有number个红包 ";
    @Path (value = "server.bag.none-message")
    public String bagNoneMessage = " player 的 id 红包抢完了 ";
    @Path (value = "server.bag.get-message")
    public String bagGetSend = " 您抢到了 money 元 ";
    @Path (value = "server.sign-once-time-hours")
    public int hours = 24;
    @Path (value = "server.sign-prize")
    public int prize = 50;
    @Path (value = "server.sign-7-item-drop-prize")
    public int dropPrize = 1;
    @Path (value = "server.sign-7-item-give-prize")
    public int givePrize = 1;
    @Path (value = "server.sign-7-money-prize")
    public double moneyPrize = 240;
    @Path (value = "server.finish-sign")
    public String finish = " 签到成功";
    @Path (value = "server.have-sign")
    public String haveSign = " 已经签到过了";
    /*
    飞行拽回的位置
     */
    @Path (value = "fly-catch-to.x")
    public double x = 0;
    @Path (value = "fly-catch-to.y")
    public double y = 0;
    @Path (value = "fly-catch-to.z")
    public double z = 0;
    /*
    玩家
     */
    @Path (value = "player.fly-check")
    public boolean isFly = false;
    //是否开启
    @Path (value = "died-message.state")
    public boolean state =true;
    @Path (value = "player.sign-msg")
    public String signMessage = " player 请签到,签到指令为/sign ";
    @Path (value = "player.qq-get-message")
    public String getQQMessage = " player 请输入您的qq:/qq qq号码 ";
    @Path (value = "player.qq-mod-message")
    public String modQQMessage = " player 确认修改您的qq:/qq qq号码";
    @Path (value = "player.com-message-contains-people")
    public String welcomeMessageContainsPeople = " 欢迎加入服务器 [people/MAX] ";
    @Path (value = "player.exitMessage")
    public String exitMessage = " player 退出了服务器,因为reason ";
    @Path (value = "player.com-Message")
    public String welcomeMessage = " player 欢迎加入服务器 ";
    @Path (value = "player.com-Title.text")
    public String title = " player 欢迎加入服务器 ";
    @Path (value = "player.com-Title.subTitle")
    public String sub = " player 欢迎加入服务器 ";
    @Path (value = "player.com-Title.fate-in")
    public int in = 20;
    @Path (value = "player.com-Title.fate-out")
    public int out = 20;
    @Path (value = "player.com-Title.duration")
    public int dura = 20;
    @Path (value = "player.com-Tip")
    public String tip = " player 欢迎加入服务器 ";
    @Path (value = "player.register-list")
    public String list = " 恭喜您,你在本群的注册排名： list ";
    @Path (value = "player.frist-come")
    public String fristCome = " 欢迎 player 首次加入本服务器 ";
    @Path (value = "player.frist-come-server-broadcast")
    public String fristComeServerMessage = " 欢迎 player 首次加入本服务器 ";
    @Path (value = "player.chat")
    public String chatFormat = "[title][ip] player : message";
    //每次说话的时间
    @Path (value = "player.chat.chat-one-minute")
    public double chatSecond = 0.6;
    /**
     * web　server
     */
    @Path (value = "web.open")
    public boolean isStart = false;
    @Path (value = "web.maxPeople")
    public int maxPeople = 100;
    public InitSetConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
    }
}
