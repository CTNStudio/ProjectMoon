package ctn.project_moon.common.payloadInit.data.skill;

import ctn.project_moon.capability_provider.EntitySkillHandler;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IPlayerMixin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;

public record AllSkillsData(List<SkillStack> skills) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<AllSkillsData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.all_stack_data"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, AllSkillsData> CODEC =
			StreamCodec.composite(SkillStack.LIST_STREAM_CODEC, AllSkillsData::skills, AllSkillsData::new);
	
	public static void server(final AllSkillsData data, final IPayloadContext context) {
		Player player = context.player();
		((IPlayerMixin) player).setSkillHandler(new EntitySkillHandler(player, data.skills()));
	}
	
	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
