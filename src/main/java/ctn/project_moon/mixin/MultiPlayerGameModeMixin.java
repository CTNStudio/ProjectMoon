package ctn.project_moon.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ctn.project_moon.api.TempNbtAttr.PLAYER_ATTACK;
import static net.minecraft.world.InteractionResult.FAIL;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    public void projectMoon$interactAt(Player player, Entity target, EntityHitResult ray, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!player.getPersistentData().getBoolean(PLAYER_ATTACK)) {
            return;
        }
        cir.setReturnValue(FAIL);
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    public void projectMoon$useItemOn(LocalPlayer player, InteractionHand hand, BlockHitResult result, CallbackInfoReturnable<InteractionResult> cir) {
        if (!player.getPersistentData().getBoolean(PLAYER_ATTACK)) {
            return;
        }
        cir.setReturnValue(FAIL);
    }
}