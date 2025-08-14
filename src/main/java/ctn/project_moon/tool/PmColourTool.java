package ctn.project_moon.tool;

import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.regex.Pattern;

public enum PmColourTool {
	PHYSICS("#ff0000", "physics"),
	SPIRIT("#ffffff", "spirit"),
	EROSION("#8a2be2", "erosion"),
	THE_SOUL("#00ffff", "the_soul"),
	ZAYIN("#00ff00", "ZAYIN"),
	TETH("#1e90ff", "TETH"),
	HE("#ffff00", "HE"),
	WAW("#8a2be2", "WAW"),
	ALEPH("#ff0000", "ALEPH");
	
	public static final  String  RGBA_WHITE      = "#ffffffff";
	public static final  String  RGB_WHITE       = "#ffffff";
	public static final  int     RGB_WHITE_VALUE = 0xFFFFFF;
	private static final Pattern COLOR_6_PATTERN = Pattern.compile("^#[a-fA-F0-9]{6}$");
	private static final Pattern COLOR_8_PATTERN = Pattern.compile("^#[a-fA-F0-9]{8}$");
	private static final Pattern COLOR_PATTERN   = Pattern.compile("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{8})$");
	private static final int     MAX_COLOR_VALUE = 255;
	private static final int     MIN_COLOR_VALUE = 0;
	private static final int     ALPHA_OPAQUE    = 0xFF;
	private static final int     DEFAULT_COLOR   = 0xFFFFFFFF;
	private final        String  colour;
	private final        String  colourText;
	
	PmColourTool(String colour, String colourText) {
		this.colour     = colour;
		this.colourText = colourText;
	}
	
	/**
	 * 创建指定透明度的颜色值
	 *
	 * @param color 颜色字符串，格式为 #RRGGBB
	 * @param alpha 透明度值 (0-255)，0为完全透明，255为完全不透明
	 * @return ARGB颜色值
	 */
	public static int colorWithAlpha(String color, int alpha) {
		alpha = clamp(alpha, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		
		return (alpha << 24) | (rgbColor(color) & RGB_WHITE_VALUE);
	}
	
	/**
	 * 将值限制在指定范围内
	 *
	 * @param value 要限制的值
	 * @param min   最小值
	 * @param max   最大值
	 * @return 限制后的值
	 */
	private static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}
	
	/**
	 * 创建指定RGB值的颜色并返回16位数字格式（255,255,255格式）
	 *
	 * @param r 红色值 (0-255)
	 * @param g 绿色值 (0-255)
	 * @param b 蓝色值 (0-255)
	 * @return 16位数字格式的颜色值
	 */
	public static int rgbTo16Bit(int r, int g, int b) {
		return rgbaTo16Bit(r, g, b, MAX_COLOR_VALUE);
	}
	
