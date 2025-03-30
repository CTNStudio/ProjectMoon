package ctn.project_moon.common.items;

import ctn.project_moon.api.PmApi;
import ctn.project_moon.datagen.PmTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ctn.project_moon.datagen.PmTags.PmItem.*;

/**
 * EGO
 */
public interface EgoItem {
    /** 返回EGO等级tga */
    default TagKey<Item> getEgoLevelTag(ItemStack item){
        List<TagKey<Item>> tags = item.getTags().toList();
        Map<TagKey<Item>, TagKey<Item>> levelTags = Map.of(
                ALEPH, ALEPH,
                WAW, WAW,
                HE, HE,
                TETH, TETH,
                ZAYIN, ZAYIN);
        for (TagKey<Item> tag : tags) {
            if (levelTags.containsKey(tag)) {
                return levelTags.get(tag);
            }
        }
        return ZAYIN;
    }

    /** 返回EGO等级 */
    default int levelValue(TagKey<Item> levelTag) {
        if (levelTag.equals(PmTags.PmItem.ZAYIN)) return 1;
        else if (levelTag.equals(PmTags.PmItem.TETH)) return 2;
        else if (levelTag.equals(PmTags.PmItem.HE)) return 3;
        else if (levelTag.equals(PmTags.PmItem.WAW)) return 4;
        else if (levelTag.equals(PmTags.PmItem.ALEPH)) return 5;
        return 1;
    }

    /** 返回EGO伤害类型 */
    default List<TagKey<Item>> egoDamageTypes(ItemStack item){
        List<TagKey<Item>> itemTags = item.getTags().toList();
        List<TagKey<Item>> tags = new ArrayList<>();
        Map<TagKey<Item>, TagKey<Item>> levelTags = Map.of(
                PHYSICS, PHYSICS,
                SPIRIT, SPIRIT,
                EROSION, EROSION,
                THE_SOUL, THE_SOUL);
        for (TagKey<Item> tag : itemTags) {
            if (levelTags.containsKey(tag)) {
                tags.add(levelTags.get(tag));
            }
        }
        if (tags.isEmpty()) {
            tags.add(PHYSICS);
        }
        return tags;
    }

    /** 返回EGO之间的等级差值 */
    default int leveDifferenceValue(TagKey<Item> levelTag, TagKey<Item> levelTag2){
        return levelValue(levelTag) - levelValue(levelTag2);
    }

    /** 返回EGO之间的伤害倍数 */
    default double damageMultiple(TagKey<Item> levelTag, TagKey<Item> levelTag2) {
        return PmApi.damageMultiple(leveDifferenceValue(levelTag, levelTag2));
    }
}
