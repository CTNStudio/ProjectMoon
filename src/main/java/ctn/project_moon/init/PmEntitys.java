package ctn.project_moon.init;

import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import ctn.project_moon.common.entity.projectile.MagicBulletEntity;
import ctn.project_moon.common.entity.projectile.ParadiseLostSpikeweed;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmEntitys {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);

	public static final Supplier<EntityType<TrainingRabbits>> TRAINING_RABBITS = registerEntity(
			"training_rabbits",
			EntityType.Builder.of(TrainingRabbits::new, MobCategory.MISC)
					.sized(0.625F, 1.375F)
					.eyeHeight(1F)
					.clientTrackingRange(8)
					.updateInterval(2)
					.canSpawnFarFromPlayer());

	public static final Supplier<EntityType<ParadiseLostSpikeweed>> PARADISE_LOST_SPIKEWEED = registerEntity(
			"paradise_lost_spikeweed",
			EntityType.Builder.of(ParadiseLostSpikeweed::new, MobCategory.MISC)
					.sized(2F, 2.5F)
					.clientTrackingRange(6)
					.updateInterval(2));

	public static final Supplier<EntityType<MagicBulletEntity>> MAGIC_BULLET_ENTITY = registerEntity(
			"magic_bullet",
			EntityType.Builder.<MagicBulletEntity>of(MagicBulletEntity::new, MobCategory.MISC)
					.sized(0.2F, 0.2F)
					.clientTrackingRange(6)
					.updateInterval(1));

	public static <I extends Entity> Supplier<EntityType<I>> registerEntity(final String name, final EntityType.Builder<I> sup) {
		return register(name, () -> sup.build(name));
	}

	public static <I extends EntityType<?>> DeferredHolder<EntityType<?>, I> register(final String name, final Supplier<? extends I> sup) {
		return ENTITY_TYPE_REGISTER.register(name, sup);
	}
}
