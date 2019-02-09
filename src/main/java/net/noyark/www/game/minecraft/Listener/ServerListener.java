package net.noyark.www.game.minecraft.Listener;
/**
 * Server相关事件
 * Fun
 */

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import net.noyark.www.game.minecraft.basic.MFunCore;

public class ServerListener implements Listener {
    private String command;
    private MFunCore core = MFunCore.getMFunCore();
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onServerCommand(ServerCommandEvent event){
        //检测玩家使用指令
        boolean isCheckCommand = core.getCfg().isCheck;
        String name = event.getSender().getName();
        command = event.getCommand();
        if(isCheckCommand == true){
            core.getLogger().info(name+"使用了"+command);
        }
    }
}
