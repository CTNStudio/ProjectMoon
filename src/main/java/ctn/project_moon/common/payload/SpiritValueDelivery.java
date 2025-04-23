package ctn.project_moon.common.payload;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.SpiritTool.getSpiritValue;

public record SpiritValueDelivery(float spiritValue) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SpiritValueDelivery> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_value_delivery"));
    public static final StreamCodec<FriendlyByteBuf, SpiritValueDelivery> CODEC =
            CustomPacketPayload.codec(SpiritValueDelivery::write, SpiritValueDelivery::of);

    public static SpiritValueDelivery of(FriendlyByteBuf buf) {
        return new SpiritValueDelivery(buf.readFloat());
    }

    public static SpiritValueDelivery create(ServerPlayer serverPlayer){
        return new SpiritValueDelivery(getSpiritValue(serverPlayer));
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
