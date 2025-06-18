package ctn.project_moon.client.gui;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.init.PmCapabilitys;
import ctn.project_moon.tool.PmTool;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/// 技能层渲染
///
/// @author 尽
@OnlyIn(Dist.CLIENT)
public class SkillLayersDrew extends LayeredDraw implements LayeredDraw.Layer {
	private static final int           SKILL_RENDER_OFFSET = 2;
	private static final int           TEXT_CENTER_X       = 8;
	private static final int           TEXT_Y_OFFSET       = 4;
	private static final int           MAX_TEXT_WIDTH      = 15;
	private static final float         MIN_SCALE           = 0.5F;
	private static final float         SCALE_FACTOR        = 0.1F;
	private static final int           WHITE_COLOR         = PmTool.colorConversion("#ffffff");
	private final        Minecraft     minecraft;
	private              ISkillHandler skillHandler;
	
	public SkillLayersDrew(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Minecraft minecraft) {
		this.minecraft = minecraft;
		
		render(guiGraphics, deltaTracker);
	}
	
	@Override
	public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
		if (minecraft == null || minecraft.player == null) {
			return;
		}
		
		skillHandler = minecraft.player.getCapability(PmCapabilitys.Skill.SKILL_ENTITY);
		if (skillHandler == null) {
			return;
		}
		
		int guiHeight = guiGraphics.guiHeight();
		int guiWidth = guiGraphics.guiWidth();
		int y = guiHeight - 20;
		int x = guiWidth - 20;
		final int width = 20;
		final int height = 20;
		final int spacing = width + 2;
		
		Iterable<SkillStack> skills = skillHandler.getSkills();
		if (skills == null) {
			return;
		}
		
		for (SkillStack stack : skills) {
			renderSkillGroup(guiGraphics, deltaTracker, stack, x, y);
			x -= spacing;
		}
	}
	
	
	private void renderSkillGroup(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker, SkillStack stack, int x, int y) {
		x -= SKILL_RENDER_OFFSET;
		y -= SKILL_RENDER_OFFSET;
		
		guiGraphics.pose().pushPose();
		int cd = stack.getCd();
		
		// 渲染技能图标
		guiGraphics.pose().pushPose();
		renderSkill(stack, guiGraphics, deltaTracker, x, y, cd > 0);
		guiGraphics.pose().popPose();
		
		/// 渲染技能冷却时间
		if (cd > 0) {
			// TODO处理时 h、分 m、秒
			renderCooldownText(guiGraphics, x, y, cd, WHITE_COLOR);
		}
		
		guiGraphics.pose().popPose();
	}
	
	private void renderCooldownText(GuiGraphics guiGraphics, int x, int y, int cd, int color) {
		Font font = minecraft.font;
		
		String literal = Integer.toString(cd / 20);
		int fontWidth = font.width(literal);
		int fontX = x + TEXT_CENTER_X - fontWidth / 2;
		int fontY = y + TEXT_Y_OFFSET;
		
		guiGraphics.pose().pushPose();
//	    guiGraphics.pose().translate(fontX, fontY, 0);
		
		if (fontWidth > MAX_TEXT_WIDTH) {
			float scale = Math.max(MIN_SCALE, 1.0F - ((fontWidth - MAX_TEXT_WIDTH) * SCALE_FACTOR));
			float scaledFontX = fontX + (fontWidth + TEXT_Y_OFFSET * (1.0F - scale)) / 2;
			float scaledFontY = fontY + (font.lineHeight * (scale)) / 2;
			
			guiGraphics.pose().translate(scaledFontX, scaledFontY, 0);
			guiGraphics.pose().scale(scale, scale, scale);
			guiGraphics.pose().translate(-scaledFontX, -scaledFontY, 0);
		}
		
		guiGraphics.drawString(font, literal, fontX, fontY, color);
		guiGraphics.pose().popPose();
	}
	
	
	private void renderSkill(SkillStack stack, GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker, int x, int y, boolean isCd) {
		ResourceLocation originalPath = stack.getSkill().getIconPath();
		ResourceLocation path = ResourceLocation.fromNamespaceAndPath(originalPath.getNamespace(), "textures/skill/" + originalPath.getPath() + ".png");
		if (isCd) {
			final float gray = 0.3F;
			guiGraphics.setColor(gray, gray, gray, 1.0F);
		}
		guiGraphics.blit(path, x, y, 0, 0, 16, 16, 16, 16);
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
