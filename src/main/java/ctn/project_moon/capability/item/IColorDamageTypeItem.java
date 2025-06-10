package ctn.project_moon.capability.item;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.List;

public interface IColorDamageTypeItem {
	/// 获取物品的伤害类型（当前的）
	@CheckForNull
	PmDamageTool.ColorType getDamageType(ItemStack stack);

	/// 获取可以造成的伤害类型
	@CheckForNull
	List<PmDamageTool.ColorType> getCanCauseDamageTypes();

	Component getFourColorDamageTypeToTooltip();
}
