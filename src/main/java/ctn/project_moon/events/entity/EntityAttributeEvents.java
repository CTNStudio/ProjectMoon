package ctn.project_moon.events.entity;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import ctn.project_moon.init.PmAttributes;
import ctn.project_moon.init.PmEntitys;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.*;
import static net.minecraft.world.entity.EntityType.*;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class EntityAttributeEvents {

    /**
     * 注册实体
     */
    @SubscribeEvent
    public static void entityAttribute(EntityAttributeCreationEvent event){
        event.put(PmEntitys.TRAINING_RABBITS.get(), TrainingRabbits.createAttributes().build());
    }

    /** 添加或修改属性 */
    @SubscribeEvent
    public static void add(EntityAttributeModificationEvent event) {
        addSpiritAttributes(event, PLAYER);
        setFourColorResistanceAttributes(event, WARDEN, ALEPH, 0.6, 1.2, 0.8, 0.2);
        setFourColorResistanceAttributes(event, ENDER_DRAGON, ALEPH, 0.5, 0.5, 0.5, 0.5);
        setFourColorResistanceAttributes(event, WITHER, ALEPH, 0.5, 0.7, -1.0, 1.0);
        setFourColorResistanceAttributes(event, IRON_GOLEM, WAW, 0.5, 0.6, 1.5, 1.0);
        setFourColorResistanceAttributes(event, ELDER_GUARDIAN, WAW, 0.5, 0.8, 0.9, 1.2);
        setFourColorResistanceAttributes(event, RAVAGER, WAW, 0.5, 1.0, 1.5, 1.3);
        setFourColorResistanceAttributes(event, GUARDIAN, HE, 0.5, 0.8, 0.9, 1.2);
        setFourColorResistanceAttributes(event, ENDERMAN, HE, 0.8, 0.5, 1.2, 1.5);
        setFourColorResistanceAttributes(event, GHAST, HE, 0.5, 0.5, 1.2, 1.5);
        setFourColorResistanceAttributes(event, HOGLIN, HE, 0.8, 1.2, 1.1, 1.2);
        setFourColorResistanceAttributes(event, PIGLIN_BRUTE, HE, 0.6, 1.3, 1.0, 1.1);
        setFourColorResistanceAttributes(event, SHULKER, HE, 0.2, 1.5, 1.0, 1.1);
        setFourColorResistanceAttributes(event, ZOGLIN, HE, 0.5, 1.2, 1.2, 1.3);
        setFourColorResistanceAttributes(event, EVOKER, HE, 1.0, 1.2, 1.3, 1.3);
        setFourColorResistanceAttributes(event, VINDICATOR, HE, 0.8, 1.2, 1.3, 1.3);
        setFourColorResistanceAttributes(event, WITCH, HE, 1.2, 1.1, 1.0, 1.3);
        setFourColorResistanceAttributes(event, WITHER_SKELETON, HE, 0.8, 0.8, -1.0, 1.1);
        setLevelAttributes(event, (EntityType<? extends LivingEntity>) (EntityType<? extends Entity>) SHULKER_BULLET, HE);
        setFourColorResistanceAttributes(event, BLAZE, TETH, 0.7, 0.8, 1.3, 1.2);
        setFourColorResistanceAttributes(event, BOGGED, TETH, 1.0, 0.5, 0.7, 1.0);
        setFourColorResistanceAttributes(event, SKELETON, TETH, 0.9, 0.6, 0.8, 1.0);
        setFourColorResistanceAttributes(event, STRAY, TETH, 0.8, 0.6, 0.8, 1.0);
        setFourColorResistanceAttributes(event, ZOMBIE, TETH, 0.7, 0.8, 0.9, 1.1);
        setFourColorResistanceAttributes(event, ZOMBIFIED_PIGLIN, TETH, 0.6, 0.7, 0.5, 1.3);
        setFourColorResistanceAttributes(event, DROWNED, TETH, 0.8, 0.8, 1.0, 1.1);
        setFourColorResistanceAttributes(event, BREEZE, TETH, 0.5, 0.8, 1.3, 1.2);
        setFourColorResistanceAttributes(event, CREEPER, TETH, 1.2, 0.8, 1.2, 1.2);
        setFourColorResistanceAttributes(event, HUSK, TETH, 0.6, 0.6, 0.8, 1.2);
        setFourColorResistanceAttributes(event, MAGMA_CUBE, TETH, 0.4, 0.6, 1.4, 1.2);
        setFourColorResistanceAttributes(event, SLIME, TETH, 0.5, 0.7, 1.2, 1.1);
        setFourColorResistanceAttributes(event, PHANTOM, TETH, 0.6, 1.0, 0.8, 1.3);
        setFourColorResistanceAttributes(event, ENDERMITE, TETH, 0.9, 1.2, 1.1, 1.3);
        setFourColorResistanceAttributes(event, SILVERFISH, TETH, 0.8, 1.2, 1.3, 1.3);
        setFourColorResistanceAttributes(event, VEX, TETH, 0.8, 1.3, 1.1, 1.5);
        setFourColorResistanceAttributes(event, PILLAGER, TETH, 0.8, 1.2, 1.3, 1.3);
        setFourColorResistanceAttributes(event, PIGLIN, TETH, 0.8, 1.2, 1.3, 1.2);
        setFourColorResistanceAttributes(event, SPIDER, TETH, 0.7, 1.1, 1.3, 1.1);
        setFourColorResistanceAttributes(event, CAVE_SPIDER, TETH, 0.7, 1.1, 1.0, 1.1);
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
