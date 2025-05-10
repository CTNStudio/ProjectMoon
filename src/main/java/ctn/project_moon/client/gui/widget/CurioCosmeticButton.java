package ctn.project_moon.client.gui.widget;

import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.common.network.client.CPacketToggleCosmetics;

public class CurioCosmeticButton extends SwitchButton {
	private final PlayerAttributeScreen screen;

	/**
	 * 构造一个 SwitchButton 实例
	 *
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 */
	public CurioCosmeticButton(PlayerAttributeScreen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart) {
		super(resourceLocation, x, y, width, height, xTexStart, yTexStart, (button) -> {
			screen.getMenu().toggleCosmetics();
			PacketDistributor.sendToServer(new CPacketToggleCosmetics(screen.getMenu().containerId));
		}, true);
		this.screen = screen;
	}

	@Override
	public int overlayOpen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		return screen.getMenu().isViewingCosmetics ? 2 * (width + 1) : 0;
	}

}
