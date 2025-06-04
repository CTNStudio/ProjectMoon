package ctn.project_moon.mixin;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.events.DourColorDamageTypesEvent;
import ctn.project_moon.mixin_extend.PmDamageSourceMixin;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.api.tool.PmDamageTool.FourColorType.getType;
import static ctn.project_moon.api.tool.PmDamageTool.getDamageItemStack;
import static ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon.isCloseCombatEgo;
import static ctn.project_moon.init.PmCommonHooks.dourColorDamageType;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;

/**
 * @author 尽
 */
@Mixin(DamageSource.class)
@Implements(@Interface(iface = PmDamageSourceMixin.class, prefix = "projectMoonInt$"))
public abstract class DamageSourceMixin implements PmDamageSourceMixin {
	@Unique
	private PmDamageTool.FourColorType projectMoon$fourColorType;

	@Unique
	private PmDamageTool.Level projectMoon$damageLevel;

	@Shadow @Final
	private Holder<DamageType> type;

	@Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
	                 "Lnet/minecraft/world/entity/Entity;" +
	                 "Lnet/minecraft/world/entity/Entity;" +
	                 "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
	private void DamageSource(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
		var damageSource = (DamageSource) (Object) this;
		var itemStack = getDamageItemStack(damageSource);
		if (isCloseCombatEgo(itemStack)) {
			projectMoon$fourColorType = getType(itemStack.get(CURRENT_DAMAGE_TYPE));
		} else {
			DourColorDamageTypesEvent dourColorEvents = dourColorDamageType(damageSource);
			if (dourColorEvents.getDamageTypes() != null) {
				projectMoon$fourColorType = dourColorEvents.getDamageTypes();
			} else {
				for (PmDamageTool.FourColorType types : PmDamageTool.FourColorType.values()) {
					TagKey<DamageType> damageTypeTagKey =
							type.tags().filter(tag -> types.getDamageTypeTag().equals(tag))
									.findFirst()
									.orElse(null);
					if (damageTypeTagKey != null) {
						projectMoon$fourColorType = types;
						break;
					}
				}
			}
		}
		projectMoon$damageLevel = PmDamageTool.Level.getDamageLevel(damageSource);
	}

	@Unique
	public PmDamageTool.FourColorType projectMoonInt$getFourColorDamageTypes() {
		return projectMoon$fourColorType;
	}

	@Unique
	public PmDamageTool.Level projectMoonInt$getDamageLevel() {
		return projectMoon$damageLevel;
	}
}
