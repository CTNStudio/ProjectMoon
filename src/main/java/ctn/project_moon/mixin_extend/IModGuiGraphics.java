package ctn.project_moon.mixin_extend;

import net.minecraft.resources.ResourceLocation;

public interface IModGuiGraphics {
	void blitTiledSprite(
			ResourceLocation sprite,
			int x,
			int y,
			int blitOffset,
			int width,
			int height,
			int uPosition,
			int vPosition,
			int spriteWidth,
			int spriteHeight,
			int nineSliceWidth,
			int nineSliceHeight
	);
}
