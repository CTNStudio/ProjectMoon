package ctn.project_moon.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private net.neoforged.neoforge.client.gui.GuiLayerManager layerManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void gui(Minecraft minecraft, CallbackInfo ci) {
    }

    @Unique
    private LocalPlayer getPlayer() {
        return this.minecraft.player;
    }

    @Shadow protected abstract Player getCameraPlayer();

    @Shadow public abstract void setNowPlaying(Component displayName);

    @Shadow public abstract BossHealthOverlay getBossOverlay();
}



