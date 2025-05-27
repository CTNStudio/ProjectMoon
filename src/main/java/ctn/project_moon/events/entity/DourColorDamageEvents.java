package ctn.project_moon.events.entity;

import ctn.project_moon.client.particles.DamageParticle;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.events.DourColorDamageTypesEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.INJURY_TICK;
import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_RECOVERY_TICK;
import static ctn.project_moon.api.SpiritAttribute.*;
import static ctn.project_moon.api.tool.PmDamageTool.*;
import static ctn.project_moon.api.tool.PmDamageTool.FourColorType.*;
import static ctn.project_moon.init.PmEntityAttributes.*;

@EventBusSubscriber(modid = MOD_ID)
public class DourColorDamageEvents {
	@SubscribeEvent
	public static void dourColorDamageTypesEvents(DourColorDamageTypesEvent events) {
		FourColorType fourColorType = null;
		DamageSource source = events.getSource();
		Entity entity = source.getEntity();
		switch (entity) {
			case EvokerFangs ignored -> fourColorType = THE_SOUL;
			case Endermite ignored -> fourColorType = EROSION;
			case WitherSkeleton ignored -> fourColorType = EROSION;
			case MagmaCube ignored -> fourColorType = EROSION;
			case Slime ignored -> fourColorType = EROSION;
			case Warden ignored -> fourColorType = THE_SOUL;
			case EnderMan ignored -> fourColorType = SPIRIT;
			case Shulker ignored -> fourColorType = SPIRIT;
			case Silverfish ignored -> fourColorType = SPIRIT;
			case WitherBoss ignored -> fourColorType = EROSION;
			case null, default -> {
			}
		}
		events.setDourColorDamageTypes(fourColorType);
	}

	/**
	 * 处理伤害效果
	 */
	@SubscribeEvent
	public static void dealingWithDamageEffects(LivingDamageEvent.Pre event) {
		if (!PmConfig.SERVER.ENABLE_FOUR_COLOR_DAMAGE.get()) {
			return;
		}
		LivingEntity entity = event.getEntity();
		ItemStack itemStack = event.getSource().getWeaponItem();
		DamageSource damageSource = event.getSource();
		FourColorType types = FourColorType.getFourColorDamageTypeDefault(damageSource, itemStack);
		switch (types) {
			case PHYSICS -> {
				applySlowdownIfAttributeExceedsOne(PHYSICS_RESISTANCE, entity);
			}
			case SPIRIT -> {
				executeSpiritDamage(event, entity);
				applySlowdownIfAttributeExceedsOne(SPIRIT_RESISTANCE, entity);
			}
			case EROSION -> {
				executeErosionDamage(event, entity);
				applySlowdownIfAttributeExceedsOne(EROSION_RESISTANCE, entity);
			}
			case THE_SOUL -> {
				if (doesTheOrganismSufferFromTheSoulDamage(entity)) {
					break;
				}
				executeTheSoulDamage(event, entity);
				applySlowdownIfAttributeExceedsOne(THE_SOUL_RESISTANCE, entity);
				return;
			}
			case null, default -> {}
		}
		reply(event, entity);
	}

	/** 已应用伤害至实体事件 */
	@SubscribeEvent
	public static void appliedDamageToEntityEvent(LivingDamageEvent.Post event) {
		LivingEntity entity = event.getEntity();
		CompoundTag nbt = entity.getPersistentData();
		if (nbt.contains(INJURY_TICK)) {
			setInjuryCount(entity, 200);
			if (nbt.contains(SPIRIT_RECOVERY_TICK)) {
				setSpiritRecoveryCount(entity, 0);
			}
		}

		var world = entity.level();
		if (world.isClientSide()) {
			return;
		}

		Vec3 pos = entity.position();
		double x = pos.x;
		double y = (pos.y + entity.getBbHeight() * 0.8);
		double z = pos.z;
		Component text = Component.literal(String.valueOf(event.getNewDamage()));
//		((ServerLevel) world).sendParticles(new DamageParticle.Options(text), x, y, z, 1, 0.1, 0.0, 0.1, 0.2);
		((ServerLevel) world).sendParticles(
						new DamageParticle.Options(text),
						x, (y - pos.y) < 1 ? y + 1.3 : y, z,
						1,
						0.3, 0.3, 0.3,
						0.2);

	}
}
