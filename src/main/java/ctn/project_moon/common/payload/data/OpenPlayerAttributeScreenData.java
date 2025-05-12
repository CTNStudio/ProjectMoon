package ctn.project_moon.common.payload.data;

import ctn.project_moon.common.inventory.container.PlayerAttributeContainerProvider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;

import javax.annotation.Nonnull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 打开玩家属性界面
 */
public record OpenPlayerAttributeScreenData(ItemStack carried) implements CustomPacketPayload {

	public static final Type<OpenPlayerAttributeScreenData> TYPE =
			new Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "data.open_player_attribute_screen"));

	public static final StreamCodec<RegistryFriendlyByteBuf, OpenPlayerAttributeScreenData> CODEC =
			StreamCodec.composite(
					ItemStack.OPTIONAL_STREAM_CODEC,
					OpenPlayerAttributeScreenData::carried,
					OpenPlayerAttributeScreenData::new);

	@Nonnull
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	public static void client(final OpenPlayerAttributeScreenData data, final IPayloadContext context) {
		context.enqueueWork(() -> {
			Player player = context.player();

			if (player instanceof ServerPlayer serverPlayer) {
				ItemStack stack = player.isCreative() ? data.carried() : player.containerMenu.getCarried();
				player.containerMenu.setCarried(ItemStack.EMPTY);
				player.openMenu(new PlayerAttributeContainerProvider());

				if (!stack.isEmpty()) {
					player.containerMenu.setCarried(stack);
					PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
				}
			}
		});
	}
}
