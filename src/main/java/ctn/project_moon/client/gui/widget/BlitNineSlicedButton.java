package ctn.project_moon.client.gui.widget;

import ctn.project_moon.client.gui.tool.blit_nine_sliced.SliceSprite;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 切片精灵按钮
 * @author 尽
 */
public class BlitNineSlicedButton extends ImageButton {
	private static final ResourceLocation EMPTY_RESORCE_LOCATION = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/empty.png");
	private static final WidgetSprites EMPTY_SPRITES          = new WidgetSprites(EMPTY_RESORCE_LOCATION, EMPTY_RESORCE_LOCATION);
	private final SliceSprite sprite;
	
	public BlitNineSlicedButton(
			WidgetSprites widgetSprites,
			int x, int y,
			int uWidth, int vHeight,
			int uOffset, int vOffset,
			int width, int height,
			int left, int top, int right, int bottom,
			OnPress onPress, Component message) {
		this(widgetSprites, x, y,
				new SliceSprite(
						uWidth, vHeight,
						uOffset, vOffset,
						width, height,
						left, top, right, bottom),
				onPress, message);
	}
	
	public BlitNineSlicedButton(WidgetSprites widgetSprites, int x, int y, SliceSprite sprite, OnPress onPress, Component message) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), widgetSprites, onPress, message);
		this.sprite = sprite;
	}
	
	@Override
	public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isHoveredOrFocused());
		sprite.blitNineSliced(resourcelocation, guiGraphics, getX(), getY());
	}
}
