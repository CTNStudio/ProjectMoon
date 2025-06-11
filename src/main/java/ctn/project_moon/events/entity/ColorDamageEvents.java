package ctn.project_moon.events.entity;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.client.particles.DamageParticle;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.INJURY_TICK;
import static ctn.project_moon.api.MobGeneralAttribute.SPIRIT_RECOVERY_TICK;
import static ctn.project_moon.api.SpiritAttribute.setInjuryCount;
import static ctn.project_moon.api.SpiritAttribute.setSpiritRecoveryCount;
import static ctn.project_moon.api.tool.PmDamageTool.*;

@EventBusSubscriber(modid = MOD_ID)
public class ColorDamageEvents {
	/** 即将受到伤害但还没处理 */
	@SubscribeEvent
	public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		if (!(damageSource instanceof IModDamageSource iModDamageSource)){
			return;
		}
		int invincibleTick = iModDamageSource.getInvincibleTick();
		event.setInvulnerabilityTicks(invincibleTick);
		// 根据四色伤害类型处理抗性
		resistanceTreatment(event, damageSource, iModDamageSource.getDamageLevel(), iModDamageSource.getFourColorDamageTypes());
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
		DamageSource damageSource = event.getSource();
		PmDamageTool.ColorType colorType = null;
		if (damageSource instanceof IModDamageSource modDamageSource){
			colorType = modDamageSource.getFourColorDamageTypes();
		}
		applySlowdownIfAttributeExceedsOne(colorType, entity);
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

		Level world = entity.level();
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
