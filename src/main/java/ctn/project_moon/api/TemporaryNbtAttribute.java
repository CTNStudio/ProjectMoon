package ctn.project_moon.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import static ctn.project_moon.PmMain.MOD_ID;

/** 不保存在存档的实体属性 */
public class TemporaryNbtAttribute {
    /** 临时属性前置 */
    private static final String PREFIX = MOD_ID + ".temporary";
    /** 当前速度 */
    public static final String PLAYER_RECORD_SPEED = PREFIX + ".player.record_speed";
    /** 是否正在使用武器 */
    public static final String IS_PLAYER_USE_ITEM = PREFIX + ".player.is_use_item";
    /** 是否正在攻击 */
    public static final String IS_PLAYER_ATTACK = PREFIX + ".player.is_attack";
    /** 使用物品tick */
    public static final String PLAYER_USE_ITEM_TICK = PREFIX + ".player.use_item_tick";
    /** 使用(长按)物品tick */
    public static final String PLAYER_USE_TICK = PREFIX + ".player.use_tick";

    /** 重置临时属性 */
    public static void resetTemporaryAttribute(Player player){
        CompoundTag nbt = player.getPersistentData();
        if (player.getSpeed() == 0) {
            nbt.putFloat(PLAYER_RECORD_SPEED, 0.1f);
        }
        nbt.putBoolean(IS_PLAYER_USE_ITEM, false);
        nbt.putBoolean(IS_PLAYER_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(nbt.getFloat(PLAYER_RECORD_SPEED));
    }
}
