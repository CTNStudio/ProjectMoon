package ctn.project_moon.client.gui;

import ctn.project_moon.api.SpiritAttribute;
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

import javax.annotation.Nullable;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 理智值层渲染
 */
@OnlyIn(Dist.CLIENT)
public class SpiritLayersDraw extends LayeredDraw implements LayeredDraw.Layer {
	private static final ResourceLocation DEFAULT_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/default");
	private static final ResourceLocation FULL_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/full");
	private static final ResourceLocation FEW_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/few");
	private final Minecraft minecraft;

	public SpiritLayersDraw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Minecraft minecraft) {
		this.minecraft = minecraft;
		render(guiGraphics, deltaTracker);
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
		renderSpirit(guiGraphics);
	}

	private void renderSpirit(GuiGraphics guiGraphics) {
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
		double spiritValue = SpiritAttribute.getSpiritValue(player);
		double maxSpiritValue = SpiritAttribute.getMaxSpiritValue(player);
		double minSpiritValue = SpiritAttribute.getMinSpiritValue(player);

		int textColor;//经验数字颜色

		if (spiritValue >= maxSpiritValue * 0.7) {
			currentTexture = FULL_SPIRIT_TEXTURE;
			textColor = 0x4f6175;
		} else if (spiritValue <= minSpiritValue * 0.7) {
			currentTexture = FEW_SPIRIT_TEXTURE;
			textColor = 0x962c2a;
		} else {
			currentTexture = DEFAULT_SPIRIT_TEXTURE;
			textColor = 0x878e91;
		}
		guiGraphics.blitSprite(currentTexture, width, height, spriteWidth, spriteHeight);
		renderSpiritValue(guiGraphics, player, height, textColor);
	}

	private Player getCameraPlayer() {
		return this.minecraft.getCameraEntity() instanceof Player player ? player : null;
	}

	/**
	 * 理智数值显示
	 */
	private void renderSpiritValue(GuiGraphics guiGraphics, Player player, int spiritHudHeight, int color) {
		double spiritValue = SpiritAttribute.getSpiritValue(player);

		int screenWidth = guiGraphics.guiWidth();

		int spiritValueX = screenWidth / 2;
		int spiritValueY = spiritHudHeight + 6;

		String text = String.format("%.0f", spiritValue);
		int x = spiritValueX - minecraft.font.width(text) / 2;


		guiGraphics.drawString(minecraft.font, text, x, spiritValueY, color, false);
	}

	@Nullable
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
