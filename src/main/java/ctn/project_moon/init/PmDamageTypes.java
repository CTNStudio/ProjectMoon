package ctn.project_moon.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import javax.annotation.CheckForNull;
import java.util.Arrays;

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

    static String getDamageTypeLocation(PmDamageTypes.Types damageType) {
        return damageType.getKey().location().toString();
    }

    enum Types{
        /** 物理 */
        PHYSICS(PmDamageTypes.PHYSICS),
        /** 精神 */
        SPIRIT(PmDamageTypes.SPIRIT),
        /** 侵蚀 */
        EROSION(PmDamageTypes.EROSION),
        /** 灵魂 */
        THE_SOUL(PmDamageTypes.THE_SOUL);

        private final ResourceKey<DamageType> key;
        private final String location;
        Types(ResourceKey<DamageType> key){
            this.key = key;
            this.location = key.location().toString();
        }

        public ResourceKey<DamageType> getKey() {
            return key;
        }

        @CheckForNull
        public static PmDamageTypes.Types getType(ResourceKey<DamageType> key) {
            return Arrays.stream(PmDamageTypes.Types.values())
                    .filter(it -> key.equals(it.getKey()))
                    .findFirst()
                    .orElse(null);
        }

        @CheckForNull
        public static PmDamageTypes.Types getType(String keyString) {
            return Arrays.stream(PmDamageTypes.Types.values())
                    .filter(it -> keyString.equals(it.getLocationString()))
                    .findFirst()
                    .orElse(null);
        }

        public String getLocationString() {
            return location;
        }
    }
}
