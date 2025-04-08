package ctn.project_moon.common.item;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.datagen.PmTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static ctn.project_moon.api.GradeType.Level.*;
import static ctn.project_moon.datagen.PmTags.PmItem.*;

public interface EgoItem {
    List<TagKey<Item>> DAMAGE_TYPE = List.of(PHYSICS, SPIRIT, EROSION, THE_SOUL);

    /**
     * 返回之间的等级差值
     */
    static int leveDifferenceValue(ItemStack item, ItemStack item2) {
        return getItemLevelValue(item) - getItemLevelValue(item2);
    }

    /**
     * 返回物品等级
     */
    static int getItemLevelValue(ItemStack item) {
        return getItemLevelValue(getEgoLevelTag(item));

    }

    /**
     * @return {@link GradeType.Level}
     */
    static GradeType.Level getItemLevel(ItemStack item) {
        return getItemTag(getEgoLevelTag(item));
    }

    /**
     * 返回物品等级
     */
    static int getItemLevelValue(TagKey<Item> itemLevelTag) {
        final var type = getItemTag(itemLevelTag);
        return Objects.nonNull(type) ? type.getLevel() : 0;
    }

    /**
     * 返回EGO伤害类型 仅物品描述用
     */
    static List<TagKey<Item>> egoDamageTypes(ItemStack item) {
        return item.getTags().filter(DAMAGE_TYPE::contains).toList();
    }

    enum DamageTypes {
        PHYSICS(PmTags.PmItem.PHYSICS),
        SPIRIT(PmTags.PmItem.SPIRIT),
        EROSION(PmTags.PmItem.EROSION),
        THE_SOUL(PmTags.PmItem.THE_SOUL);

        private final TagKey<Item> key;

        DamageTypes(TagKey<Item> key) {
            this.key = key;
        }

        @CheckForNull
        public static DamageTypes getTypeByTag(TagKey<Item> tag) {
            return Arrays.stream(DamageTypes.values())
                    .filter(it -> tag.equals(it.getKey()))
                    .findFirst()
                    .orElse(null);
        }

        public TagKey<Item> getKey() {
            return key;
        }
    }
}
