package ctn.project_moon.events.entity;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.config.PmConfig;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.attr.MobGeneralAttribute.INJURY_TICK;
import static ctn.project_moon.api.attr.MobGeneralAttribute.RATIONALITY_RECOVERY_TICK;
import static ctn.project_moon.api.attr.RationalityAttribute.setInjuryCount;
import static ctn.project_moon.api.attr.RationalityAttribute.setRationalityRecoveryCount;
import static ctn.project_moon.api.tool.PmDamageTool.*;
import static ctn.project_moon.client.particles.TextParticle.createDamageParticles;

@EventBusSubscriber(modid = MOD_ID)
public class ColorDamageEvents {
	/** 即将受到伤害但还没处理 */
	@SubscribeEvent
	public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		IModDamageSource modDamageSource = (IModDamageSource) damageSource;
		// 根据四色伤害类型处理抗性
		resistanceTreatment(event, damageSource, modDamageSource.getDamageLevel(), modDamageSource.getFourColorDamageTypes());
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
		IModDamageSource modDamageSource = (IModDamageSource) event.getSource();
		PmDamageTool.ColorType colorType = modDamageSource.getFourColorDamageTypes();
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
			if (nbt.contains(RATIONALITY_RECOVERY_TICK)) {
				setRationalityRecoveryCount(entity, 0);
			}
		}
		
		Component text = Component.literal(String.format("%.2f", event.getNewDamage()));
		// 生成粒子
		createDamageParticles(
				event.getSource().typeHolder().getKey(),
				((IModDamageSource) event.getSource()).getFourColorDamageTypes(),
				entity, text, false);
	}
}
