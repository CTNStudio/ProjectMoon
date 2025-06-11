package ctn.project_moon.registrar;

import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import ctn.project_moon.init.PmEntityAttributes;
import ctn.project_moon.init.PmEntitys;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.minecraft.world.entity.EntityType.*;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class RegistrarEntityAttributeEvents {

	/**
	 * 注册实体属性
	 */
	@SubscribeEvent
	public static void entityAttribute(EntityAttributeCreationEvent event) {
		event.put(PmEntitys.TRAINING_RABBITS.get(), TrainingRabbits.createAttributes().build());
	}

	/// 添加或修改属性 等级在{@link RegistrarCapability}类注册
	@SubscribeEvent
	public static void addAttribute(EntityAttributeModificationEvent event) {
		addAttributes(event, PLAYER);
		setFourColorResistanceAttributes(event, WARDEN, 0.6, 1.2, 0.8, 0.2);
		setFourColorResistanceAttributes(event, ENDER_DRAGON, 0.5, 0.5, 0.5, 0.5);
		setFourColorResistanceAttributes(event, WITHER, 0.5, 0.7, -1.0, 1.0);
		setFourColorResistanceAttributes(event, IRON_GOLEM, 0.5, 0.6, 1.5, 1.0);
		setFourColorResistanceAttributes(event, ELDER_GUARDIAN, 0.5, 0.8, 0.9, 1.2);
		setFourColorResistanceAttributes(event, RAVAGER, 0.5, 1.0, 1.5, 1.3);
		setFourColorResistanceAttributes(event, GUARDIAN, 0.5, 0.8, 0.9, 1.2);
		setFourColorResistanceAttributes(event, ENDERMAN, 0.8, 0.5, 1.2, 1.5);
		setFourColorResistanceAttributes(event, GHAST, 0.5, 0.5, 1.2, 1.5);
		setFourColorResistanceAttributes(event, HOGLIN, 0.8, 1.2, 1.1, 1.2);
		setFourColorResistanceAttributes(event, PIGLIN_BRUTE, 0.6, 1.3, 1.0, 1.1);
		setFourColorResistanceAttributes(event, SHULKER, 0.2, 1.5, 1.0, 1.1);
		setFourColorResistanceAttributes(event, ZOGLIN, 0.5, 1.2, 1.2, 1.3);
		setFourColorResistanceAttributes(event, EVOKER, 1.0, 1.2, 1.3, 1.3);
		setFourColorResistanceAttributes(event, VINDICATOR, 0.8, 1.2, 1.3, 1.3);
		setFourColorResistanceAttributes(event, WITCH, 1.2, 1.1, 1.0, 1.3);
		setFourColorResistanceAttributes(event, WITHER_SKELETON, 0.8, 0.8, -1.0, 1.1);
		setFourColorResistanceAttributes(event, BLAZE, 0.7, 0.8, 1.3, 1.2);
		setFourColorResistanceAttributes(event, BOGGED, 1.0, 0.5, 0.7, 1.0);
		setFourColorResistanceAttributes(event, SKELETON, 0.9, 0.6, 0.8, 1.0);
		setFourColorResistanceAttributes(event, STRAY, 0.8, 0.6, 0.8, 1.0);
		setFourColorResistanceAttributes(event, ZOMBIE, 0.7, 0.8, 0.9, 1.1);
		setFourColorResistanceAttributes(event, ZOMBIFIED_PIGLIN, 0.6, 0.7, 0.5, 1.3);
		setFourColorResistanceAttributes(event, DROWNED, 0.8, 0.8, 1.0, 1.1);
		setFourColorResistanceAttributes(event, BREEZE, 0.5, 0.8, 1.3, 1.2);
		setFourColorResistanceAttributes(event, CREEPER, 1.2, 0.8, 1.2, 1.2);
		setFourColorResistanceAttributes(event, HUSK, 0.6, 0.6, 0.8, 1.2);
		setFourColorResistanceAttributes(event, MAGMA_CUBE, 0.4, 0.6, 1.4, 1.2);
		setFourColorResistanceAttributes(event, PHANTOM, 0.6, 1.0, 0.8, 1.3);
		setFourColorResistanceAttributes(event, ENDERMITE, 0.9, 1.2, 1.1, 1.3);
		setFourColorResistanceAttributes(event, SILVERFISH, 0.8, 1.2, 1.3, 1.3);
		setFourColorResistanceAttributes(event, VEX, 0.8, 1.3, 1.1, 1.5);
		setFourColorResistanceAttributes(event, PILLAGER, 0.8, 1.2, 1.3, 1.3);
		setFourColorResistanceAttributes(event, PIGLIN, 0.8, 1.2, 1.3, 1.2);
		setFourColorResistanceAttributes(event, SPIDER, 0.7, 1.1, 1.3, 1.1);
		setFourColorResistanceAttributes(event, CAVE_SPIDER, 0.7, 1.1, 1.0, 1.1);
		setFourColorResistanceAttributes(event, SLIME, 0.5, 0.7, 1.2, 1.1);
	}

	private static void setFourColorResistanceAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, double physics, double spirit, double erosion, double theSoul) {
		event.add(entityType, PmEntityAttributes.PHYSICS_RESISTANCE, physics);
		event.add(entityType, PmEntityAttributes.SPIRIT_RESISTANCE, spirit);
		event.add(entityType, PmEntityAttributes.EROSION_RESISTANCE, erosion);
		event.add(entityType, PmEntityAttributes.THE_SOUL_RESISTANCE, theSoul);
	}

	private static void addAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType) {
		event.add(entityType, PmEntityAttributes.MAX_SPIRIT);
		event.add(entityType, PmEntityAttributes.SPIRIT_NATURAL_RECOVERY_RATE);
		event.add(entityType, PmEntityAttributes.SPIRIT_RECOVERY_AMOUNT);
		event.add(entityType, PmEntityAttributes.MAX_FORTITUDE);
		event.add(entityType, PmEntityAttributes.MAX_PRUDENCE);
		event.add(entityType, PmEntityAttributes.MAX_TEMPERANCE);
		event.add(entityType, PmEntityAttributes.MAX_JUSTICE);
		event.add(entityType, PmEntityAttributes.FORTITUDE_ADDITIONAL);
		event.add(entityType, PmEntityAttributes.PRUDENCE_ADDITIONAL);
		event.add(entityType, PmEntityAttributes.TEMPERANCE_ADDITIONAL);
		event.add(entityType, PmEntityAttributes.JUSTICE_ADDITIONAL);
		event.add(entityType, PmEntityAttributes.COMPOSITE_RATING);
		event.add(entityType, PmEntityAttributes.ID_ACT);
	}
}
