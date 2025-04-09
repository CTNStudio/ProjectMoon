package ctn.project_moon.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PmCommonHooks {
    private static final Logger LOGGER = LogManager.getLogger();

    public static ArmorAbsorptionEvent.Pre entityArmorAbsorptionPre(Entity entity, DamageSource damageSource, float damageAmount) {
        return NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Pre(entity, damageSource, damageAmount));
    }

    public static ArmorAbsorptionEvent.Post entityArmorAbsorptionPost(Entity entity, DamageSource damageSource, float damageAmount) {
        return NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Post(entity, damageSource, damageAmount));
    }
}
