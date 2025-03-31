package ctn.project_moon.create;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import static ctn.project_moon.PmMain.MOD_ID;

public interface PmDamageTypes extends DamageTypes {
    /** 物理 */
    ResourceKey<DamageType> PHYSICS = create("physics");
    /** 精神 */
    ResourceKey<DamageType> SPIRIT = create("spirit");
    /** 侵蚀 */
    ResourceKey<DamageType> EROSION = create("erosion");
    /** 灵魂 */
    ResourceKey<DamageType> THE_SOUL = create("the_soul");
    /** ABNORMALITIES 异想体 */
    ResourceKey<DamageType> ABNOS = create("abnos");
    // Extermination of Geometrical Organ 是的没错这玩意的全称就是这么长
    /** EGO */
    ResourceKey<DamageType> EGO = create("ego");

    static ResourceKey<DamageType> create(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
