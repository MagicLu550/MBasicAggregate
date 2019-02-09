package cn.gulesberry.www.Extend.core.log;

import cn.nukkit.Player;


@FunctionalInterface
public interface Log {
    void Logging(Player player, String s) throws Exception;
}
