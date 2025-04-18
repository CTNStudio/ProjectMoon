package ctn.project_moon.init;

import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmEntitys {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);

    public static final Supplier<EntityType<TrainingRabbits>> TRAINING_RABBITS = registerEntity("training_rabbits",
            EntityType.Builder.of(TrainingRabbits::new, MobCategory.MISC)
                    .sized(0.625F, 1.375F)
                    .eyeHeight(1F)
                    .clientTrackingRange(8)
                    .updateInterval(2)
                    .canSpawnFarFromPlayer());

    public static  <I extends Entity> DeferredHolder<EntityType<?>, EntityType<I>> registerEntity(final String name, final EntityType.Builder<I> sup) {
        return register(name, () -> sup.build(name));
    }

    public static  <I extends EntityType<?>> DeferredHolder<EntityType<?>, I> register(final String name, final Supplier<? extends I> sup) {
        return ENTITY_TYPE.register(name, sup);
    }
}
