package ctn.project_moon.common.item;

import ctn.project_moon.api.GradeType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static ctn.project_moon.api.GradeType.Level.getEgoLevelTag;
import static ctn.project_moon.datagen.PmTags.PmItem.*;

public interface Ego {
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
        return GradeType.Level.getItemLevel(getEgoLevelTag(item));
    }

    /**
     * 返回物品等级
     */
    static int getItemLevelValue(TagKey<Item> itemLevelTag) {
        final GradeType.Level type = GradeType.Level.getItemLevel(itemLevelTag);
        return type.getLevel();
    }
}
