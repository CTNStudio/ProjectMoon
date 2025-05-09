package ctn.project_moon.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.common.inventory.CurioSlot;

import javax.annotation.Nonnull;

public class CurioRenderButton extends SwitchButton {
  private final CurioSlot slot;

  public CurioRenderButton(CurioSlot slot,
                           ResourceLocation resourceLocationIn,
                           int x, int y,
                           int width, int height,
                           int xTexStart, int yTexStart,
                           OnPress onPress) {
    super(resourceLocationIn, x, y, width, height, xTexStart, yTexStart, onPress, true);
    this.slot = slot;
  }

  @Override
  public int overlayOpen(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    if (!slot.getRenderStatus()) {
      return 2*(width + 1);
    }
    return 0;
  }
}
