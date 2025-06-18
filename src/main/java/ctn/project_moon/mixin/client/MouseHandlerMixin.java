package ctn.project_moon.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.api.attr.TempNbtAttribute.CANNOT_PLAYER_ROTATING_PERSPECTIVE;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;
	
	@Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
	private void projectMoon$turnPlayer(CallbackInfo ci) {
		if (!(minecraft.player != null && minecraft.player.getPersistentData().getBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE))) {
			return;
		}
		ci.cancel();
	}
}
