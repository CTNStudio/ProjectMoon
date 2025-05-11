package ctn.project_moon.client.gui.widget.player_attribute;

import ctn.project_moon.client.gui.widget.SwitchButton;
import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.common.inventory.CurioSlot;

import javax.annotation.Nonnull;

public class CurioRenderButton extends SwitchButton {
	private final CurioSlot slot;

	public CurioRenderButton(PlayerAttributeScreen screen, CurioSlot slot,
	                         ResourceLocation resourceLocationIn,
	                         int x, int y,
	                         int width, int height,
	                         int xTexStart, int yTexStart,
	                         OnPress onPress) {
		super(screen, resourceLocationIn, x, y, width, height, xTexStart, yTexStart, onPress, true);
		this.slot = slot;
		message = Component.translatable("gui.curios.toggle");
	}

	@Override
	public int overlayOpen(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		if (!slot.getRenderStatus()) {
			return 2 * (width + 1);
		}
		return 0;
	}
}
