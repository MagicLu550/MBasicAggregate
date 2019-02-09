package net.noyark.www.game.minecraft.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.gulesberry.www.Extend.core.log.Log;
import net.noyark.www.game.minecraft.Log.BreakLog;
import net.noyark.www.game.minecraft.basic.MFunCore;

public class BlockListener implements Listener {
    private MFunCore core = MFunCore.getMFunCore();
    private Log log = new BreakLog();
    /**
     * 方块更新事件
     */
    @EventHandler
    public void onBlockUpdated(BlockUpdateEvent event) {
        if (!core.getCfg().updated) {
            int id = event.getBlock().getId();
            if (id == 12 || id == 13) {
                event.setCancelled();
            } else {
                event.setCancelled(false);
            }
        }
    }

    /**
     * 方块破坏 记录
     * @param event
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        String name = event.getBlock().getName();
        Player player = event.getPlayer();
        try {
            log.Logging(player, name);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}