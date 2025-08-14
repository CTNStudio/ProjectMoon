package ctn.project_moon.common.payloadInit.data.skill;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

public record SkillSlotIndexData(int index) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<SkillSlotIndexData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.skill_slot_index_data"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, SkillSlotIndexData> CODEC =
			StreamCodec.composite(ByteBufCodecs.INT, SkillSlotIndexData::index, SkillSlotIndexData::new);
	
	public static void bidirectional(final SkillSlotIndexData data, final IPayloadContext context) {
		IModPlayerMixin playerMixin = ((IModPlayerMixin) context.player());
		ISkillHandler handler = playerMixin.getSkillHandler();
		
		handler.setSkillSlotIndex(data.index());
	}
	
	@Override
	public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
