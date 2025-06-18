package ctn.project_moon.common.payloadInit.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.attr.MobGeneralAttribute.RATIONALITY_VALUE;
import static ctn.project_moon.api.attr.RationalityAttribute.getRationalityValue;

/// 理智值
public record RationalityValueData(double rationalityValue) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<RationalityValueData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.rationality_value"));
	
	public static final StreamCodec<FriendlyByteBuf, RationalityValueData> CODEC =
			CustomPacketPayload.codec(RationalityValueData::encoder, RationalityValueData::decoder);
	
	public static RationalityValueData decoder(FriendlyByteBuf buf) {
		return new RationalityValueData(buf.readDouble());
	}
	
	public static RationalityValueData create(ServerPlayer serverPlayer) {
		return new RationalityValueData(getRationalityValue(serverPlayer));
	}
	
	public static void server(final RationalityValueData data, final IPayloadContext context) {
		context.player().getPersistentData().putDouble(RATIONALITY_VALUE, data.rationalityValue);
	}
	
	public static void client(final RationalityValueData data, final IPayloadContext context) {
	}
	
	public void encoder(ByteBuf buffer) {
		buffer.writeDouble(this.rationalityValue);
	}
	
	@Override
	public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