	/**
	 * 创建指定RGBA值的颜色并返回16位数字格式（255,255,255,255格式）
	 *
	 * @param r 红色值 (0-255)
	 * @param g 绿色值 (0-255)
	 * @param b 蓝色值 (0-255)
	 * @param a 透明度值 (0-255)
	 * @return 16位数字格式的颜色值
	 */
	public static int rgbaTo16Bit(int r, int g, int b, int a) {
		// 限制输入值在有效范围内
		r = clamp(r, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		g = clamp(g, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		b = clamp(b, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		a = clamp(a, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		
		// 构建32位ARGB值
		int argb = (a << 24) | (r << 16) | (g << 8) | b;
		
		// 转换为16位格式 (ARGB 1555)
		int a1 = (argb >> 31) & 0x1;  // Alpha: 1位
		int r5 = (argb >> 19) & 0x1F; // Red: 5位
		int g5 = (argb >> 11) & 0x1F; // Green: 5位
		int b5 = (argb >> 3) & 0x1F;  // Blue: 5位
		
		return (a1 << 15) | (r5 << 10) | (g5 << 5) | b5;
	}
	
	/**
	 * 创建指定RGB值的颜色并返回16位数字格式（1,1,1格式）
	 *
	 * @param r 红色值 (0-1.0)
	 * @param g 绿色值 (0-1.0)
	 * @param b 蓝色值 (0-1.0)
	 * @return 16位数字格式的颜色值
	 */
	public static int rgbTo16Bit(float r, float g, float b) {
		return rgbaTo16Bit(r, g, b, 1.0f);
	}
	
	/**
	 * 创建指定RGBA值的颜色并返回16位数字格式（1,1,1,1格式）
	 *
	 * @param r 红色值 (0-1.0)
	 * @param g 绿色值 (0-1.0)
	 * @param b 蓝色值 (0-1.0)
	 * @param a 透明度值 (0-1.0)
	 * @return 16位数字格式的颜色值
	 */
	public static int rgbaTo16Bit(float r, float g, float b, float a) {
		return rgbaTo16Bit(
				(int) (r * MAX_COLOR_VALUE),
				(int) (g * MAX_COLOR_VALUE),
				(int) (b * MAX_COLOR_VALUE),
				(int) (a * MAX_COLOR_VALUE)
		);
	}
	
	/**
	 * 创建指定RGB值的颜色并返回RGB十六进制格式（如0x000000）
	 *
	 * @param r 红色值 (0-1.0)
	 * @param g 绿色值 (0-1.0)
	 * @param b 蓝色值 (0-1.0)
	 * @return RGB十六进制格式的颜色值
	 */
	public static int rgbToHex(float r, float g, float b) {
		return rgbToHex(
				(int) (r * MAX_COLOR_VALUE),
				(int) (g * MAX_COLOR_VALUE),
				(int) (b * MAX_COLOR_VALUE)
		);
	}
	
	/**
	 * 创建指定RGB值的颜色并返回RGB十六进制格式（如0x000000）
	 *
	 * @param r 红色值 (0-255)
	 * @param g 绿色值 (0-255)
	 * @param b 蓝色值 (0-255)
	 * @return RGB十六进制格式的颜色值
	 */
	public static int rgbToHex(int r, int g, int b) {
		// 限制输入值在有效范围内
		r = clamp(r, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		g = clamp(g, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		b = clamp(b, MIN_COLOR_VALUE, MAX_COLOR_VALUE);
		
		// 返回RGB十六进制格式
		return (r << 16) | (g << 8) | b;
	}
	
	/**
	 * 使用Java Color对象创建16位数字格式的颜色值
	 *
	 * @param color Java Color对象
	 * @return 16位数字格式的颜色值
	 */
	public static int colorTo16Bit(Color color) {
		return rgbaTo16Bit(
				clamp(color.getRed(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getGreen(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getBlue(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getAlpha(), MIN_COLOR_VALUE, MAX_COLOR_VALUE)
		);
	}
	
	/**
	 * 使用Java Color对象创建RGB十六进制格式的颜色值（如0x000000）
	 *
	 * @param color Java Color对象
	 * @return RGB十六进制格式的颜色值
	 */
	public static int colorToHex(Color color) {
		return rgbToHex(
				clamp(color.getRed(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getGreen(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getBlue(), MIN_COLOR_VALUE, MAX_COLOR_VALUE)
		);
	}
	
	/**
	 * 使用Java Color对象创建RGB十六进制格式的字符串（如"#000000"）
	 *
	 * @param color Java Color对象
	 * @return RGB十六进制格式的字符串
	 */
	public static String colorToHexString(Color color) {
		return String.format("#%02x%02x%02x",
				clamp(color.getRed(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getGreen(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getBlue(), MIN_COLOR_VALUE, MAX_COLOR_VALUE)
		);
	}
	
	/**
	 * 使用Java Color对象创建ARGB十六进制格式的字符串（如"#FF000000"）
	 *
	 * @param color Java Color对象
	 * @return ARGB十六进制格式的字符串
	 */
	public static String colorToArgbHexString(Color color) {
		return String.format("#%02x%02x%02x%02x",
				clamp(color.getAlpha(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getRed(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getGreen(), MIN_COLOR_VALUE, MAX_COLOR_VALUE),
				clamp(color.getBlue(), MIN_COLOR_VALUE, MAX_COLOR_VALUE)
		);
	}
	
	public int getColourRGB() {
		return colorConversion(getColour());
	}
	
	/**
	 * 将颜色字符串转换为带透明度的ARGB值
	 *
	 * @param color 颜色字符串，格式为 #RRGGBB 或 #AARRGGBB
	 * @return ARGB颜色值
	 */
	public static int colorConversion(String color) {
		if (color == null || !COLOR_PATTERN.matcher(color).matches()) {
			color = RGBA_WHITE; // 默认为不透明的白色
		}
		
		try {
			if (color.length() == 7) { // #RRGGBB 格式
				return (ALPHA_OPAQUE << 24) | rgbColor(color); // 添加不透明度为255
			} else { // #AARRGGBB 格式
				String alpha = color.substring(1, 3);
				String rgb = color.substring(3);
				int alphaValue = Integer.parseInt(alpha, 16);
				int rgbValue = Integer.parseInt(rgb, 16);
				return (alphaValue << 24) | rgbValue;
			}
		} catch (NumberFormatException e) {
			// 如果解析失败，返回默认颜色
			return DEFAULT_COLOR;
		}
	}
	
	public String getColour() {
		return colour;
	}
	
	public static int rgbColor(String color) {
		return TextColor.parseColor(handleColor(color)).getOrThrow().getValue();
	}
	
	private static @NotNull String handleColor(String color) {
		if (color == null || !COLOR_6_PATTERN.matcher(color).matches()) {
			color = RGB_WHITE;
		}
		return color;
	}
	
	public String getColourText() {
		return colourText;
	}
}
