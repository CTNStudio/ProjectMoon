package ctn.project_moon.common.payloadInit.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.attr.TempNbtAttribute.*;

public record TempNbtAttrData(boolean isPlayerUseItem, boolean isPlayerAttack, boolean isPlayerSwitchItems,
                              boolean isPlayerRotatingPerspective, boolean isPlayerMoved,
                              int playerUseItemTick, int playerUseTick) implements CustomPacketPayload {
	
	public static final CustomPacketPayload.Type<TempNbtAttrData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.temp_nbt_attr"));
	
	public static final StreamCodec<ByteBuf, TempNbtAttrData> CODEC = NeoForgeStreamCodecs.composite(
			ByteBufCodecs.BOOL, TempNbtAttrData::isPlayerUseItem,
			ByteBufCodecs.BOOL, TempNbtAttrData::isPlayerAttack,
			ByteBufCodecs.BOOL, TempNbtAttrData::isPlayerSwitchItems,
			ByteBufCodecs.BOOL, TempNbtAttrData::isPlayerRotatingPerspective,
			ByteBufCodecs.BOOL, TempNbtAttrData::isPlayerMoved,
			ByteBufCodecs.INT, TempNbtAttrData::playerUseItemTick,
			ByteBufCodecs.INT, TempNbtAttrData::playerUseTick,
			TempNbtAttrData::new
	);
	
	public static void server(final TempNbtAttrData data, final IPayloadContext context) {
		CompoundTag nbt = context.player().getPersistentData();
		nbt.putBoolean(PLAYER_USE_ITEM, data.isPlayerUseItem());
		nbt.putBoolean(PLAYER_ATTACK, data.isPlayerAttack());
		nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, data.isPlayerSwitchItems());
		nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, data.isPlayerRotatingPerspective());
		nbt.putBoolean(CANNOT_PLAYER_MOVED, data.isPlayerMoved());
		nbt.putInt(PLAYER_USE_ITEM_TICK, data.playerUseItemTick());
		nbt.putInt(PLAYER_USE_TICK, data.playerUseTick());
	}
	
	@Override
	public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
