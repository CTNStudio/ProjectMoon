package ctn.project_moon.common.payload.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.SpiritTool.getSpiritValue;

public record SpiritValueData(float spiritValue) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SpiritValueData> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_value_delivery"));
    public static final StreamCodec<FriendlyByteBuf, SpiritValueData> CODEC =
            CustomPacketPayload.codec(SpiritValueData::write, SpiritValueData::of);

    public static SpiritValueData of(FriendlyByteBuf buf) {
        return new SpiritValueData(buf.readFloat());
    }

    public static SpiritValueData create(ServerPlayer serverPlayer){
        return new SpiritValueData(getSpiritValue(serverPlayer));
    }

    public void write(ByteBuf buffer) {
        buffer
                .writeFloat(this.spiritValue);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
