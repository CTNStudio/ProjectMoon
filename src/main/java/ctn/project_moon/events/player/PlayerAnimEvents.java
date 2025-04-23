package ctn.project_moon.events.player;

import ctn.project_moon.common.item.AnimItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class PlayerAnimEvents {
    @SubscribeEvent
    public static void empty(PlayerInteractEvent.LeftClickEmpty event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isLeftKeyEmpty()) {
            return;
        }
        item.executeLeftKeyEmpty(event.getEntity());
    }

    @SubscribeEvent
    public static void block(PlayerInteractEvent.LeftClickBlock event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isLeftKeyBlock()) {
            return;
        }
        item.executeLeftKeyBlock(event.getEntity());
        event.setCanceled(false);
    }

    @SubscribeEvent
    public static void entity(AttackEntityEvent event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isLeftKeyEntity()) {
            return;
        }
        item.executeLeftKeyEntity(event.getEntity());
        event.setCanceled(false);
    }

    @SubscribeEvent
    public static void empty(PlayerInteractEvent.RightClickEmpty event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isRightKeyEmpty()) {
            return;
        }
        item.executeRightKeyEmpty(event.getEntity());
    }

    @SubscribeEvent
    public static void block(PlayerInteractEvent.RightClickBlock event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isRightKeyBlock()) {
            return;
        }
        item.executeRightKeyBlock(event.getEntity());
        event.setCanceled(false);
    }

    @SubscribeEvent
    public static void entity(PlayerInteractEvent.EntityInteract event) {
        if (!(getItem(event.getEntity()) instanceof AnimItem item) || !item.isRightKeyEntity()) {
            return;
        }
        item.executeRightKeyEntity(event.getEntity());
        event.setCanceled(false);
    }

    @SubscribeEvent
    public static void cancel(MovementInputUpdateEvent event){}

    private static @NotNull Item getItem(Player event) {
        return event.getUseItem().getItem();
    }
}
