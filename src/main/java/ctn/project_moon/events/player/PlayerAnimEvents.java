package ctn.project_moon.events.player;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.AnimItem;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.TemporaryAttribute.PLAYER_RECORD_SPEED;

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
    public static void cancel(MovementInputUpdateEvent event){
//        Player player = event.getEntity();
//        if (player instanceof AbstractClientPlayer clientPlayer) {
//            Input input = event.getInput();
//            if (!getInput(input)) {
//                return;
//            }
//            CompoundTag nbt = player.getPersistentData();
//            if (!(player.getWeaponItem().getItem() instanceof AnimAttackItem item)) {
//                return;
//            }
//            if (!(nbt.getInt(TemporaryAttribute.PLAYER_USE_ITEM_TICK) >= item.freMovementTick())) {
//                return;
//            }
//            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
//            animationStack.removeLayer(0);
//        }
    }

    private static @NotNull Item getItem(Player event) {
        return event.getUseItem().getItem();
    }

    public static void restorePlayerSpeed(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(TemporaryAttribute.PLAYER_SPECIAL_WEAPON_ATTACK, false);
        float playerSpeed = player.getPersistentData().getFloat(PLAYER_RECORD_SPEED);
        if (playerSpeed == 0) {
            playerSpeed = 0.1F;
        }
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(playerSpeed);
    }

    public static void restoreTick(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt(TemporaryAttribute.PLAYER_USE_ITEM_TICK, 0);
    }

    public static boolean getInput(Input input) {
        return input.up || input.down || input.left || input.right || input.jumping;
    }

    public static void cancelAnimation(Player player) {
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(0);
        }
    }
}
