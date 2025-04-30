package ctn.project_moon.common.payload.handler;

import ctn.project_moon.common.payload.data.TempNbtAttrData;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static ctn.project_moon.api.TempNbtAttr.*;

public class TempNbtAttrPayloadHandler {
    public static void client(final TempNbtAttrData data, final IPayloadContext context){
        CompoundTag nbt = context.player().getPersistentData();
        nbt.putBoolean(PLAYER_USE_ITEM, data.isPlayerUseItem());
        nbt.putBoolean(PLAYER_ATTACK, data.isPlayerAttack());
        nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, data.isPlayerSwitchItems());
        nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, data.isPlayerRotatingPerspective());
        nbt.putBoolean(CANNOT_PLAYER_MOVED, data.isPlayerMoved());
        nbt.putInt(PLAYER_USE_ITEM_TICK, data.playerUseItemTick());
        nbt.putInt(PLAYER_USE_TICK, data.playerUseTick());
    }

    public static void server(final TempNbtAttrData data, final IPayloadContext context){
    }
}
