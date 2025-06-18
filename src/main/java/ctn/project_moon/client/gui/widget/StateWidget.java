package ctn.project_moon.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * 多重状态图像控件(2维)
 *
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class StateWidget extends AbstractWidget {
	protected final ResourceLocation texture;
	protected final List<Component>  messages = new ArrayList<>();
	protected final int              uOffset;
	protected final int              vOffset;
	protected       int              stateU   = 0;// 序列从0开始
	protected       int              stateV   = 0;// 序列从0开始
	protected       Tooltip          tooltip;
	
	public StateWidget(int x, int y,
			int width, int height,
			int uOffset, int vOffset, ResourceLocation texture, Component component) {
		super(x, y, width, height, component);
		this.uOffset = uOffset;
		this.vOffset = vOffset;
		this.texture = texture;
	}
	
	@Override
	protected void renderWidget(GuiGraphics guiGraphics,
			int mouseX, int mouseY, float partialTick) {
		int uOffset = this.uOffset + (width + 1) * stateU;
		int vOffset = this.vOffset + (height + 1) * stateV;
		guiGraphics.blit(texture, getX(), getY(), uOffset, vOffset, width, height);
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (tooltip != null) {
			tooltip.run(this, guiGraphics, mouseX, mouseY);
		}
		if (messages.isEmpty()) {
			guiGraphics.renderTooltip(Minecraft.getInstance().font, getMessage(), mouseX, mouseY);
			return;
		}
		guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, messages, mouseX, mouseY);
	}
	
	public List<Component> getMessageList() {
		return messages;
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
	}
	
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
	
	public interface Tooltip {
		void run(StateWidget widget, GuiGraphics guiGraphics, int mouseX, int mouseY);
	}
}
