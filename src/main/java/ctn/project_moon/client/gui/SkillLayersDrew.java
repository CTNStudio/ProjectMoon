package ctn.project_moon.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.init.PmCapabilitys;
import ctn.project_moon.tool.PmTool;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

/**
 * 技能层渲染类
 * 用于在游戏界面中渲染玩家的技能信息，包括技能图标、按键绑定和冷却时间等。
 */
@OnlyIn(Dist.CLIENT)
public class SkillLayersDrew extends LayeredDraw implements LayeredDraw.Layer {
	// 资源路径常量定义
	public static final String           SKILL_SKILLS   = "skill/skills/";
	public static final ResourceLocation KEY_BG         = getPath("skill/key_background");
	public static final ResourceLocation KEY_BG_DISABLE = getPath("skill/key_background_disable");
	public static final ResourceLocation SELECTION_BOX  = getPath("skill/skill_selection_box");
	public static final ResourceLocation BG             = getPath("skill/skill_slot_background");
	
	// 颜色和其他渲染相关常量
	/// 按键背景图标大小
	public static final  int   KEY_BG_SIZE             = 16;
	/// 按键禁用背景图标颜色
	public static final  int   DISABLE_KEY_COLOR       = PmTool.colorConversion("#737373");
	/// 技能图标大小
	public static final  int   SKILL_ICON_SIZE         = 16;
	/// 技能图标灰度
	public static final  float DISABLE_SKILL_ICON_GRAY = 0.3F;
	/// 技能间隔
	public static final  int   SKILL_SPACE             = 20;
	/// 技能背景高度
	public static final  int   BG_HEIGHT               = 26;
	/// 选择框大小
	public static final  int   SELECTION_BOX_SIZE      = 28;
	/// 白色
	private static final int   WHITE_COLOR             = PmTool.colorConversion("#ffffff");
	/// 技能文本偏移
	private static final int   TEXT_X_OFFSET           = 8, TEXT_Y_OFFSET = 4;
	/// 技能文本最大宽度
	private static final int       SKILL_MAX_TEXT_WIDTH = 15;
	// Minecraft实例和字体以及GUI尺寸
	private final        Minecraft minecraft;
	private final        Font      font;
	private final        int       guiWidth;
	private final        int       guiHeight;
	
	/**
	 * 构造函数
	 * 初始化Minecraft实例、字体和GUI的宽度与高度，并调用render方法进行渲染
	 */
	public SkillLayersDrew(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Minecraft minecraft) {
		this.minecraft = minecraft;
		this.font      = minecraft.font;
		this.guiWidth  = guiGraphics.guiWidth();
		this.guiHeight = guiGraphics.guiHeight();
		render(guiGraphics, deltaTracker);
	}
	
	/**
	 * 获取资源路径
	 *
	 * @param path 相对路径
	 * @return 完整资源路径
	 */
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
	
	/**
	 * 获取技能图标路径
	 *
	 * @param originalPath 原始路径
	 * @return 技能图标路径
	 */
	private static @NotNull ResourceLocation getSkillPath(ResourceLocation originalPath) {
		return ResourceLocation.fromNamespaceAndPath(originalPath.getNamespace(), SKILL_SKILLS + originalPath.getPath());
	}
	
	/**
	 * 渲染方法
	 * 主要负责绘制技能栏背景、技能图标、按键绑定和冷却时间文字
	 */
	@Override
	public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
		if (minecraft == null || minecraft.player == null) {
			return;
		}
		final ISkillHandler skillHandler = minecraft.player.getCapability(PmCapabilitys.Skill.SKILL_ENTITY);
		if (skillHandler == null) {
			return;
		}
		final NonNullList<SkillStack> skills = skillHandler.getSkills();
		if (skills == null) {
			return;
		}
        guiGraphics.pose().pushPose();
        // 技能数量
        final int skillCount = skills.size();
        // 技能背景宽度
        int bgWidth = 24 + (skillCount - 1) * SKILL_SPACE;
        // 技能背景X坐标
        int bgX = 0;
        // 技能背景Y坐标
        int bgY = guiHeight - 31;
        int skillX = bgX + 2, skillY = bgY + 5;
        
        /// 技能背景
        guiGraphics.blitSprite(BG, bgX, bgY, bgWidth, BG_HEIGHT);
		
		for (SkillStack stack : skills) {
			renderSkillGroup(stack, guiGraphics, deltaTracker, skillX, skillY);
			skillX += SKILL_SPACE;
		}
        
