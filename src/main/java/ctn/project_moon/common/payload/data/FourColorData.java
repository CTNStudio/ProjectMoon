package ctn.project_moon.common.payload.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.PlayerAttribute.*;

public record FourColorData(int fortitude, int prudence, int temperance, int justice)  implements CustomPacketPayload {

	public static final CustomPacketPayload.Type<FourColorData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.four_color"));

	public static final StreamCodec<ByteBuf, FourColorData> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, FourColorData::fortitude,
			ByteBufCodecs.INT, FourColorData::prudence,
			ByteBufCodecs.INT, FourColorData::temperance,
			ByteBufCodecs.INT, FourColorData::justice,
			FourColorData::new
	);

	@Override
	public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void server(final FourColorData data, final IPayloadContext context) {
		CompoundTag nbt = context.player().getPersistentData();
		nbt.putInt(BASE_FORTITUDE, data.fortitude());
		nbt.putInt(BASE_PRUDENCE, data.prudence());
		nbt.putInt(BASE_TEMPERANCE, data.temperance());
		nbt.putInt(BASE_JUSTICE, data.justice());

	}
}
