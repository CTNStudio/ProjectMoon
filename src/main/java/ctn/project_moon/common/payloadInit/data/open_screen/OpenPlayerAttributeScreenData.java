package ctn.project_moon.common.payloadInit.data.open_screen;

import ctn.project_moon.common.inventory.container.PlayerAttributeContainerProvider;
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
 * 打开玩家属性界面
 */
public record OpenPlayerAttributeScreenData(ItemStack carried) implements OpenMenuData {
	
	public static final Type<OpenPlayerAttributeScreenData> TYPE =
			new Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.open_player_attribute_screen"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenPlayerAttributeScreenData> CODEC =
			StreamCodec.composite(
					ItemStack.OPTIONAL_STREAM_CODEC, OpenPlayerAttributeScreenData::carried,
					OpenPlayerAttributeScreenData::new);
	
	public static void toServer(final OpenPlayerAttributeScreenData data, final IPayloadContext context) {
		openMenu(data, context, new PlayerAttributeContainerProvider());
	}
	
	@Nonnull
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
