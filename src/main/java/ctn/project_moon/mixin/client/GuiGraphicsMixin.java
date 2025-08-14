package ctn.project_moon.mixin.client;

import ctn.project_moon.mixin_extend.IModGuiGraphics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.IGuiGraphicsExtension;
import org.spongepowered.asm.mixin.*;

@Mixin(GuiGraphics.class)
@Implements(@Interface(iface = IModGuiGraphics.class, prefix = "projectMoonInt$"))
public abstract class GuiGraphicsMixin implements IGuiGraphicsExtension, IModGuiGraphics {
	@Shadow @Final private GuiSpriteManager sprites;
	
	@Shadow protected abstract void blitTiledSprite(TextureAtlasSprite sprite, int x, int y, int blitOffset, int width, int height, int uPosition, int vPosition, int spriteWidth, int spriteHeight, int nineSliceWidth, int nineSliceHeight);
	
	public void projectMoonInt$blitTiledSprite(
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
	) {
		TextureAtlasSprite textureatlassprite = this.sprites.getSprite(sprite);
		blitTiledSprite(textureatlassprite, x, y, blitOffset, width, height, uPosition, vPosition, spriteWidth, spriteHeight, nineSliceWidth, nineSliceHeight);
	}
}
