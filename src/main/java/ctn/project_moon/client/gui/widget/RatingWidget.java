package ctn.project_moon.client.gui.widget;

import ctn.project_moon.client.screen.PlayerAttributeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static ctn.project_moon.PmMain.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class RatingWidget extends AbstractWidget {
	public static final String COMPOSITE_RATING = MOD_ID + ".gui.player_attribute.composite_rating.message";
	private static final ResourceLocation texture = PlayerAttributeScreen.GUI;
	public final boolean isCompositeRating;
	private int rating = 0;// 请输入0-5

	public RatingWidget(int x, int y, boolean isCompositeRating) {
		super(x, y, 16, 13, Component.empty());
		this.isCompositeRating = isCompositeRating;
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		int uOffset = 199;
		int vOffset = 113 + (height + 1) * rating;
		if (isCompositeRating) {
			uOffset += width + 1;
			if (isHovered()){
				guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable(COMPOSITE_RATING), mouseX, mouseY);
			}
		}
		guiGraphics.blit(texture, getX(), getY(), uOffset, vOffset, width, height);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		if (rating < 0) {
			rating = 0;
		}
		if (rating > 5) {
			rating = 5;
		}
		this.rating = rating;
	}
}
