package ctn.project_moon.common.client.gui_layered;

import ctn.project_moon.events.SpiritEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 理智值（精神值）渲染类
 */
@OnlyIn(Dist.CLIENT)
public class SpiritLayersDraw extends LayeredDraw implements LayeredDraw.Layer {
    private GuiGraphics guiGraphics;
    private DeltaTracker deltaTracker;
    private Minecraft minecraft;

    private static final ResourceLocation DEFAULT_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/default");
    private static final ResourceLocation FULL_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/full");
    private static final ResourceLocation FEW_SPIRIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "spirit_hud/few");

    public SpiritLayersDraw(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        this.guiGraphics = guiGraphics;
        this.deltaTracker = deltaTracker;
        render(guiGraphics,deltaTracker);

    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        renderSpirit(guiGraphics);
    }

    private void renderSpirit(GuiGraphics guiGraphics) {
        minecraft = Minecraft.getInstance();
        // todo : 获取玩家当前精神值，并渲染对应图片() 判断是否按F1
//        if (minecraft.gui.getDebugOverlay().getBandwidthLogger())
        int spriteWidth = 20;
        int spriteHeight = 20;
        int height = guiGraphics.guiHeight() / 2 - spriteHeight / 2;
        int width = (guiGraphics.guiWidth() - spriteWidth) - 35;
        ResourceLocation currentTexture;
        LocalPlayer player = getPlayer();
        float spiritValue = SpiritEvents.getSpiritValue(player);
        if (spiritValue >= SpiritEvents.getMaxSpiritValue(player) * 0.7) {
            currentTexture = FULL_SPIRIT_TEXTURE;
        } else if (spiritValue <= SpiritEvents.getMinSpiritValue(player) * 0.7) {
            currentTexture = FEW_SPIRIT_TEXTURE;
        } else {
            currentTexture = DEFAULT_SPIRIT_TEXTURE;
        }
        guiGraphics.blitSprite(currentTexture, width, height, spriteWidth, spriteHeight);
    }


    private LocalPlayer getPlayer() {
        return this.minecraft.player;
    }

    private Player getCameraPlayer() {
        return this.minecraft.getCameraEntity() instanceof Player player ? player : null;
    }
}
