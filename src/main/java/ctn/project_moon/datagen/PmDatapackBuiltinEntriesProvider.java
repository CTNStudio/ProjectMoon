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
import static ctn.project_moon.init.PmDamageTypes.*;

public class PmDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, context -> {
                createDamageType(context, PHYSICS, 0.1f);

                createDamageType(context, SPIRIT, 0.2f);

                createDamageType(context, EROSION, 0.3f);

                createDamageType(context, THE_SOUL, 0.4f);

                createDamageType(context, ABNOS, 0.3f);

                createDamageType(context, EGO,0.3f);
            });

    public PmDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MOD_ID));
    }

    public static Holder.Reference<DamageType> createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> damageType, DamageScaling damageScaling, float exhaustion, DamageEffects damageEffects, DeathMessageType deathMessageType) {
        return context.register(damageType, new DamageType(damageType.location().getPath(), damageScaling, exhaustion, damageEffects, deathMessageType));
    }

    public static Holder.Reference<DamageType> createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> damageType, float exhaustion) {
        return createDamageType(context, damageType, DamageScaling.ALWAYS, exhaustion, DamageEffects.HURT, DeathMessageType.DEFAULT);
    }

}
