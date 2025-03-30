package ctn.project_moon.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.datagen.PmDamageTypes.*;

/**
 * @author wang_
 * @version 2024.3.4.1
 * @description
 * @date 2025/3/29
 */
public class PmDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, context -> {
                createDamageType(context, SPIRIT,
                        DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                        0.1f,
                        DamageEffects.HURT,
                        DeathMessageType.DEFAULT);
                createDamageType(context, EROSION,
                        DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                        0.1f,
                        DamageEffects.HURT,
                        DeathMessageType.DEFAULT);
                createDamageType(context, THE_SOUL,
                        DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                        0.1f,
                        DamageEffects.HURT,
                        DeathMessageType.DEFAULT);
                createDamageType(context, PHYSICS,
                        DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                        0.1f,
                        DamageEffects.HURT,
                        DeathMessageType.DEFAULT);
            });

    public PmDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MOD_ID));
    }

    public static Holder.Reference<DamageType> createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> damageType, DamageScaling damageScaling, float exhaustion, DamageEffects damageEffects, DeathMessageType deathMessageType) {
        return context.register(damageType, new DamageType(damageType.location().getPath(), damageScaling, exhaustion, damageEffects, deathMessageType));
    }

}
