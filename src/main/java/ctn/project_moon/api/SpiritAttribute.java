package ctn.project_moon.api;

import ctn.project_moon.common.payload.data.SpiritValueData;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.event.SpiritEvent;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Objects;

import static ctn.project_moon.api.MobGeneralAttribute.*;
import static ctn.project_moon.client.particles.DamageParticle.createDamageParticles;
import static ctn.project_moon.client.particles.DamageParticle.createHealParticles;
import static ctn.project_moon.init.PmEntityAttributes.SPIRIT_NATURAL_RECOVERY_RATE;
import static ctn.project_moon.init.PmEntityAttributes.SPIRIT_RECOVERY_AMOUNT;
import static ctn.project_moon.init.PmEvents.spiritDamage;
import static ctn.project_moon.init.PmEvents.spiritHeal;

/**
 * 理智值相关
 */
public class SpiritAttribute {
	/**
	 * 自然恢复理智值
	 */
	public static void refreshSpiritValue(EntityTickEvent.Pre event) {
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
		    !(nbt.contains(SPIRIT_VALUE) && nbt.contains(SPIRIT_RECOVERY_TICK)) ||
		    getSpiritValue(entity) < 0 ||
		    getInjuryCount(entity) != 0 || getSpiritValue(nbt) == getMaxSpiritValue(entity)) {
			return;
		}
		incrementSpiritRecoveryTicks(entity, 1);
		if (getSpiritRecoveryTicks(entity) < (20 / entity.getAttributeValue(SPIRIT_NATURAL_RECOVERY_RATE))) {
			return;
		}
		healSpiritValue(entity, entity.getAttributeValue(SPIRIT_RECOVERY_AMOUNT));
		setSpiritRecoveryCount(entity, 0);
	}

	/** 同步理智值 */
	public static void syncSpiritValue(ServerPlayer player) {
		restrictSpirit(player);
		if (player instanceof ServerPlayer serverPlayer) {
			PacketDistributor.sendToPlayer(serverPlayer, new SpiritValueData(serverPlayer.getPersistentData().getDouble(SPIRIT_VALUE)));
		}
	}

	/** 设置生物当前理智 */
	public static void setSpiritValue(LivingEntity entity, double value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(SPIRIT_VALUE, value);
		restrictSpirit(entity);
	}

	/** 设置生物恢复理智tick,每次成功恢复理智重置 */
	public static void setSpiritRecoveryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(SPIRIT_RECOVERY_TICK, value);
	}

	/** 设置生物受伤tick */
	public static void setInjuryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(INJURY_TICK, value);
	}

	/** 增加生物当前理智 */
	public static void healSpiritValue(LivingEntity entity, double value) {
		SpiritEvent.Heal heal = spiritHeal(entity, value);
		if (heal.isCanceled() || value <= 0) {
			return;
		}
		createHealParticles(entity, Component.literal(String.format("+%.2f", heal.getAmount())), true);
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(SPIRIT_VALUE, getSpiritValue(entity) + heal.getAmount());
		restrictSpirit(entity);
	}

	/// 减少生物当前理智
	public static void damageSpiritValue(LivingEntity entity, double value) {
		SpiritEvent.Damage damage = spiritDamage(entity, value);
		if (damage.isCanceled() || value <= 0) {
			return;
		}
		createDamageParticles(null, null, entity, Component.literal(String.format("-%.2f", damage.getAmount())), true);
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(SPIRIT_VALUE, getSpiritValue(entity) - damage.getAmount());
		restrictSpirit(entity);
	}

	/** 增加生物受伤tick */
	public static void incrementInjuryCount(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(INJURY_TICK, getInjuryCount(entity) + value);
	}

	/** 增加生物恢复理智tick */
	public static void incrementSpiritRecoveryTicks(LivingEntity entity, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(SPIRIT_RECOVERY_TICK, getSpiritRecoveryTicks(entity) + value);
	}

	/** 设置生物最大理智 */
	public static void setMaxSpiritValue(LivingEntity entity, double value) {
		Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.MAX_SPIRIT)).setBaseValue(value);
		restrictSpirit(entity);
	}

	/** 限制生物最大理智 */
	private static void restrictSpirit(LivingEntity entity) {
		double spirit = getSpiritValue(entity);
		double maxSpirit = getMaxSpiritValue(entity);
		double minSpirit = getMinSpiritValue(entity);
		if (spirit > maxSpirit) {
			setSpiritValue(entity, maxSpirit);
		} else if (spirit < minSpirit) {
			setSpiritValue(entity, minSpirit);
		}
	}

	public static double getSpiritValue(LivingEntity entity) {
		return entity.getPersistentData().getDouble(SPIRIT_VALUE);
	}

	public static int getSpiritRecoveryTicks(LivingEntity entity) {
		return entity.getPersistentData().getInt(SPIRIT_RECOVERY_TICK);
	}

	public static int getInjuryCount(LivingEntity entity) {
		return entity.getPersistentData().getInt(INJURY_TICK);
	}

	public static double getMaxSpiritValue(LivingEntity entity) {
		return entity.getAttributeValue(PmEntityAttributes.MAX_SPIRIT);
	}

	public static double getMinSpiritValue(LivingEntity entity) {
		return -(entity.getAttributeValue(PmEntityAttributes.MAX_SPIRIT));
	}

	public static double getSpiritValue(CompoundTag nbt) {
		return nbt.getDouble(SPIRIT_VALUE);
	}

	public static int getSpiritRecoveryTicks(CompoundTag nbt) {
		return nbt.getInt(SPIRIT_RECOVERY_TICK);
	}

	public static int getInjuryCount(CompoundTag nbt) {
		return nbt.getInt(INJURY_TICK);
	}

	/** 如果受伤者有理智，则仅减少理智 */
	public static void executeSpiritDamage(LivingDamageEvent.Pre event, LivingEntity entity) {
		if (isRationality(entity)) return;
		handleRationally(event, entity);
		event.setNewDamage(0);
	}

	/** 判断是否为有理智值 */
	public static boolean isRationality(LivingEntity entity) {
		return !(entity.getPersistentData().contains(SPIRIT_VALUE) && PmConfig.SERVER.ENABLE_SPIRIT_DAMAGE.get() && PmConfig.COMMON.ENABLE_RATIONALITY.get());
	}

	/** 生物遭受精神伤害 */
	public static void handleRationally(LivingDamageEvent.Pre event, LivingEntity entity) {
		if (isRationality(entity)) return;
		damageSpiritValue(entity, event.getNewDamage());
	}
}
