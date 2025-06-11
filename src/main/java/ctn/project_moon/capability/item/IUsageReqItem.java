package ctn.project_moon.capability.item;

import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.init.PmItemDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/// 带有需求的物品 如果不满足要求则触发
/// <p>
/// 一般捆绑{@link IColorUsageReqItem}或{@link ItemColorUsageReq}或{@link PmItemDataComponents#ITEM_COLOR_USAGE_REQ}
public interface IUsageReqItem{
	/** 使用物品时触发 */
	void useImpede(ItemStack itemStack, Level level, LivingEntity entity);

	/** 攻击时触发 */
	void attackImpede(ItemStack itemStack, Level level, LivingEntity entity);

	/** 在手上时触发 */
	void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity);

	/** 物品在背包时里触发 */
	void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity);

	/** 在装备里时触发，如盔甲，饰品 */
	void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity);
}
