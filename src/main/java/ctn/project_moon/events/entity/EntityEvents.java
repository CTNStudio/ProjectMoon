package ctn.project_moon.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.MobGeneralAttribute.addSpiritAttribute;
import static ctn.project_moon.api.SpiritAttribute.refreshSpiritValue;
import static ctn.project_moon.init.PmEntityAttributes.MAX_SPIRIT;

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
		if (event.getEntity() instanceof LivingEntity entity && entity.getAttributes().hasAttribute(MAX_SPIRIT)) {
			addSpiritAttribute(entity);
		}
	}

	@SubscribeEvent
	public static void entityTickEvent(EntityTickEvent.Pre event) {
		refreshSpiritValue(event);
	}
}
