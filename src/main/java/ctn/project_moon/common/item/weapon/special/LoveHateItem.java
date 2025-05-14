package ctn.project_moon.common.item.weapon.special;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.common.item.RequestItem;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.abstract_ltem.SpecialEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;

public class LoveHateItem extends SpecialEgoWeapon implements RequestItem {
	public LoveHateItem(Weapon.Builder builder) {
		super(builder.build().component(ITEM_COLOR_USAGE_REQ, ItemColorUsageReq.empty()
				.setValue(FourColorAttribute.Type.FORTITUDE, FourColorAttribute.Rating.III)
				.setValue(FourColorAttribute.Type.JUSTICE, FourColorAttribute.Rating.III)
				.setValue(FourColorAttribute.Type.COMPOSITE_RATING, FourColorAttribute.Rating.IV)
		), builder);
	}

	/**
	 * 使用物品时触发
	 */
	@Override
	public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {

	}

	/**
	 * 攻击时触发
	 */
	@Override
	public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

	}

	/**
	 * 在手上时触发
	 */
	@Override
	public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {

	}

	/**
	 * 物品在背包时里触发
	 */
	@Override
	public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

	}

	/**
	 * 在装备里时触发，如盔甲，饰品
	 */
	@Override
	public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {

	}
}
