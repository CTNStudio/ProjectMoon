package ctn.project_moon.init;

import ctn.project_moon.tool.PmTool;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

import static ctn.project_moon.init.PmDamageTypes.*;

/**
 * 伤害来源
 */
public class PmDamageSources extends DamageSources {
	private final DamageSource physics;
	private final DamageSource spirit;
	private final DamageSource erosion;
	private final DamageSource theSoul;
	private final DamageSource abnos;
	private final DamageSource ego;

	public PmDamageSources(RegistryAccess registry) {
		super(registry);
		physics = source(PHYSICS);
		spirit = source(SPIRIT);
		erosion = source(EROSION);
		theSoul = source(THE_SOUL);
		abnos = source(ABNOS);
		ego = source(EGO);
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> physicsDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).physics();
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> spiritDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).spirit();
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> erosionDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).erosion();
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> theSoulDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).theSoul();
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> abnosDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).abnos();
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> egoDamage() {
		return (attacker, target) -> getDamageSource().apply(attacker, target).ego();
	}

	public static DamageSource spiritDamage(Entity causer) {
		return createDamage(causer, SPIRIT);
	}

	public static DamageSource erosionDamage(Entity causer) {
		return createDamage(causer, EROSION);
	}

	public static DamageSource theSoulDamage(Entity causer) {
		return createDamage(causer, THE_SOUL);
	}

	public static DamageSource physicsDamage(Entity causer) {
		return createDamage(causer, PHYSICS);
	}

	public static DamageSource abnosDamage(Entity causer) {
		return createDamage(causer, ABNOS);
	}

	public static DamageSource egoDamage(Entity causer) {
		return createDamage(causer, EGO);
	}

	private static @NotNull DamageSource createDamage(Entity causer, ResourceKey<DamageType> damageTypes) {
		return new DamageSource(causer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypes), causer);
	}

	public static BiFunction<LivingEntity, LivingEntity, ? extends PmDamageSources> getDamageSource() {
		return (attacker, target) -> PmTool.getDamageSource(attacker);
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

	public DamageSource abnos() {
		return abnos;
	}

	public DamageSource ego() {
		return ego;
	}

}
