package ctn.project_moon.init;

import ctn.project_moon.events.DourColorDamageTypesEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 事件
 */
public class PmCommonHooks {
    private static final Logger LOGGER = LogManager.getLogger();

    public static DourColorDamageTypesEvent dourColorDamageType(LivingEntity entity, DamageSource source, DamageContainer container) {
        return NeoForge.EVENT_BUS.post(new DourColorDamageTypesEvent(entity, source, container));
    }

    public static DourColorDamageTypesEvent dourColorDamageType(LivingEntity entity, DamageSource source) {
        return NeoForge.EVENT_BUS.post(new DourColorDamageTypesEvent(entity, source));
    }
}
