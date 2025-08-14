package ctn.project_moon.common.payloadInit.data;

import ctn.project_moon.common.payloadInit.data.open_screen.OpenMenuData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;

public class PmDataTool {
	/// 打开菜单GUI
	public static void openMenu(OpenMenuData data, IPayloadContext context, MenuProvider menuProvider) {
		context.enqueueWork(() -> {
			Player player = context.player();
			
			if (player instanceof ServerPlayer serverPlayer) {
				ItemStack stack = player.isCreative() ? data.carried() : player.containerMenu.getCarried();
				player.containerMenu.setCarried(ItemStack.EMPTY);
				player.openMenu(menuProvider);
				
				if (!stack.isEmpty()) {
					player.containerMenu.setCarried(stack);
					PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
				}
			}
		});
	}
}
