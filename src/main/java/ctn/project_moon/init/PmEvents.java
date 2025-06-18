package ctn.project_moon.init;

import ctn.project_moon.event.RationalityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 事件
 */
public class PmEvents {
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static RationalityEvent.Heal rationalityHeal(LivingEntity entity, double amount) {
		return NeoForge.EVENT_BUS.post(new RationalityEvent.Heal(entity, amount));
	}
	
	public static RationalityEvent.Damage rationalityDamage(LivingEntity entity, double amount) {
		return NeoForge.EVENT_BUS.post(new RationalityEvent.Damage(entity, amount));
	}
}
