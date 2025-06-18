package ctn.project_moon.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Switch2Button 类继承自 ImageButton，用于创建具有开关功能的图像按钮
 * <p>
 * <b>使用需满足：</b>
 * <li>打开、打开悬停、关闭、关闭悬停 这四个状态</li>
 * <li>都在一个文件里，按这个顺序水平排序</li>
 * <li>每个纹理之间只有一格间隙</li>
 * <li>每个纹理都等同与输入的按钮大小</li>
 * <p>
 * 部分注释由AI生成并由人工补充
 *
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class Switch2Button extends ImageButton {
	protected final Screen           screen;
	// 按钮纹理资源的位置
	protected final ResourceLocation resourceLocation;
	// 按钮纹理在资源图像中的起始x坐标
	protected final int              xTexStart;
	// 按钮纹理在资源图像中的起始y坐标
	protected final int              yTexStart;
	// 按钮纹理的宽度
	protected final int              textureWidth;
	// 按钮纹理的高度
	protected final int              textureHeight;
	// 按钮是否具有特殊渲染逻辑
	private final   boolean          isSpecial;
	protected       Component        message;
	// 按钮当前是否处于打开状态
	protected       boolean          isOpen;
	
	/**
	 * 构造一个 Switch2Button 实例
	 *
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 * @param onPress          按钮被按下时的回调接口
	 */
	public Switch2Button(Screen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart, OnPress onPress) {
		this(screen, resourceLocation, x, y, width, height, xTexStart, yTexStart, onPress, true);
	}
	
	/**
	 * 构造一个 Switch2Button 实例，包含是否特殊的标志
	 *
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 * @param onPress          按钮被按下时的回调接口
	 * @param isSpecial        按钮是否具有特殊渲染逻辑
	 */
	public Switch2Button(Screen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart, OnPress onPress, boolean isSpecial) {
		this(screen, resourceLocation, x, y, width, height, xTexStart, yTexStart, 256, 256, onPress, isSpecial);
	}
	
	/**
	 * 构造一个 Switch2Button 实例，包含按钮纹理的宽度和高度
	 *
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 * @param textureWidth     按钮纹理的宽度
	 * @param textureHeight    按钮纹理的高度
	 * @param onPress          按钮被按下时的回调接口
	 */
	public Switch2Button(Screen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart, int textureWidth, int textureHeight, OnPress onPress) {
		this(screen, resourceLocation, x, y, width, height, xTexStart, yTexStart, textureWidth, textureHeight, onPress, true);
	}
	
	/**
	 * 构造一个 Switch2Button 实例，包含是否特殊的标志，以及按钮纹理的宽度和高度
	 *
	 * @param screen
	 * @param resourceLocation 按钮纹理资源的位置
	 * @param x                按钮的x坐标
	 * @param y                按钮的y坐标
	 * @param width            按钮的宽度
	 * @param height           按钮的高度
	 * @param xTexStart        按钮纹理在资源图像中的起始x坐标
	 * @param yTexStart        按钮纹理在资源图像中的起始y坐标
	 * @param textureWidth     按钮纹理的宽度
	 * @param textureHeight    按钮纹理的高度
	 * @param onPress          按钮被按下时的回调接口
	 * @param isSpecial        按钮是否具有特殊渲染逻辑
	 */
	public Switch2Button(Screen screen, ResourceLocation resourceLocation, int x, int y, int width, int height, int xTexStart, int yTexStart, int textureWidth, int textureHeight, OnPress onPress, boolean isSpecial) {
		super(
				x, y, width, height, null, new PmOnPressAbstract(onPress) {
					@Override
					public void on(Button button) {
						if (button instanceof Switch2Button switchButton) switchButton.change();
					}
				});
		this.screen           = screen;
		this.resourceLocation = resourceLocation;
		this.xTexStart        = xTexStart;
		this.yTexStart        = yTexStart;
		this.textureWidth     = textureWidth;
		this.textureHeight    = textureHeight;
		this.isSpecial        = isSpecial;
	}
	
	/**
	 * 切换按钮的打开状态
	 */
	public void change() {
		this.isOpen = !isOpen;
	}
	
	/**
	 * 获取按钮的打开状态
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * 渲染按钮，根据按钮的状态和是否悬停来确定渲染的纹理部分
	 */
	@Override
	public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (isSpecial) {
			return;
		}
		int x = this.xTexStart;
		if (isOpen) {
			x += 2 * (width + 1);
		}
		if (isHovered()) {
			x += width + 1;
		}
		render(guiGraphics, x);
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	/**
	 * 在特殊情况下替换默认的按钮渲染方法
	 */
	public void renderWidgetOverlay(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		if (!isSpecial) {
			return;
		}
		int x = this.xTexStart;
		x += overlayOpen(guiGraphics, mouseX, mouseY, partialTicks);
		if (isHovered()) {
			x += width + 1;
		}
		render(guiGraphics, x);
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	/**
	 * 渲染按钮的纹理
	 */
	protected void render(GuiGraphics guiGraphics, float x) {
		RenderSystem.disableDepthTest();
		guiGraphics.blit(
				this.resourceLocation,
				this.getX(), this.getY(),
				x, this.yTexStart,
				this.width, this.height,
				textureWidth, textureHeight);
		RenderSystem.enableDepthTest();
	}
	
	/**
	 * 特殊执行的渲染逻辑，返回渲染的纹理部分的偏移量
	 */
	public int overlayOpen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		return 0;
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (message == null) {
			return;
		}
		guiGraphics.renderTooltip(screen.getMinecraft().font, message, mouseX, mouseY);
	}
}
