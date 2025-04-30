package ctn.project_moon.api;

import ctn.project_moon.common.payload.data.TempNbtAttrData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import static ctn.project_moon.PmMain.MOD_ID;

/** 不保存在存档的实体临时属性 */
public class TempNbtAttr {
    /** 临时属性前置 */
    private static final String PREFIX = MOD_ID + ".temporary.";
    /** 正在使用武器 */
    public static final String PLAYER_USE_ITEM = PREFIX + "player.use_item";
    /** 正在攻击
     * <p>
     * 为true时 禁用对方块、实体使用，攻击
     */
    public static final String PLAYER_ATTACK = PREFIX + "player.attack";
    /** 使用物品tick */
    public static final String PLAYER_USE_ITEM_TICK = PREFIX + "player.use_item_tick";
    /** 使用(长按)物品tick */
    public static final String PLAYER_USE_TICK = PREFIX + "player.use_tick";
    /** 禁用切换物品插槽
     * <p>
     * 为true时 禁用交换左右手物品，滚轮、数字键切换物品，<br>
     * 丢弃物品，鼠标中键复制等，打开背包<br>
     * 注：在ui中或者强制扔掉、移除物品会导致这些无法正常关闭，死亡或重进恢复
     */
    public static final String CANNOT_PLAYER_SWITCH_ITEMS = PREFIX + "player.cannot_switch_items";
    /** 禁用可以转动视角
     * <p>
     * 为true时 阻止玩家转动视角
     */
    public static final String CANNOT_PLAYER_ROTATING_PERSPECTIVE = PREFIX + "player.cannot_rotating_perspective";
    /** 禁用可以移动
     * <p>
     * 为true时 禁用WASD对应的移动、跳跃、潜行（会强行解除该状态）、以及当前速度（会强行停止）<br>
     * 注：对应的是按键绑定的按键
     * */
    public static final String CANNOT_PLAYER_MOVED = PREFIX + "player.cannot_moved";
    /** 物品计时 */
    public static final String ITEM_TICK = PREFIX + "player.item_tick";

    /** 重置临时属性 */
    public static void resetTemporaryAttribute(Player player){
        CompoundTag nbt = player.getPersistentData();
        nbt.putBoolean(PLAYER_USE_ITEM, false);
        nbt.putBoolean(PLAYER_ATTACK, false);
        nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
        nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
        nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
        nbt.putInt(PLAYER_USE_TICK, 0);
    }
}
