package ctn.project_moon.mixin;

import ctn.project_moon.capability.IRandomDamage;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.api.tool.PmDamageTool.getDamageItemStack;
import static ctn.project_moon.init.PmCapability.RANDOM_DAMAGE_ITEM;

@Mixin(DamageContainer.class)
public abstract class DamageContainerMixin {
	@Shadow
	private float newDamage;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void projectMoon$DamageContainer(DamageSource source, float originalDamage, CallbackInfo ci) {
		ItemStack stack = getDamageItemStack(source);
		if (stack == null) {
			return;
		}
		IRandomDamage capability = stack.getCapability(RANDOM_DAMAGE_ITEM);
		if (capability == null) {
			return;
		}
		RandomSource randomSource = null;
		Entity entity = source.getEntity();
		if (entity != null) {
			randomSource = entity.getRandom();
		}
		if (randomSource == null) {
			entity = source.getDirectEntity();
			if (entity != null) {
				randomSource = entity.getRandom();
			}
		}
		if (randomSource == null) {
			randomSource = RandomSource.create();
		}
		newDamage = capability.getDamageValue(randomSource) + (originalDamage - capability.getMaxDamage());
	}
}
