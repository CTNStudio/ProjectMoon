package ctn.project_moon.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmDamageTypes implements DamageTypes {
    /** 物理 */
    public static final ResourceKey<DamageType> PHYSICS = create("physics");
    /** 精神 */
    public static final ResourceKey<DamageType> SPIRIT = create("spirit");
    /** 侵蚀 */
    public static final ResourceKey<DamageType> EROSION = create("erosion");
    /** 灵魂 */
    public static final ResourceKey<DamageType> THE_SOUL = create("the_soul");

    public static ResourceKey<DamageType> create(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
