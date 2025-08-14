package ctn.project_moon.client.gui.widget.open_screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class OpenScreenButton extends ImageButton {
	private final AbstractContainerScreen<?> parentGui;
	private final int renderX;
	private final int renderY;
	
	public OpenScreenButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message, AbstractContainerScreen<?> parentGui) {
		super(width, height, sprites, onPress, message);
		renderX = x;
		renderY = y;
		this.parentGui = parentGui;
	}
	
	@Override
	public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY,
			float partialTicks) {
		if (!(parentGui instanceof InventoryScreen ||
		      parentGui instanceof CreativeModeInventoryScreen creativeInventory &&
		      creativeInventory.isInventoryOpen())) {
			this.active = false;
			return;
		}
		this.active = true;
		int x = parentGui.getXSize() - getWidth() - renderX;
		this.setX(parentGui.getGuiLeft() + x);
		this.setY(parentGui.getGuiTop() + renderY);
		super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
		if (isHovered()) {
			guiGraphics.renderTooltip(parentGui.getMinecraft().font, getMessage(), mouseX, mouseY);
		}
	}
}
