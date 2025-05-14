package ctn.project_moon.client.gui.widget.player_attribute;

import ctn.project_moon.client.gui.widget.StateWidget;
import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 属性评级控件
 * @author 小尽
 */
@OnlyIn(Dist.CLIENT)
public final class RatingWidget extends StateWidget {
	public static final String COMPOSITE_RATING = MOD_ID + ".gui.player_attribute.composite_rating.message";
	private static final ResourceLocation texture = PlayerAttributeScreen.GUI;
	public RatingWidget(int x, int y, boolean isCompositeRating) {
		super(x, y, 16, 13, 199, 113, PlayerAttributeScreen.GUI);
		if (isCompositeRating){
			setStateU(1);
		}
	}

	public RatingWidget(int x, int y) {
		this(x, y, false);
	}

	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (stateU == 1) {
			guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable(COMPOSITE_RATING), mouseX, mouseY);
		}
	}

	@Override
	public void setStateU(int stateU) {
		if (stateU < 0) {
			stateU = 0;
		}
		if (stateU > 1) {
			stateU = 1;
		}
		super.setStateU(stateU);
	}

	@Override
	public void setStateV(int stateV) {
		if (stateV < 0) {
			stateV = 0;
		}
		if (stateV > 5) {
			stateV = 5;
		}
		super.setStateV(stateV);
	}
}
