package ctn.project_moon.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PmImageSprite extends AbstractWidget {
	private final ResourceLocation texture;
	
	public PmImageSprite(int x, int y, int width, int height, ResourceLocation texture, Component component) {
		super(x, y, width, height, component);
		this.texture       = texture;
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.blitSprite(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	@Override
	protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.renderTooltip(Minecraft.getInstance().font, getMessage(), mouseX, mouseY);
	}
	
	public ResourceLocation getTexture() {
		return texture;
	}
}
