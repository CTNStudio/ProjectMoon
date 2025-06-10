package ctn.project_moon.capability;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;

import static ctn.project_moon.init.PmCapability.Level.LEVEL_ITEM;

public interface ILevel {
	@CheckForNull
	PmDamageTool.Level getItemLevel();

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
		var level = getItemLevel(item);
		if (level == null) {
			return 0;
		}
		return level.getLevelValue();

	}

	/**
	 * @return {@link PmDamageTool.Level}
	 */
	static PmDamageTool.Level getItemLevel(ItemStack item) {
		var capability = item.getCapability(LEVEL_ITEM);
		if (capability == null) {
			return null;
		}
		return capability.getItemLevel();
	}
}
