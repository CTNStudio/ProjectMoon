package ctn.project_moon.common.spirit_layered;

import ctn.project_moon.events.SpiritEvents;
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

import javax.annotation.Nullable;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 理智值层渲染
 */
@OnlyIn(Dist.CLIENT)
public class SpiritLayersDraw extends LayeredDraw implements LayeredDraw.Layer {
    private final Minecraft minecraft;

    private static final ResourceLocation DEFAULT_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/default");
    private static final ResourceLocation FULL_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/full");
    private static final ResourceLocation FEW_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/few");

    public SpiritLayersDraw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Minecraft minecraft) {
        this.minecraft = minecraft;
        render(guiGraphics, deltaTracker);
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        renderSpirit(guiGraphics);
    }

    private void renderSpirit(GuiGraphics guiGraphics) {
        if (minecraft.options.hideGui) {
            return;
        }
        Player player = getCameraPlayer();
        if (player == null){
            return;
        }
        int spriteWidth = 20;
        int spriteHeight = 20;
        int height = guiGraphics.guiHeight() - spriteHeight - 35;
        int width = guiGraphics.guiWidth() / 2 - spriteWidth / 2 ;
        ResourceLocation currentTexture;
        float spiritValue = SpiritEvents.getSpiritValue(player);
        float maxSpiritValue = SpiritEvents.getMaxSpiritValue(player);
        float minSpiritValue = SpiritEvents.getMinSpiritValue(player);
        if (spiritValue >= maxSpiritValue * 0.7) {
            currentTexture = FULL_SPIRIT_TEXTURE;
        } else if (spiritValue <= minSpiritValue * 0.7) {
            currentTexture = FEW_SPIRIT_TEXTURE;
        } else {
            currentTexture = DEFAULT_SPIRIT_TEXTURE;
        }
        guiGraphics.blitSprite(currentTexture, width, height, spriteWidth, spriteHeight);

    }

    private Player getCameraPlayer() {
        return this.minecraft.getCameraEntity() instanceof Player player ? player : null;
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
                return (LivingEntity)entity;
            }
        }

        return null;
    }
}
