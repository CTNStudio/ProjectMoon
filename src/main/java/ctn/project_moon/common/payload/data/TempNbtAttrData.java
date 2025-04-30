package ctn.project_moon.common.payload.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import static ctn.project_moon.PmMain.MOD_ID;

public record TempNbtAttrData(boolean isPlayerUseItem, boolean isPlayerAttack , boolean isPlayerSwitchItems,
                              boolean isPlayerRotatingPerspective, boolean isPlayerMoved,
                              int playerUseItemTick, int playerUseTick) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TempNbtAttrData> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "temp_nbt_attr_data"));

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

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
