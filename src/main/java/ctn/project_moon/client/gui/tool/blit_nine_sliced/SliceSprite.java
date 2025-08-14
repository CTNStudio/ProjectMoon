package ctn.project_moon.client.gui.tool.blit_nine_sliced;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * 图像切片
 * 原版的精灵渲染貌似问题很大在此编写一个类似的
 *
 * @author 尽
 */
public class SliceSprite {
	private final SliceSpriteData          sliceSpriteData;
	private SliceSpriteSliceData[][] sliceData;
	
	public SliceSprite(
			int uWidth, int vHeight,
			int width, int height,
			int left, int top, int right, int bottom) {
		this(uWidth, vHeight, 0, 0, width, height, left, top, right, bottom);
	}
	
	public SliceSprite(
			int uWidth, int vHeight,
			int uOffset, int vOffset,
			int width, int height,
			int left, int top, int right, int bottom) {
		this(new SliceSpriteData(
				uWidth, vHeight,
				uOffset, vOffset,
				width, height,
				left, top, right, bottom));
	}
	
	public SliceSprite(SliceSpriteData sliceSpriteData) {
		this.sliceSpriteData = sliceSpriteData;
		this.sliceData       = sliceSpriteData.getSliceData();
	}
	
	/**
	 * 使用九宫格切片方式绘制图像
	 */
	public void blitNineSliced(ResourceLocation texture, GuiGraphics guiGraphics, int x, int y) {
		blitNineSliced(texture, guiGraphics, x, y, 256, 256);
	}
	
	/**
	 * 使用九宫格切片方式绘制图像，并支持指定纹理大小
	 */
	public void blitNineSliced(ResourceLocation texture, GuiGraphics guiGraphics, int x, int y, int textureWidth, int textureHeight) {
		int sliceX = x;
		for (SliceSpriteSliceData[] sliceDatum : sliceData) {
			int width = sliceDatum[0].width();
			if (width <= 0) {
				continue;
			}
			int sliceY = y;
			
			for (SliceSpriteSliceData slice : sliceDatum) {
				int height = slice.height();
				if (height <= 0) {
					continue;
				}
				slice.blit(texture, guiGraphics, sliceX, sliceY, textureWidth, textureHeight);
				sliceY += height;
			}
			
			sliceX += width;
		}
	}
	
	public int getuWidth() {
		return sliceSpriteData.getuWidth();
	}
	
	public int getvHeight() {
		return sliceSpriteData.getvHeight();
	}
	
	public int getLeft() {
		return sliceSpriteData.getLeft();
	}
	
	public int getTop() {
		return sliceSpriteData.getTop();
	}
	
	public int getRight() {
		return sliceSpriteData.getRight();
	}
	
	public int getBottom() {
		return sliceSpriteData.getBottom();
	}
	
	public SliceSpriteData getSliceSpriteData() {
		return sliceSpriteData;
	}
	
	public int getWidth() {
		return sliceSpriteData.getWidth();
	}
	
	public void setWidth(int renderingWidth) {
		if (getWidth() == renderingWidth) {
			return;
		}
		sliceSpriteData.setWidth(renderingWidth);
		update();
	}
	
	public int getHeight() {
		return sliceSpriteData.getHeight();
	}
	
	public void setHeight(int renderingHeight) {
		if (getHeight() == renderingHeight) {
			return;
		}
		sliceSpriteData.setHeight(renderingHeight);
		update();
	}
	
	public int getuOffset() {
		return sliceSpriteData.getuOffset();
	}
	
	public void setuOffset(int textureUOffset) {
		if (getuOffset() == textureUOffset) {
			return;
		}
		sliceSpriteData.setuOffset(textureUOffset);
		update();
	}
	
	public int getvOffset() {
		return sliceSpriteData.getvOffset();
	}
	
	public void setvOffset(int textureVOffset) {
		if (getvOffset() == textureVOffset) {
			return;
		}
		sliceSpriteData.setvOffset(textureVOffset);
		update();
	}
	
	public void update() {
		this.sliceData = sliceSpriteData.getSliceData();
	}
}
