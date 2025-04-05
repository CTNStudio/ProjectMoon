package ctn.project_moon.common.payload;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static ctn.project_moon.PmMain.MOD_ID;

public record SpiritValueDelivery(float spiritValue, float maxSpiritValue, float minSpiritValue) implements CustomPacketPayload {

    // TODO 这里
    public static final CustomPacketPayload.Type<SpiritValueDelivery> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_value_delivery"));
    public static final StreamCodec<FriendlyByteBuf, SpiritValueDelivery> CODEC =
            CustomPacketPayload.codec(SpiritValueDelivery::write, SpiritValueDelivery::of);

    public static SpiritValueDelivery of(FriendlyByteBuf buf) {
        return new SpiritValueDelivery(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void write(ByteBuf buffer) {
        buffer
                .writeFloat(this.spiritValue)
                .writeFloat(this.maxSpiritValue)
                .writeFloat(this.maxSpiritValue);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
