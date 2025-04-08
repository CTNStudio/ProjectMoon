package ctn.project_moon.init;

import ctn.project_moon.datagen.PmTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.item.EgoItem.DAMAGE_TYPE;

public interface PmDamageTypes extends DamageTypes {
    /**
     * 物理
     */
    ResourceKey<DamageType> PHYSICS = create("physics");
    /**
     * 精神
     */
    ResourceKey<DamageType> SPIRIT = create("spirit");
    /**
     * 侵蚀
     */
    ResourceKey<DamageType> EROSION = create("erosion");
    /**
     * 灵魂
     */
    ResourceKey<DamageType> THE_SOUL = create("the_soul");
    /**
     * ABNORMALITIES 异想体
     */
    ResourceKey<DamageType> ABNOS = create("abnos");
    // Extermination of Geometrical Organ 是的没错这玩意的全称就是这么长
    /**
     * EGO
     */
    ResourceKey<DamageType> EGO = create("ego");

    static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    static String getDamageTypeLocation(PmDamageTypes.Types damageType) {
        return damageType.getKey().location().toString();
    }

    enum Types {
        /**
         * 物理
         */
        PHYSICS(PmDamageTypes.PHYSICS, PmTags.PmItem.PHYSICS, PmTags.PmDamageType.PHYSICS),
        /**
         * 精神
         */
        SPIRIT(PmDamageTypes.SPIRIT, PmTags.PmItem.SPIRIT, PmTags.PmDamageType.SPIRIT),
        /**
         * 侵蚀
         */
        EROSION(PmDamageTypes.EROSION,PmTags.PmItem.EROSION, PmTags.PmDamageType.EROSION),
        /**
         * 灵魂
         */
        THE_SOUL(PmDamageTypes.THE_SOUL, PmTags.PmItem.THE_SOUL, PmTags.PmDamageType.THE_SOUL),;

        private final TagKey<Item> itemTga;
        private final TagKey<DamageType> damageTypeTag;
        private final ResourceKey<DamageType> key;
        private final String location;

        Types(ResourceKey<DamageType> key,TagKey<Item> itemTga, TagKey<DamageType> damageTypeTag) {
            this.key = key;
            this.location = key.location().toString();
            this.itemTga = itemTga;
            this.damageTypeTag = damageTypeTag;
        }

        public static PmDamageTypes.Types getType(DamageSource damageSource) {
            return Arrays.stream(PmDamageTypes.Types.values())
                    .filter(it -> damageSource.is(it.getDamageTypeTag()))
                    .findFirst()
                    .orElse(null);
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

        public ResourceKey<DamageType> getKey() {
            return key;
        }

        public String getLocationString() {
            return location;
        }

        public TagKey<DamageType> getDamageTypeTag() {
            return damageTypeTag;
        }

        public TagKey<Item> getItemTga() {
            return itemTga;
        }


        /**
         * 返回EGO伤害类型 仅物品描述用
         */
        public static List<TagKey<Item>> egoDamageTypes(ItemStack item) {
            return item.getTags().filter(DAMAGE_TYPE::contains).toList();
        }
    }
}
