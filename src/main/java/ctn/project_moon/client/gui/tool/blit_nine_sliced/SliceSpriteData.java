package ctn.project_moon.client.gui.tool.blit_nine_sliced;

/// 切片精灵数据
public final class SliceSpriteData {
	
	/// 切片参数
	private final int left, top, right, bottom;
	/// 纹理选区
	private int uWidth, vHeight;
	/// 纹理选区偏移
	private int uOffset, vOffset;
	/// 渲染尺寸
	private int width, height;
	
	public SliceSpriteData(
			int uWidth, int vHeight,
			int uOffset, int vOffset,
			int width, int height,
			int left, int top, int right, int bottom) {
		this.uWidth  = uWidth;
		this.vHeight = vHeight;
		this.width   = width;
		this.height  = height;
		this.uOffset = uOffset;
		this.vOffset = vOffset;
		this.left    = left;
		this.top     = top;
		this.right   = right;
		this.bottom  = bottom;
	}
	
	public SliceSpriteSliceData[][] getSliceData() {
		SliceSpriteSliceData[][] data = new SliceSpriteSliceData[3][3];
		data[0][0] = getUpBuilder(getLeft(), 0, getLeft());
		data[1][0] = getUpBuilder(getCenterUWidth(), getLeft(), getCenterWidth());
		data[2][0] = getUpBuilder(getRight(), getLeft() + getCenterUWidth(), getRight());
		
		data[0][1] = getCentreBuilder(getLeft(), 0, getLeft());
		data[1][1] = getCentreBuilder(getCenterUWidth(), getLeft(), getCenterWidth());
		data[2][1] = getCentreBuilder(getRight(), getLeft() + getCenterUWidth(), getRight());
		
		data[0][2] = getDownBuilder(getLeft(), 0, getLeft());
		data[1][2] = getDownBuilder(getCenterUWidth(), getLeft(), getCenterWidth());
		data[2][2] = getDownBuilder(getRight(), getLeft() + getCenterUWidth(), getRight());
		return data;
	}
	
	private SliceSpriteSliceData getUpBuilder(int uWidth, int uOffset, int width) {
		return getImageDataBuilder()
				.uWidth(uWidth)
				.vHeight(getTop())
				.uOffset(getuOffset() + uOffset)
				.vOffset(getvOffset())
				.width(width)
				.height(getTop())
				.createSliceSpriteData();
	}
	
	private SliceSpriteSliceDataBuilder getImageDataBuilder() {
		return SliceSpriteSliceDataBuilder.build();
	}
	
	private SliceSpriteSliceData getCentreBuilder(int uWidth, int uOffset, int width) {
		return getImageDataBuilder()
				.uWidth(uWidth)
				.vHeight(getCenterUHeight())
				.uOffset(getuOffset() + uOffset)
				.vOffset(getvOffset() + getTop())
				.width(width)
				.height(getCenterHeight())
				.createSliceSpriteData();
	}
	
	private int getCenterHeight() {
		return getHeight() - getTop() - getBottom();
	}
	
	public int getCenterUHeight() {
		return getvHeight() - getTop() - getBottom();
	}
	
	private SliceSpriteSliceData getDownBuilder(int uWidth, int uOffset, int width) {
		return getImageDataBuilder()
				.uWidth(uWidth)
				.vHeight(getBottom())
				.uOffset(getuOffset() + uOffset)
				.vOffset(getvOffset() + getTop() + getCenterUHeight())
				.width(width)
				.height(getBottom())
				.createSliceSpriteData();
	}
	
	private int getCenterWidth() {
		return getWidth() - getLeft() - getRight();
	}
	
	public int getCenterUWidth() {
		return getuWidth() - getLeft() - getRight();
	}
	
	public int getuWidth() {
		return uWidth;
	}
	
	public void setuWidth(int uWidth) {
		this.uWidth = uWidth;
	}
	
	public int getvHeight() {
		return vHeight;
	}
	
	public void setvHeight(int vHeight) {
		this.vHeight = vHeight;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	public int getuOffset() {
		return uOffset;
	}
	
	public void setuOffset(int uOffset) {
		this.uOffset = uOffset;
	}
	
	
	public int getvOffset() {
		return vOffset;
	}
	
	public void setvOffset(int vOffset) {
		this.vOffset = vOffset;
	}
	
	
	public int getLeft() {
		return left;
	}
	
	public int getTop() {
		return top;
	}
	
	public int getRight() {
		return right;
	}
	
	public int getBottom() {
		return bottom;
	}
}