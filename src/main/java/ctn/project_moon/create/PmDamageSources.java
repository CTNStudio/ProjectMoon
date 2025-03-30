package ctn.project_moon.create;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;

import static ctn.project_moon.datagen.PmDamageTypes.*;

public class PmDamageSources extends DamageSources {
    private final DamageSource physics;
    private final DamageSource spirit;
    private final DamageSource erosion;
    private final DamageSource theSoul;
//    private final DamageSource egoWeapon;

    public PmDamageSources(RegistryAccess registry) {
        super(registry);
        physics = source(PHYSICS);
        spirit = source(SPIRIT);
        erosion = source(EROSION);
        theSoul = source(THE_SOUL);
    }

    public DamageSource physics() {
        return physics;
    }

    public DamageSource spirit() {
        return spirit;
    }

    public DamageSource erosion() {
        return erosion;
    }

    public DamageSource theSoul() {
        return theSoul;
    }

/*    public static DamageSource SPIRIT_DAMAGE(Entity causer) {
        return createDamage(causer, SPIRIT);
    }

    public static DamageSource EROSION_DAMAGE(Entity causer) {
        return createDamage(causer, EROSION);
    }

    public static DamageSource THE_SOUL_DAMAGE(Entity causer) {
        return createDamage(causer, THE_SOUL);
    }

    public static DamageSource PHYSICS_DAMAGE(Entity causer) {
        return createDamage(causer, PHYSICS);
    }

    private static @NotNull DamageSource createDamage(Entity causer, ResourceKey<DamageType> damageTypes) {
        return new DamageSource(causer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypes), causer);
    }

    public DamageSource createDamageSource(ResourceKey<DamageType> damageTypes) {
        return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypes), null, null, null);
    }*/
}
