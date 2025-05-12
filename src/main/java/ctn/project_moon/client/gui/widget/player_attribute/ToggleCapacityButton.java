package ctn.project_moon.client.gui.widget.player_attribute;

import ctn.project_moon.client.gui.widget.Switch2Button;
import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 属性\抗性切换按钮
 * @author 小尽
 */
@OnlyIn(Dist.CLIENT)
public final class ToggleCapacityButton extends Switch2Button {
	public static final String TOOLTIP = MOD_ID + ".gui.player_attribute.toggle_capacity_button.message";

	/**
	 * 构造一个 Switch2Button 实例
	 *
	 * @param screen
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 */
	public ToggleCapacityButton(PlayerAttributeScreen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart) {
		super(screen, resourceLocation, x, y, width, height, xTexStart, yTexStart, (onPress) -> {
			screen.toggleCapacity();
		}, false);
		message = Component.translatable(TOOLTIP);
	}
}
