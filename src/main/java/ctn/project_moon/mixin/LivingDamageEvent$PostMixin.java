package ctn.project_moon.mixin;

import ctn.project_moon.mixin_extend.IModLivingDamageEvent$Post;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingDamageEvent.Post.class)
@Implements(@Interface(iface = IModLivingDamageEvent$Post.class, prefix = "projectMoonInt$"))
public abstract class LivingDamageEvent$PostMixin implements IModLivingDamageEvent$Post {
	@Unique
	private DamageContainer projectMoon$damageContainer;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	private void projectMoon$Post(LivingEntity entity, DamageContainer container, CallbackInfo ci) {
		projectMoon$damageContainer = container;
	}
	
	@Unique
	public DamageContainer projectMoonInt$getDamageContainer() {
		return projectMoon$damageContainer;
	}
}
