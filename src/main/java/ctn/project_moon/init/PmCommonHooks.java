package ctn.project_moon.init;

import ctn.project_moon.events.entity.ArmorAbsorptionEvent;
import ctn.project_moon.events.DourColorDamageTypesEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PmCommonHooks {
    private static final Logger LOGGER = LogManager.getLogger();

    public static ArmorAbsorptionEvent.Pre entityArmorAbsorptionPre(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        return NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Pre(entity, damageSource, damageAmount));
    }

    public static ArmorAbsorptionEvent.Post entityArmorAbsorptionPost(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        return NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Post(entity, damageSource, damageAmount));
    }

    public static DourColorDamageTypesEvents dourColorDamageType(LivingEntity entity, DamageSource source, DamageContainer container) {
        return NeoForge.EVENT_BUS.post(new DourColorDamageTypesEvents(entity, source, container));
    }

    public static DourColorDamageTypesEvents dourColorDamageType(LivingEntity entity, DamageSource source) {
        return NeoForge.EVENT_BUS.post(new DourColorDamageTypesEvents(entity, source));
    }
}
