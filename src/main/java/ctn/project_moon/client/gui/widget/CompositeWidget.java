package ctn.project_moon.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 复合控件
 *
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class CompositeWidget<T extends AbstractWidget> extends AbstractWidget {
	public final T       widget1;
	public final T       widget2;
	private      boolean state = true;
	
	public CompositeWidget(int x, int y, int width, int height, T widget1, T widget2) {
		super(x, y, width, height, Component.empty());
		this.widget1 = widget1;
		this.widget2 = widget2;
		setX(x);
		setY(y);
	}
	
	@Override
	public void setX(int x) {
		super.setX(x);
		widget1.setX(x);
		widget2.setX(x);
	}
	
	@Override
	public void setY(int y) {
		super.setY(y);
		widget1.setY(y);
		widget2.setY(y);
	}
	
	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		T widget = isState() ? widget1 : widget2;
		widget.render(guiGraphics, mouseX, mouseY, partialTick);
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		T widget = isState() ? widget1 : widget2;
		if (widget.getMessage().getString().isEmpty()) {
			return;
		}
		guiGraphics.renderTooltip(Minecraft.getInstance().font, widget.getMessage(), mouseX, mouseY);
	}
	
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
	}
	
	public boolean isState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
}
