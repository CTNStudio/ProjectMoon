package ctn.project_moon.tool;

import net.minecraft.client.player.Input;
import net.minecraft.nbt.CompoundTag;

import static ctn.project_moon.api.TempNbtAttribute.PLAYER_USE_ITEM_TICK;
import static ctn.project_moon.api.TempNbtAttribute.PLAYER_USE_TICK;

public class PlayerAnimTool {
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
}
