package ctn.project_moon.events;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.ALEPH;
import static net.minecraft.world.entity.EntityType.*;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class EntityAttributeEvents {
    @SubscribeEvent
    public static void add(EntityAttributeModificationEvent event) {
        addSpiritAttributes(event, PLAYER);
        setFourColorResistanceAttributes(event, WARDEN, ALEPH, 0.6, 1.2, 0.8, 0.2);
        setFourColorResistanceAttributes(event, WITHER, ALEPH, 0.5, 0.7, 0.2, 1.0);
        setFourColorResistanceAttributes(event, ENDER_DRAGON, ALEPH, 0.5, 0.5, 0.5, 0.5);
    }

    private static void setFourColorResistanceAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, GradeType.Level level, double physics, double spirit, double erosion, double theSoul) {
        setLevelAttributes(event, entityType, level);
        setFourColorResistanceAttributes(event, entityType, physics, spirit, erosion, theSoul);
    }

    private static void addSpiritAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType) {
        event.add(entityType, PmAttributes.MAX_SPIRIT);
        event.add(entityType, PmAttributes.SPIRIT_NATURAL_RECOVERY_RATE);
        event.add(entityType, PmAttributes.SPIRIT_RECOVERY_AMOUNT);
    }

    private static void setFourColorResistanceAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, double physics, double spirit, double erosion, double theSoul) {
        event.add(entityType, PmAttributes.PHYSICS_RESISTANCE, physics);
        event.add(entityType, PmAttributes.SPIRIT_RESISTANCE, spirit);
        event.add(entityType, PmAttributes.EROSION_RESISTANCE, erosion);
        event.add(entityType, PmAttributes.THE_SOUL_RESISTANCE, theSoul);
    }

    private static void setLevelAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, GradeType.Level level){
        event.add(entityType, PmAttributes.ENTITY_LEVEL, level.getLevelValue());
    }
}
