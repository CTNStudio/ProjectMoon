package ctn.project_moon.mixin;

import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ctn.project_moon.api.TempNbtAttribute.CANNOT_PLAYER_SWITCH_ITEMS;
import static ctn.project_moon.api.TempNbtAttribute.PLAYER_ATTACK;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler, net.neoforged.neoforge.client.extensions.IMinecraftExtension{
    public MinecraftMixin(String name) {
        super(name);
    }

    @Final @Shadow public Options options;
    @Shadow public LocalPlayer player;

    @Inject(method = "handleKeybinds", at = @At("HEAD"), cancellable = true)
    protected void projectMoon$handleKeybinds(CallbackInfo ci){
        if (!player.getPersistentData().getBoolean(CANNOT_PLAYER_SWITCH_ITEMS) || this.player.isSpectator()) {
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (this.options.keyHotbarSlots[i].consumeClick()) {
                ci.cancel();
            }
        }
        while (this.options.keyDrop.consumeClick()) {
            ci.cancel();
        }
        while (this.options.keySwapOffhand.consumeClick()) {
            ci.cancel();
        }
        while (this.options.keyInventory.consumeClick()) {
            ci.cancel();
        }
    }

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void projectMoon$startAttack(CallbackInfoReturnable<Boolean> cir) {
        if (!player.getPersistentData().getBoolean(PLAYER_ATTACK)) {
            return;
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void projectMoon$pickBlock(CallbackInfo ci) {
        if (!player.getPersistentData().getBoolean(CANNOT_PLAYER_SWITCH_ITEMS)) {
            return;
        }
        ci.cancel();
    }
}
