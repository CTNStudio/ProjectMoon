package ctn.project_moon.common.item;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static ctn.project_moon.api.tool.PmDamageTool.Level.getEgoLevelTag;
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
	 * @return {@link PmDamageTool.Level}
	 */
	static PmDamageTool.Level getItemLevel(ItemStack item) {
		return PmDamageTool.Level.getItemLevel(getEgoLevelTag(item));
	}

	/** 获取武器等级 */
	static PmDamageTool.Level getItemLevel(TagKey<Item> egoLevelTag) {
		return PmDamageTool.Level.getItemLevel(egoLevelTag);
	}

	/**
	 * 返回物品等级
	 */
	static int getItemLevelValue(TagKey<Item> itemLevelTag) {
		final PmDamageTool.Level type = PmDamageTool.Level.getItemLevel(itemLevelTag);
		return type.getLevelValue();
	}


}
