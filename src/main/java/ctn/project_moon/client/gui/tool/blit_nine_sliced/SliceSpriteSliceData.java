package ctn.project_moon.client.gui.tool.blit_nine_sliced;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/// 切片精灵片段数据
public record SliceSpriteSliceData(int width, int height,
                                   int uOffset, int vOffset,
                                   int uWidth, int vHeight) {
	public void blit(ResourceLocation texture, GuiGraphics guiGraphics, int x, int y) {
		this.blit(texture, guiGraphics, x, y, 256, 256);
	}
	
	public void blit(ResourceLocation texture, GuiGraphics guiGraphics, int x, int y, int textureWidth, int textureHeight) {
		guiGraphics.blit(texture, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
	}
}
		