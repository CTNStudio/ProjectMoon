package ctn.project_moon.common.item;

import ctn.project_moon.tool.GradeTypeTool;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static ctn.project_moon.datagen.PmTags.PmItem.*;
import static ctn.project_moon.tool.GradeTypeTool.Level.getEgoLevelTag;

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
     * @return {@link GradeTypeTool.Level}
     */
    static GradeTypeTool.Level getItemLevel(ItemStack item) {
        return GradeTypeTool.Level.getItemLevel(getEgoLevelTag(item));
    }

    /** 获取武器等级 */
    static GradeTypeTool.Level getItemLevel(TagKey<Item> egoLevelTag) {
        return GradeTypeTool.Level.getItemLevel(egoLevelTag);
    }

    /**
     * 返回物品等级
     */
    static int getItemLevelValue(TagKey<Item> itemLevelTag) {
        final GradeTypeTool.Level type = GradeTypeTool.Level.getItemLevel(itemLevelTag);
        return type.getLevelValue();
    }


}
