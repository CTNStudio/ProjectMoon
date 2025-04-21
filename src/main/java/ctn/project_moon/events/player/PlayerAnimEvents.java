package ctn.project_moon.events.player;

import ctn.project_moon.api.TemporaryAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import ctn.project_moon.common.item.AnimItem;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.Minecraft;
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
import static ctn.project_moon.api.TemporaryAttribute.PLAYER_USE_TICK;

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

    /** 还原玩家速度
     * <p>
     * 不要在玩家移动的时保存
     */
    public static void restorePlayerSpeed(Player player) {
        float playerSpeed = player.getPersistentData().getFloat(PLAYER_RECORD_SPEED);
        if (playerSpeed == 0) {
            playerSpeed = 0.1F;
        }
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(playerSpeed);
    }

    /** 重置物品计时 */
    public static void restoreItemTick(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt(TemporaryAttribute.PLAYER_ITEM_TICK, 0);
    }

    /** 重置使用计时 */
    public static void restoreUseTick(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putInt(PLAYER_USE_TICK, 0);
    }

    /** 完成攻击 */
    public static void completeAttack(Player player) {
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(TemporaryAttribute.PLAYER_IS_USE_ITEM, false);
    }

    /** 判断玩家是否移动操作 */
    public static boolean getInput(Input input) {
        return input.up || input.down || input.left || input.right || input.jumping;
    }

    /** 这个方法移除第0和第1层动画 这俩层用于{@link AnimAttackItem}的使用 */
    public static void cancelAnimationLayer(Player player) {
        AbstractClientPlayer client = null;
        if (player instanceof AbstractClientPlayer clientPlayer) {
            client = clientPlayer;
        } else {
            if (Minecraft.getInstance().player instanceof AbstractClientPlayer clientPlayer){
                client = clientPlayer;
            }
        }
        if (client == null) {
            return;
        }
        AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(client);
        animationStack.removeLayer(0);
        animationStack.removeLayer(1);
    }

    /** 这个方法移除第1层动画 这层用于持续动画 */
    public static void removeLayer1(Player player) {
        if (player instanceof AbstractClientPlayer clientPlayer) {
            AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(clientPlayer);
            animationStack.removeLayer(1);
        }
    }
}
