package ctn.project_moon.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.api.TempNbtAttr.CANNOT_PLAYER_SWITCH_ITEMS;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container, Nameable {
    @Final @Shadow public Player player;

    @Inject(method = "swapPaint", at = @At("HEAD"), cancellable = true)
    protected void swapPaint(double direction, CallbackInfo ci){
        if (player.getPersistentData().getBoolean(CANNOT_PLAYER_SWITCH_ITEMS)) {
            ci.cancel();
        }
    }
}
