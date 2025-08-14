package ctn.project_moon.init;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import static ctn.project_moon.PmMain.MOD_ID;

/// 尽量用{@link IModDamageSource#setFourColorDamageTypes(PmDamageTool.ColorType)}
public interface PmDamageTypes extends DamageTypes {
	/**
	 * 物理
	 */
	ResourceKey<DamageType> PHYSICS  = create("physics");
	/**
	 * 精神
	 */
	ResourceKey<DamageType> SPIRIT   = create("rationality");
	/**
	 * 侵蚀
	 */
	ResourceKey<DamageType> EROSION  = create("erosion");
	/**
	 * 灵魂
	 */
	ResourceKey<DamageType> THE_SOUL = create("the_soul");
	/**
	 * ABNORMALITIES 异想体
	 */
	ResourceKey<DamageType> ABNOS    = create("abnos");
	// Extermination of Geometrical Organ 是的没错这玩意的全称就是这么长
	/**
	 * EGO
	 */
	ResourceKey<DamageType> EGO      = create("ego");
	
	
	/** 创建伤害类型 */
	static ResourceKey<DamageType> create(final String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
	}
}