        /// 选择框
		guiGraphics.blitSprite(SELECTION_BOX, bgX - 4, bgY - 1, SELECTION_BOX_SIZE, SELECTION_BOX_SIZE);
		guiGraphics.pose().popPose();
	}
	
	/**
	 * 渲染技能图标组
	 * 包括技能图标、按键背景和冷却时间文字
	 */
	private void renderSkillGroup(SkillStack stack, @NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker, int x, int y) {
		guiGraphics.pose().pushPose();
		
		// 渲染技能图标
		renderSkill(stack, guiGraphics, deltaTracker, x, y);
		
		/// 渲染按键背景图标和按键文本
		if (stack.getKey() != -1) {
			renderKeyBg(stack, guiGraphics, deltaTracker, x, y);
		}
		
		/// 渲染技能冷却时间文字
		if (stack.isCd()) {
			renderCooldownText(stack, guiGraphics, x, y);
		}
		
		guiGraphics.pose().popPose();
	}
	
	/**
	 * 渲染技能图标
	 * 根据技能是否可用调整图标亮度
	 */
	private void renderSkill(SkillStack stack, GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker, int x, int y) {
        guiGraphics.pose().pushPose();
        ResourceLocation path = getSkillPath(stack.getSkill().getIconPath());
		/// 降低技能图标亮度
		if (!stack.isUse()) {
			guiGraphics.setColor(DISABLE_SKILL_ICON_GRAY, DISABLE_SKILL_ICON_GRAY, DISABLE_SKILL_ICON_GRAY, 1.0F);
		}
        
        /// 渲染技能图标
		guiGraphics.blitSprite(path, x, y, SKILL_ICON_SIZE, SKILL_ICON_SIZE);
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.pose().popPose();
	}
	
	/**
	 * 渲染按键图标背景
	 * 包括按键背景和对应的按键名称
	 */
	private void renderKeyBg(SkillStack skillStack, GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker, int x, int y) {
        guiGraphics.pose().pushPose();
		/// 按键图标背景Y坐标
		int bgY = y - KEY_BG_SIZE - 7;
		/// 文本X坐标
		int textX = x + KEY_BG_SIZE / 2;
		/// 文本Y坐标
		int textY = bgY + 1;
		/// 背景纹理路径
		final ResourceLocation backgroundPath;
		/// 按键名称ID
		int keyCode = skillStack.getKey();
		String keyName = glfwGetKeyName(keyCode, -1);
		/// 按键名称
		final MutableComponent keyText;
		/// 按键名称文字颜色
		final int keyColor;
		
		if (keyName != null) {
			keyText = Component.translatable(keyName.toUpperCase());
		} else {
			final InputConstants.Key key = InputConstants.getKey(keyCode, -1);
			keyText = Component.translatable(key.getDisplayName().getString());
		}
  
		if (skillStack.isUse()) {
			keyColor       = WHITE_COLOR;
			backgroundPath = KEY_BG;
		} else {
			keyColor       = DISABLE_KEY_COLOR;
			textY += 2;
			backgroundPath = KEY_BG_DISABLE;
		}
  
		/// 渲染按键图标背景
		guiGraphics.blitSprite(backgroundPath, x, bgY, KEY_BG_SIZE, KEY_BG_SIZE);
        /// 渲染按键名称
		guiGraphics.drawCenteredString(font, keyText, textX, textY, keyColor);
        guiGraphics.pose().popPose();
	}
	
	/**
	 * 渲染技能冷却时间文字
	 * 根据剩余冷却时间显示相应的数字
	 */
	private void renderCooldownText(SkillStack stack, GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.pose().pushPose();
        int cd = stack.getCd();
		
		// TODO处理时 h、分 m、秒
		MutableComponent text = Component.literal(String.valueOf(cd / 20));
		int fontWidth = font.width(text);
		int fontX = x + TEXT_X_OFFSET;
		int fontY = y + TEXT_Y_OFFSET;
		
		/// 技能冷却时间文字缩放
		if (fontWidth > SKILL_MAX_TEXT_WIDTH) {
			float scale = Math.max(0.5F, 1.0F - ((fontWidth - SKILL_MAX_TEXT_WIDTH) * 0.1F));
			float scaledFontX = fontX + (fontWidth + TEXT_Y_OFFSET * (1.0F - scale));
			float scaledFontY = fontY + (font.lineHeight * (scale));
			
			guiGraphics.pose().translate(scaledFontX, scaledFontY, 0);
			guiGraphics.pose().scale(scale, scale, scale);
			guiGraphics.pose().translate(-scaledFontX, -scaledFontY, 0);
		}
		
        /// 渲染技能冷却时间文字
		guiGraphics.drawCenteredString(font, text, fontX, fontY, SkillLayersDrew.WHITE_COLOR);
        guiGraphics.pose().popPose();
	}
}