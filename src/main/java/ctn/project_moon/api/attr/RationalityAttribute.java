package ctn.project_moon.api.attr;

import ctn.project_moon.config.PmConfig;
import ctn.project_moon.event.RationalityEvent;
import ctn.project_moon.init.PmEntityAttributes;
import ctn.project_moon.init.PmPayloadInit;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;

import static ctn.project_moon.api.attr.MobGeneralAttribute.*;
import static ctn.project_moon.client.particles.TextParticle.createDamageParticles;
import static ctn.project_moon.client.particles.TextParticle.createHealParticles;
import static ctn.project_moon.init.PmEntityAttributes.RATIONALITY_NATURAL_RECOVERY_RATE;
import static ctn.project_moon.init.PmEntityAttributes.RATIONALITY_RECOVERY_AMOUNT;
import static ctn.project_moon.init.PmEvents.rationalityDamage;
import static ctn.project_moon.init.PmEvents.rationalityHeal;

/**
 * 理智值相关
 */
public class RationalityAttribute {
	/**
	 * 自然恢复理智值
	 */
	public static void refreshRationalityValue(EntityTickEvent.Pre event) {
		if (!(event.getEntity() instanceof LivingEntity entity)) {
			return;
		}
		if (entity instanceof AbstractClientPlayer) {
			return;
		}
		CompoundTag nbt = entity.getPersistentData();
		if (!nbt.contains(INJURY_TICK)) {
			return;
		}
		if (getInjuryCount(entity) != 0) {
			incrementInjuryCount(entity, -1);
		}
		if (!PmConfig.SERVER.ENABLE_LOW_RATIONALITY_NEGATIVE_EFFECT.get() ||
		    !(nbt.contains(RATIONALITY_VALUE) && nbt.contains(RATIONALITY_RECOVERY_TICK)) ||
		    getRationalityValue(entity) < 0 ||
		    getInjuryCount(entity) != 0 || getRationalityValue(nbt) == getMaxRationalityValue(entity)) {
			return;
		}
		incrementRationalityRecoveryTicks(entity, 1);
		if (getRationalityRecoveryTicks(entity) < (20 / entity.getAttributeValue(RATIONALITY_NATURAL_RECOVERY_RATE))) {
			return;
		}
		healRationalityValue(entity, entity.getAttributeValue(RATIONALITY_RECOVERY_AMOUNT));
		setRationalityRecoveryCount(entity, 0);
	}
	
	/** 同步理智值 */
	public static void syncRationalityValue(ServerPlayer player) {
		restrictRationality(player);
		if (player instanceof ServerPlayer serverPlayer) {
			PmPayloadInit.syncRationality(serverPlayer);
		}
	}
	
	
	/** 设置生物当前理智 */
	public static void setRationalityValue(LivingEntity entity, double value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(RATIONALITY_VALUE, value);
		restrictRationality(entity);
	}
	
	/** 设置生物恢复理智tick,每次成功恢复理智重置 */
	public static void setRationalityRecoveryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(RATIONALITY_RECOVERY_TICK, value);
	}
	
	/** 设置生物受伤tick */
	public static void setInjuryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(INJURY_TICK, value);
	}
	
	/** 增加生物当前理智 */
	public static void healRationalityValue(LivingEntity entity, double value) {
		RationalityEvent.Heal heal = rationalityHeal(entity, value);
		if (heal.isCanceled() || value <= 0) {
			return;
		}
		createHealParticles(entity, Component.literal(String.format("+%.2f", heal.getAmount())), true);
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(RATIONALITY_VALUE, getRationalityValue(entity) + heal.getAmount());
		restrictRationality(entity);
	}
	
	/// 减少生物当前理智
	public static void damageRationalityValue(LivingEntity entity, double value) {
		RationalityEvent.Damage damage = rationalityDamage(entity, value);
		if (damage.isCanceled() || value <= 0) {
			return;
		}
		createDamageParticles(null, null, entity, Component.literal(String.format("-%.2f", damage.getAmount())), true);
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(RATIONALITY_VALUE, getRationalityValue(entity) - damage.getAmount());
		restrictRationality(entity);
	}
	
	/** 增加生物受伤tick */
	public static void incrementInjuryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(INJURY_TICK, getInjuryCount(entity) + value);
	}
	
	/** 增加生物恢复理智tick */
	public static void incrementRationalityRecoveryTicks(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(RATIONALITY_RECOVERY_TICK, getRationalityRecoveryTicks(entity) + value);
	}
	
	/** 设置生物最大理智 */
	public static void setMaxRationalityValue(LivingEntity entity, double value) {
		Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.MAX_RATIONALITY)).setBaseValue(value);
		restrictRationality(entity);
	}
	
	/** 限制生物最大理智 */
	private static void restrictRationality(LivingEntity entity) {
		double rationality = getRationalityValue(entity);
		double maxRationality = getMaxRationalityValue(entity);
		double minRationality = getMinRationalityValue(entity);
		if (rationality > maxRationality) {
			setRationalityValue(entity, maxRationality);
		} else if (rationality < minRationality) {
			setRationalityValue(entity, minRationality);
		}
	}
	
	public static double getRationalityValue(LivingEntity entity) {
		return entity.getPersistentData().getDouble(RATIONALITY_VALUE);
	}
	
	public static int getRationalityRecoveryTicks(LivingEntity entity) {
		return entity.getPersistentData().getInt(RATIONALITY_RECOVERY_TICK);
	}
	
	public static int getInjuryCount(LivingEntity entity) {
		return entity.getPersistentData().getInt(INJURY_TICK);
	}
	
	public static double getMaxRationalityValue(LivingEntity entity) {
		return entity.getAttributeValue(PmEntityAttributes.MAX_RATIONALITY);
	}
	
	public static double getMinRationalityValue(LivingEntity entity) {
		return -(entity.getAttributeValue(PmEntityAttributes.MAX_RATIONALITY));
	}
	
	public static double getRationalityValue(CompoundTag nbt) {
		return nbt.getDouble(RATIONALITY_VALUE);
	}
	
	public static int getRationalityRecoveryTicks(CompoundTag nbt) {
		return nbt.getInt(RATIONALITY_RECOVERY_TICK);
	}
	
	public static int getInjuryCount(CompoundTag nbt) {
		return nbt.getInt(INJURY_TICK);
	}
	
	/** 如果受伤者有理智，则仅减少理智 */
	public static void executeRationalityDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
		if (isRationality(entity)) return;
		handleSpirit(event, entity);
		event.setNewDamage(0);
	}
	
	/** 判断是否为有理智值 */
	public static boolean isRationality(LivingEntity entity) {
		return !(entity.getPersistentData().contains(RATIONALITY_VALUE) && PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() && PmConfig.COMMON.ENABLE_RATIONALITY.get());
	}
	
	/** 生物遭受精神伤害 */
	public static void handleSpirit(LivingDamageEvent.Pre event, LivingEntity entity) {
		if (isRationality(entity)) return;
		damageRationalityValue(entity, event.getNewDamage());
	}
}
