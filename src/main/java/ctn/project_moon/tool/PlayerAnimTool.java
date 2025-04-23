package ctn.project_moon.tool;

import ctn.project_moon.api.TemporaryNbtAttribute;
import ctn.project_moon.common.item.AnimAttackItem;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import static ctn.project_moon.api.TemporaryNbtAttribute.*;

public class PlayerAnimTool {
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

    /** 设置物品计时 */
    public static void setUseItemTick(CompoundTag nbt, int tick) {
        nbt.putInt(PLAYER_USE_ITEM_TICK, tick);
    }

    /** 增加使用状态 */
    public static void incrementUseItemTick(CompoundTag nbt, int tick) {
        PmTool.incrementNbt(nbt, PLAYER_USE_ITEM_TICK, tick);
    }

    /**
     * 增加使用计时
     */
    public static void incrementUseTick(CompoundTag nbt, int tick) {
        PmTool.incrementNbt(nbt, PLAYER_USE_TICK, tick);
    }

    /**
     * 设置使用计时
     */
    public static void setUseTick(CompoundTag nbt, int tick) {
        nbt.putInt(PLAYER_USE_TICK, tick);
    }

    /** 判断玩家是否移动操作 */
    public static boolean isInput(Input input) {
        return input.up || input.down || input.left || input.right || input.jumping;
    }

    /** 这个方法移除第0和第1层动画 这俩层用于{@link AnimAttackItem}的使用 */
    public static void cancelAnimationLayer(Player player) {
        AnimationStack animationStack = getAnimationStack(player);
        if (animationStack == null) return;
        animationStack.removeLayer(0);
        animationStack.removeLayer(1);
    }

    /** 这个方法移除第1层动画 这层用于持续动画 */
    public static void removeLayer1(Player player) {
        AnimationStack animationStack = getAnimationStack(player);
        if (animationStack == null) return;
        animationStack.removeLayer(1);
    }

    public static @Nullable AnimationStack getAnimationStack(Player player) {
        AbstractClientPlayer client = null;
        if (player instanceof AbstractClientPlayer clientPlayer) {
            client = clientPlayer;
        } else {
            if (Minecraft.getInstance().player instanceof AbstractClientPlayer clientPlayer){
                client = clientPlayer;
            }
        }
        if (client == null) {
            return null;
        }
        return PlayerAnimationAccess.getPlayerAnimLayer(client);
    }
}
