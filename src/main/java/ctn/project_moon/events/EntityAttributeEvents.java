package ctn.project_moon.events;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.*;
import static net.minecraft.world.entity.EntityType.*;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class EntityAttributeEvents {
    @SubscribeEvent
    public static void attribute(EntityAttributeModificationEvent event) {
        setAttributes(event, WARDEN, ALEPH, 0.6, 1.5, 0.8, 0.5);
        setAttributes(event, WITHER, ALEPH, 0.5, 0.7, 0.4, 1.2);
        setAttributes(event, ENDER_DRAGON, ALEPH, 0.5, 0.5, 0.5, 0.5);
    }

    private static void setAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, GradeType.Level level, double physics, double spirit, double erosion, double theSoul) {
        setAttributes(event, entityType, level);
        setAttributes(event, entityType, physics, spirit, erosion, theSoul);
    }

    private static void setAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, double physics, double spirit, double erosion, double theSoul) {
        event.add(entityType, PmAttributes.PHYSICS_RESISTANCE, physics);
        event.add(entityType, PmAttributes.SPIRIT_RESISTANCE, spirit);
        event.add(entityType, PmAttributes.EROSION_RESISTANCE, erosion);
        event.add(entityType, PmAttributes.THE_SOUL_RESISTANCE, theSoul);
    }

    private static void setAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, GradeType.Level level){
        event.add(entityType, PmAttributes.ENTITY_LEVEL, level.getLevelValue());
    }
}
