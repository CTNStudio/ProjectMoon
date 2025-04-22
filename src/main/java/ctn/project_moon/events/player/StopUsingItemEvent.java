package ctn.project_moon.events.player;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * 物品结束使用或切换物品时触发的事件
 */
public class StopUsingItemEvent extends PlayerEvent {
    public StopUsingItemEvent(Player player) {
        super(player);
    }
}
