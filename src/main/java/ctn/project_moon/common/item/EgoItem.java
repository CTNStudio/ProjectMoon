package ctn.project_moon.common.item;

import ctn.project_moon.api.PmApi;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

import static ctn.project_moon.datagen.PmTags.PmItem.*;

/**
* EGO
*/
public interface EgoItem{
    /** 返回EGO等级tga */
    static TagKey<Item> getEgoLevelTag(ItemStack item){
        return item.getTags()
                .filter(it -> Objects.nonNull(AbnosEntity.AbnosType.getTypeByTag(it)))
                .findFirst()
                .orElse(ZAYIN);
    }

    /** 返回EGO等级 */
    static int levelValue(TagKey<Item> levelTag) {
        final var type = AbnosEntity.AbnosType.getTypeByTag(levelTag);
        return Objects.nonNull(type) ? type.getLevel() : 0;
    }

    List<TagKey<Item>> DAMAGE_TYPE = List.of(PHYSICS, SPIRIT, EROSION, THE_SOUL);

    /** 返回EGO伤害类型 */
    static List<TagKey<Item>> egoDamageTypes(ItemStack item){
        return item.getTags().filter(DAMAGE_TYPE::contains).toList();
    }

    /** 返回EGO之间的等级差值 */
    static int leveDifferenceValue(TagKey<Item> levelTag, TagKey<Item> levelTag2){
        return levelValue(levelTag) - levelValue(levelTag2);
    }

    /** 返回EGO之间的伤害倍数 */
    static double damageMultiple(TagKey<Item> levelTag, TagKey<Item> levelTag2) {
        return PmApi.damageMultiple(leveDifferenceValue(levelTag, levelTag2));
    }
}
