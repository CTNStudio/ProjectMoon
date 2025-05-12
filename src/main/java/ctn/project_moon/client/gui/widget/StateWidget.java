package ctn.project_moon.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 多重状态图像控件(2维)
 * @author 小尽
 */
@OnlyIn(Dist.CLIENT)
public class StateWidget extends AbstractWidget {
	protected final ResourceLocation texture;
	protected int stateU = 0;// 序列从0开始
	protected int stateV = 0;// 序列从0开始
	protected final int uOffset;
	protected final int vOffset;

	public StateWidget(int x, int y, int width, int height, int uOffset, int vOffset, ResourceLocation texture) {
		super(x, y, width, height, Component.empty());
		this.uOffset = uOffset;
		this.vOffset = vOffset;
		this.texture = texture;
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		int uOffset = this.uOffset + (width + 1) * stateU;
		int vOffset = this.vOffset + (height + 1) * stateV;
		if (isHovered()){
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
		guiGraphics.blit(texture, getX(), getY(), uOffset, vOffset, width, height);
	}

	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

	public int getStateU() {
		return stateU;
	}

	public void setStateU(int stateU) {
		this.stateU = stateU;
	}

	public int getUOffset() {
		return uOffset;
	}

	public int getVOffset() {
		return vOffset;
	}

	public int getStateV() {
		return stateV;
	}

	public void setStateV(int stateV) {
		this.stateV = stateV;
	}
}
