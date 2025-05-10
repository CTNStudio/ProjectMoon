package ctn.project_moon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.api.TempNbtAttribute.CANNOT_PLAYER_MOVED;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
	@Final
	@Shadow
	private Options options;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void projectMoon$tick(boolean isSneaking, float sneakingSpeedMultiplier, CallbackInfo ci) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player != null && minecraft.player.getPersistentData().getBoolean(CANNOT_PLAYER_MOVED)) {
			this.up = false;
			this.down = false;
			this.left = false;
			this.right = false;
			this.jumping = false;
			this.shiftKeyDown = false;
			this.forwardImpulse = 0;
			this.leftImpulse = 0;
			ci.cancel();
		}
	}
}
