package net.noyark.www.game.minecraft.basic;

import cn.gulesberry.www.Extend.core.log.LogBase;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.SimpleConfig;
import cn.nukkit.utils.TextFormat;
import com.webserver.core.WebServer;
import net.noyark.www.game.minecraft.Command.CommandBase;
import net.noyark.www.game.minecraft.Command.SignInCommand;
import net.noyark.www.game.minecraft.Config.*;
import net.noyark.www.game.minecraft.threadBase.BroadCast;
import net.noyark.www.game.minecraft.threadBase.CheckTPS;
import net.noyark.www.game.minecraft.threadBase.QQ;
import net.noyark.www.game.minecraft.web.WebSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**GulesBerry Technology Co. Ltd. (c) Java Lib.
 * <hr>
 * <br>该类实现了最主要的功能
 * <p>这个类是插件的<strong>主运行类</strong>，继承自PluginBase</p>
 * {@code onEnable}
 * @author magiclu550
 * @since JDK1.8.0_201
 * @since NukkitxAPI_1.0.7
 * @see cn.nukkit.command.CommandExecutor
 * @see cn.nukkit.plugin.Plugin
 * @see cn.nukkit.plugin.PluginBase
 *
 */
public class MFunCore extends PluginBase {

    private QQ qq;
    private Config QQConfig;
    private InitSetConfig cfg;
    private ExecutorService pool = Executors.newFixedThreadPool(100);
    private String fs[] = {};
    private List<Listener> listeners;
    private List<SimpleConfig> configs;
    private net.noyark.www.game.minecraft.Command.CommandBase commandBase;
    private static MFunCore MFunCore;
    private Info info;
    private WriteInfo things;
    private DiedGetWriter writer;
    private DiedGetReader reader;
    private net.noyark.www.game.minecraft.Command.SignInCommand command;
    public static final String LOG_FILE = LogBase.getLogFolder("MBasicAggregate");
    @Override
    public void onLoad() {
        getLogger().info(TextFormat.BLUE + "MFun 集成小插件 By MagicLu GulesBerry Tech");
        getLogger().info("本插件需要您安装经济API");
        loadWorld();
    }

    @Override
    public void onEnable() {
        setting();
        loadInfo();
    }

    @Override
    public void onDisable() {
        getLogger().info("插件关闭成功,MFun QQ 843983728 Web:magic.noyark.net");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandBase.onCommand(sender, command, label, args);
    }

    /**
     * 设置所有的初始化信息
     */
    public void setting() {
        initVar();
        loadCfg();
        addThread();
        registerListener();
        loadWeb();
    }

    /**
     * 创建参考文档
     */
    public void loadInfo() {
        info.initFile();
        things.info();
        File f = new File(getDataFolder()+"/diedMessage.yml");
        if(!f.exists()){
            writer.write();
            reader.read();
        }
        File fTxt = new File(getDataFolder()+"died.txt");
        fTxt.delete();
    }

    public void loadWeb() {
        WebSetting.set();
    }

    /**
     * 初始化和实例化
     */
    public void initVar() {
        MFunCore = this;
        listeners = new ArrayList<>();
        configs = new ArrayList<>();
        qq = new QQ();
        QQConfig = InitDynamicConfig.initQQConfig();
        cfg = new InitSetConfig(this, "Set");
        command = new net.noyark.www.game.minecraft.Command.SignInCommand();
        commandBase = new CommandBase();
        info = new Info();
        things = new WriteInfo();
        writer = new DiedGetWriter();
        reader = new DiedGetReader();
    }

    /**
     * 加载配置文件
     */
    public void loadCfg() {
        //读取数值
        this.cfg.load();
        //保存值到文件
        this.cfg.save();
        if (this.getCfg().reset) {
            //1.屏蔽关键字创建文字
            //2.屏蔽关键字所在的句子集合
            //3.监听器注册
            configs.add(new InitSentenceConfig(this, "badSentence"));
            configs.add(new InitWordsConfig(this, "badWord"));
            configs.add(new InitListenerConfig(this, "Listener"));
            for (SimpleConfig config : configs) {
                config.save();
            }
        }
        this.getCfg().reset = false;//关闭第二次创建
        this.getCfg().save();
    }

    /**
     * 注册监听器
     */
    public void registerListener() {
        Config listenerConfig = InitDynamicConfig.initListenerConfig();
        listenerConfig.save();
        List<String> listenersClasses = listenerConfig.getList("Listeners");
        try {
            for (int i = 0; i < listenersClasses.size(); i++) {
                Class clz = Class.forName(listenersClasses.get(i));
                Listener l = (Listener) clz.newInstance();
                listeners.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * 向线程池里添加任务
     */
    public void addThread() {
        /*
         * 检查TPS
         */
        CheckTPS check = new CheckTPS();
        BroadCast broadCast = new BroadCast();
        WebServer server = new WebServer();
        pool.execute(check);
        pool.execute(broadCast);
        if (this.getCfg().isStart) {
            pool.execute(server);// 待修复
        }
        pool.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(net.noyark.www.game.minecraft.Command.SignInCommand.ONCE_HOURS * 60 * 60 * 1000);
                    command.addDays();
                    Config c = InitDynamicConfig.initSignInConfig();
                    c.set("days", command.getDays());
                    c.save();
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * 加载世界
     */
    public void loadWorld() {
        File f = new File("worlds");
        File[] files = f.listFiles();
        fs = new String[files.length];
        for (int i = 0; i < fs.length; i++) {
            fs[i] = files[i].toString();
            StringBuilder builder = new StringBuilder(fs[i]);
            fs[i] = builder.substring(fs[i].indexOf("/") + 1);
        }
        getLogger().info("您的所有世界为：" + Arrays.toString(fs));
    }


    public Config getQQConfig() {
        return QQConfig;
    }

    public QQ getQq() {
        return qq;
    }

    public static MFunCore getMFunCore() {
        return MFunCore;
    }

    public InitSetConfig getCfg() {
        return cfg;
    }

    public SignInCommand getSignInCommand() {
        return command;
    }
}