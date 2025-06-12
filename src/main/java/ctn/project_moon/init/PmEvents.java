package ctn.project_moon.init;

import ctn.project_moon.event.SpiritEvent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 事件
 */
public class PmEvents {
	private static final Logger LOGGER = LogManager.getLogger();

	public static SpiritEvent.Heal spiritHeal(LivingEntity entity, double amount) {
		return NeoForge.EVENT_BUS.post(new SpiritEvent.Heal(entity, amount));
	}

	public static SpiritEvent.Damage spiritDamage(LivingEntity entity, double amount) {
		return NeoForge.EVENT_BUS.post(new SpiritEvent.Damage(entity, amount));
	}
}
