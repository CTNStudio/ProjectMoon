package ctn.project_moon.mixin;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.ILevel;
import ctn.project_moon.capability.entity.IColorDamageTypeEntity;
import ctn.project_moon.capability.entity.IInvincibleTickEntity;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.init.PmCapability;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.CheckForNull;

import static ctn.project_moon.api.tool.PmDamageTool.getColorDamageType;
import static ctn.project_moon.api.tool.PmDamageTool.getDamageItemStack;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ENTITY;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ITEM;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ENTITY;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ITEM;

/**
 * @author 尽
 */
@Mixin(DamageSource.class)
@Implements(@Interface(iface = IModDamageSource.class, prefix = "projectMoonInt$"))
public abstract class DamageSourceMixin implements IModDamageSource {
	@Unique
	@CheckForNull
	private PmDamageTool.ColorType projectMoon$ColorType;

	@Unique
	@CheckForNull
	private PmDamageTool.Level projectMoon$damageLevel;

	@Unique
	private int projectMoon$invincibleTick = -1;

	@Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
	                 "Lnet/minecraft/world/entity/Entity;" +
	                 "Lnet/minecraft/world/entity/Entity;" +
	                 "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
	private void projectMoon$DamageSource(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
		DamageSource damageSource = (DamageSource) (Object) this;
		ItemStack itemStack = getDamageItemStack(damageSource);
		ILevel level;
		if (itemStack != null) {
			IColorDamageTypeItem colorDamageTypeItem = itemStack.getCapability(COLOR_DAMAGE_TYPE_ITEM);
			IInvincibleTickItem invincibleTickItem = itemStack.getCapability(INVINCIBLE_TICK_ITEM);
			level = itemStack.getCapability(PmCapability.Level.LEVEL_ITEM);
			if (colorDamageTypeItem != null) {
				projectMoon$ColorType = colorDamageTypeItem.getColorDamageType(itemStack);
			}
			if (invincibleTickItem != null) {
				projectMoon$invincibleTick = invincibleTickItem.getInvincibleTick(itemStack);
			}
			if (level != null) {
				projectMoon$damageLevel = level.getItemLevel();
			}
		}

		projectMoon$getEntityAttribute(directEntity);
		projectMoon$getEntityAttribute(causingEntity);

		projectMoon$ColorType = getColorDamageType(projectMoon$ColorType, type);

		if (projectMoon$damageLevel == null) {
			projectMoon$damageLevel = PmDamageTool.Level.ZAYIN;
		}

		if (projectMoon$invincibleTick == -1) {
			projectMoon$invincibleTick = 20;
		}
	}

	@Unique
	private void projectMoon$getEntityAttribute(Entity entity) {
		IColorDamageTypeEntity colorDamageTypeEntity;
		IInvincibleTickEntity invincibleTickEntity;
		ILevel level;
		if (entity != null) {
			colorDamageTypeEntity = entity.getCapability(COLOR_DAMAGE_TYPE_ENTITY);
			invincibleTickEntity  = entity.getCapability(INVINCIBLE_TICK_ENTITY);
			if (projectMoon$ColorType == null) {
				if (colorDamageTypeEntity != null) {
					projectMoon$ColorType = colorDamageTypeEntity.getDamageType(entity);
				}
			}
			if (projectMoon$invincibleTick != -1) {
				if (invincibleTickEntity != null) {
					projectMoon$invincibleTick = invincibleTickEntity.getInvincibleTick(entity);
				}
			}
			level = entity.getCapability(PmCapability.Level.LEVEL_ENTITY);
			if (level != null) {
				projectMoon$damageLevel = level.getItemLevel();
			}
		}
	}

	@Unique
	@CheckForNull
	public PmDamageTool.ColorType projectMoonInt$getFourColorDamageTypes() {
		return projectMoon$ColorType;
	}

	@Unique
	@CheckForNull
	public PmDamageTool.Level projectMoonInt$getDamageLevel() {
		return projectMoon$damageLevel;
	}

	@Unique
	public int projectMoonInt$getInvincibleTick() {
		return projectMoon$invincibleTick;
	}

	@Unique
	public void projectMoonInt$setFourColorDamageTypes(PmDamageTool.ColorType type) {
		this.projectMoon$ColorType = type;
	}

	@Unique
	public void projectMoonInt$setDamageLevel(PmDamageTool.Level level) {
		this.projectMoon$damageLevel = level;
	}

	@Unique
	public void projectMoonInt$setInvincibleTick(int tick) {
		this.projectMoon$invincibleTick = tick;
	}
}
