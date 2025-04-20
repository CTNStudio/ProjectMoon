package ctn.project_moon.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import static ctn.project_moon.PmMain.MOD_ID;

/** 不保存在存档的实体属性 */
public class TemporaryAttribute {
    /** 临时属性前置 */
    private static final String PREFIX = MOD_ID + ".temporary";
    /** 当前速度 */
    public static final String PLAYER_RECORD_SPEED = PREFIX + ".player.record.speed";
    /** 是否使用特殊武器 */
    public static final String PLAYER_SPECIAL_WEAPON_ATTACK = PREFIX + ".player.special_weapon.attack";
    /** 使用物品后播放tick */
    public static final String PLAYER_USE_ITEM_TICK = PREFIX + ".player.player.use_item.tick";
    /** 使用物品多久 */
    public static final String PLAYER_USE_TICK = PREFIX + ".player.player.use.tick";

    public static void resetTemporaryAttribute(Player player){
        CompoundTag nbt = player.getPersistentData();
        if (player.getSpeed() == 0) {
            nbt.putFloat(PLAYER_RECORD_SPEED, 0.1f);
        }
        nbt.putBoolean(PLAYER_SPECIAL_WEAPON_ATTACK, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
    }
}
