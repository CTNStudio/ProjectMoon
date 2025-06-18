package ctn.project_moon.common.payloadInit.data.skill;

import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IPlayerMixin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * @param skill 技能堆栈
 * @param index 位置索引
 */
public record SkillsData(SkillStack skill, int index) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<SkillsData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.stack_data"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, SkillsData> CODEC = StreamCodec.composite(SkillStack.STREAM_CODEC, SkillsData::skill, ByteBufCodecs.INT, SkillsData::index, SkillsData::new);
	
	public static void server(final SkillsData data, final IPayloadContext context) {
		((IPlayerMixin) context.player()).getSkillHandler().setSkill(data.skill(), data.index());
	}
	
	@Override
	public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
