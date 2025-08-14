package ctn.project_moon.client.gui.screen.player;

import com.mojang.blaze3d.platform.InputConstants;
import ctn.project_moon.client.gui.hud.SkillLayersDrew;
import ctn.project_moon.common.skill.SkillStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
// TODO

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class SkillBox extends AbstractWidget {
	private final Font               font;
	private final SkillStack         stack;
	private final InputConstants.Key key;
	private final Component          keyText;
	private final ResourceLocation   skillIconPath;
	public        boolean            isDetailed = false;
	
	public SkillBox(SkillStack stack, int x, int y, int width, int height, Font font) {
		super(x, y, width, height, Component.empty());
		this.stack = stack;
		this.font  = font;
		
		// 加载技能图标
		this.skillIconPath = SkillLayersDrew.getSkillIconPath(stack);
		
		// 解析按键信息
		String keyCode = stack.getKeyName();
		if (keyCode != null && !keyCode.trim().isEmpty()) {
			this.key     = InputConstants.getKey(keyCode.trim());
			this.keyText = key.getDisplayName();
		} else {
			this.key     = null;
			this.keyText = Component.translatable("key.keyboard.unknown");
		}
	}
	
	public void addSelf(PlayerSkillScreen screen){
		screen.addWidget(this);
		screen.addWidget(this);
		screen.addWidget(this);
	}
	
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
	
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
	}
}
