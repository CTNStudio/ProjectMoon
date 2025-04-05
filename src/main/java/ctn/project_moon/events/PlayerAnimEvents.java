package ctn.project_moon.events;

import ctn.project_moon.common.item.AnimItem;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class PlayerAnimEvents {
    public static void empty(PlayerInteractEvent.LeftClickEmpty event){
        if (!(event.getEntity().getUseItem().getItem() instanceof AnimItem item) || !item.isLeftKeyEmpty()) {
            return;
        }
        item.isLeftKeyEmpty();
    }

    public static void block(PlayerInteractEvent.LeftClickBlock event){
        if (!(event.getEntity().getUseItem().getItem() instanceof AnimItem item) || !item.isLeftKeyBlock()){
            return;
        }
        item.isLeftKeyBlock();
    }

    public static void entity(AttackEntityEvent event){
        if (!(event.getEntity().getUseItem().getItem() instanceof AnimItem item) || !item.isLeftKeyEmpty()){
            return;
        }
        item.isLeftKeyEntity();
    }
}
