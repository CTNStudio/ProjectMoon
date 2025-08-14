package ctn.project_moon.common.payloadInit.data.open_screen;

import ctn.project_moon.common.inventory.container.PlayerSkillContainerProvider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.payloadInit.data.PmDataTool.openMenu;

/**
 * @author 尽
 */
public record OpenPlayerSkillScreenData(ItemStack carried) implements OpenMenuData {
	public static final CustomPacketPayload.Type<OpenPlayerSkillScreenData> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.open_player_skill_screen"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenPlayerSkillScreenData> CODEC =
			StreamCodec.composite(
					ItemStack.OPTIONAL_STREAM_CODEC, OpenPlayerSkillScreenData::carried,
					OpenPlayerSkillScreenData::new);
	
	public static void toServer(final OpenPlayerSkillScreenData data, final IPayloadContext context) {
		openMenu(data, context, new PlayerSkillContainerProvider());
	}
	
	@Nonnull
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
