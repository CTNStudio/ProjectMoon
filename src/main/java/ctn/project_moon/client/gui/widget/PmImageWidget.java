package ctn.project_moon.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PmImageWidget extends AbstractWidget {
	private final ResourceLocation texture;
	private final int              textureWidth;
	private final int              textureHeight;
	private final float            uOffset;
	private final float            vOffset;
	
	public PmImageWidget(int x, int y,
			int width, int height,
			ResourceLocation texture,
			int textureWidth, int textureHeight,
			float uOffset, float vOffset,
			Component component) {
		super(x, y, width, height, component);
		this.texture       = texture;
		this.textureWidth  = textureWidth;
		this.textureHeight = textureHeight;
		this.uOffset       = uOffset;
		this.vOffset       = vOffset;
	}
	
	public PmImageWidget(int x, int y,
			int width, int height,
			ResourceLocation texture,
			float uOffset, float vOffset,
			Component component) {
		this(x, y, width, height, texture, 256, 256, uOffset, vOffset, component);
	}
	
	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.blit(
				this.getTexture(),
				this.getX(), this.getY(),
				this.getWidth(), this.getHeight(),
				getUOffset(), getVOffset(),
				getWidth(), getHeight(),
				getTextureWidth(), getTextureHeight());
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.renderTooltip(Minecraft.getInstance().font, getMessage(), mouseX, mouseY);
	}
	
	public ResourceLocation getTexture() {
		return texture;
	}
	
	public int getTextureWidth() {
		return textureWidth;
	}
	
	public int getTextureHeight() {
		return textureHeight;
	}
	
	public float getUOffset() {
		return uOffset;
	}
	
	public float getVOffset() {
		return vOffset;
	}
}
