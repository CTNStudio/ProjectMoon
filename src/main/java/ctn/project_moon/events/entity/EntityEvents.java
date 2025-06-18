package ctn.project_moon.events.entity;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.init.PmCapabilitys;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.attr.MobGeneralAttribute.addRationalityAttribute;
import static ctn.project_moon.api.attr.RationalityAttribute.refreshRationalityValue;
import static ctn.project_moon.client.particles.TextParticle.createHealParticles;
import static ctn.project_moon.init.PmEntityAttributes.MAX_RATIONALITY;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class EntityEvents {
	/**
	 * 实体死亡事件
	 */
	@SubscribeEvent
	public static void deathEvent(LivingDeathEvent event) {
	}
	
	@SubscribeEvent
	public static void addSpirtAttyibute(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof LivingEntity entity && entity.getAttributes().hasAttribute(MAX_RATIONALITY)) {
			addRationalityAttribute(entity);
		}
	}
	
	@SubscribeEvent
	public static void entityTickEvent(EntityTickEvent.Pre event) {
		refreshRationalityValue(event);
		Entity entity = event.getEntity();
		ISkillHandler handler = entity.getCapability(PmCapabilitys.Skill.SKILL_ENTITY);
		// 技能冷却
		if (!entity.level().isClientSide() && handler != null) {
			handler.tick(entity.level(), entity);
		}
	}
	
	@SubscribeEvent
	public static void entityHealEvent(LivingHealEvent event) {
		float amount = event.getAmount();
		LivingEntity entity = event.getEntity();
		if (amount > 0) {
			createHealParticles(entity, Component.literal(String.format("+%.2f", amount)), false);
		}
	}
}
