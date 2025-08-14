package ctn.project_moon.client.gui.tool.blit_nine_sliced;

public class SliceSpriteSliceDataBuilder {
	// 使用 ThreadLocal 缓存 builder 实例
	private static final ThreadLocal<SliceSpriteSliceDataBuilder> BUILDER_THREAD_LOCAL =
			ThreadLocal.withInitial(SliceSpriteSliceDataBuilder::new);
	
	private int width;
	private int height;
	private int uOffset;
	private int vOffset;
	private int uWidth;
	private int vHeight;
	
	public SliceSpriteSliceDataBuilder() {
	}
	
	public static SliceSpriteSliceDataBuilder build() {
		SliceSpriteSliceDataBuilder builder = BUILDER_THREAD_LOCAL.get();
		builder.reset(); // 重置状态
		return builder;
	}
	
	// 添加 reset 方法用于重置 builder 状态
	private void reset() {
		this.width = 0;
		this.height = 0;
		this.uOffset = 0;
		this.vOffset = 0;
		this.uWidth = 0;
		this.vHeight = 0;
	}
	
	public SliceSpriteSliceDataBuilder width(int width) {
		this.width = width;
		return this;
	}
	
	public SliceSpriteSliceDataBuilder height(int height) {
		this.height = height;
		return this;
	}
	
	public SliceSpriteSliceDataBuilder uOffset(int uOffset) {
		this.uOffset = uOffset;
		return this;
	}
	
	public SliceSpriteSliceDataBuilder vOffset(int vOffset) {
		this.vOffset = vOffset;
		return this;
	}
	
	public SliceSpriteSliceDataBuilder uWidth(int uWidth) {
		this.uWidth = uWidth;
		return this;
	}
	
	public SliceSpriteSliceDataBuilder vHeight(int vHeight) {
		this.vHeight = vHeight;
		return this;
	}
	
	public SliceSpriteSliceData createSliceSpriteData() {
		return new SliceSpriteSliceData(width, height, uOffset, vOffset, uWidth, vHeight);
	}
}