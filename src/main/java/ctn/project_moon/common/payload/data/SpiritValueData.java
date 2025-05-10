package ctn.project_moon.common.payload.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_VALUE;
import static ctn.project_moon.api.SpiritAttribute.getSpiritValue;

public record SpiritValueData(double spiritValue) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<SpiritValueData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.spirit_value"));
	public static final StreamCodec<FriendlyByteBuf, SpiritValueData> CODEC =
			CustomPacketPayload.codec(SpiritValueData::write, SpiritValueData::of);

	public static SpiritValueData of(FriendlyByteBuf buf) {
		return new SpiritValueData(buf.readDouble());
	}

	public static SpiritValueData create(ServerPlayer serverPlayer) {
		return new SpiritValueData(getSpiritValue(serverPlayer));
	}

	public void write(ByteBuf buffer) {
		buffer.writeDouble(this.spiritValue);
	}

	@Override
	public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void client(final SpiritValueData data, final IPayloadContext context) {
		context.player().getPersistentData().putDouble(SPIRIT_VALUE, data.spiritValue);
	}

	public static void server(final SpiritValueData data, final IPayloadContext context) {
	}
}
