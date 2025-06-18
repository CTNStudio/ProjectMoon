package ctn.project_moon.client.gui;

import ctn.project_moon.api.attr.RationalityAttribute;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;

import static ctn.project_moon.PmMain.MOD_ID;

/// 理智层渲染
@OnlyIn(Dist.CLIENT)
public class RationalityLayersDraw extends LayeredDraw implements LayeredDraw.Layer {
	private static final ResourceLocation DEFAULT_RATIONALITY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "rationality_hud/default");
	private static final ResourceLocation FULL_RATIONALITY_TEXTURE    = ResourceLocation.fromNamespaceAndPath(MOD_ID, "rationality_hud/full");
	private static final ResourceLocation FEW_RATIONALITY_TEXTURE     = ResourceLocation.fromNamespaceAndPath(MOD_ID, "rationality_hud/few");
	private final        Minecraft        minecraft;
	
	public RationalityLayersDraw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Minecraft minecraft) {
		this.minecraft = minecraft;
		render(guiGraphics, deltaTracker);
	}
	
	@Override
	public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
		guiGraphics.pose().pushPose();
		renderRationality(guiGraphics);
		guiGraphics.pose().popPose();
	}
	
	private void renderRationality(GuiGraphics guiGraphics) {
		if (minecraft.options.hideGui) {
			return;
		}
		Player player = getCameraPlayer();
		if (player == null || player.isCreative() || player.isSpectator()) {
			return;
		}
		int spriteWidth = 20;
		int spriteHeight = 20;
		int height = guiGraphics.guiHeight() - spriteHeight - 35;
		int width = guiGraphics.guiWidth() / 2 - spriteWidth / 2;
		ResourceLocation currentTexture;
		double rationalityValue = RationalityAttribute.getRationalityValue(player);
		double maxRationalityValue = RationalityAttribute.getMaxRationalityValue(player);
		double minRationalityValue = RationalityAttribute.getMinRationalityValue(player);
		
		int textColor;//经验数字颜色
		
		if (rationalityValue >= maxRationalityValue * 0.7) {
			currentTexture = FULL_RATIONALITY_TEXTURE;
			textColor      = 0x4f6175;
		} else if (rationalityValue <= minRationalityValue * 0.7) {
			currentTexture = FEW_RATIONALITY_TEXTURE;
			textColor      = 0x962c2a;
		} else {
			currentTexture = DEFAULT_RATIONALITY_TEXTURE;
			textColor      = 0x878e91;
		}
		guiGraphics.blitSprite(currentTexture, width, height, spriteWidth, spriteHeight);
		renderRationalityValue(guiGraphics, player, height, textColor);
	}
	
	private Player getCameraPlayer() {
		return this.minecraft.getCameraEntity() instanceof Player player ? player : null;
	}
	
	/**
	 * 理智数值显示
	 */
	private void renderRationalityValue(GuiGraphics guiGraphics, Player player, int rationalityHudHeight, int color) {
		double rationalityValue = RationalityAttribute.getRationalityValue(player);
		
		int screenWidth = guiGraphics.guiWidth();
		
		int rationalityValueX = screenWidth / 2;
		int rationalityValueY = rationalityHudHeight + 6;
		
		String text = String.format("%.0f", rationalityValue);
		int x = rationalityValueX - minecraft.font.width(text) / 2;
		guiGraphics.drawString(minecraft.font, text, x, rationalityValueY, color, false);
	}
	
	@CheckForNull
	private LivingEntity getPlayer() {
		Player player = this.getCameraPlayer();
		if (player != null) {
			Entity entity = player.getVehicle();
			if (entity == null) {
				return null;
			}
			
			if (entity instanceof LivingEntity) {
				return (LivingEntity) entity;
			}
		}
		
		return null;
	}
}
