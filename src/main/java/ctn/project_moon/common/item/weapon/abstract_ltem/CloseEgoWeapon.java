package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.api.tool.PmDamageTool;
import net.minecraft.world.item.ItemStack;

import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;

/**
 * 近战EGO武器
 */
public abstract class CloseEgoWeapon extends EgoWeapon {
	public CloseEgoWeapon(Weapon.Builder builder, PmDamageTool.FourColorType defaultDamageType) {
		this(builder.build(), builder, defaultDamageType);
	}

	public CloseEgoWeapon(Properties properties, Weapon.Builder builder, PmDamageTool.FourColorType defaultDamageType) {
		super(properties.component(CURRENT_DAMAGE_TYPE, defaultDamageType.getLocationString()), builder);
	}

	protected void setDamageType(ItemStack itemStack, PmDamageTool.FourColorType damageType) {
		itemStack.set(CURRENT_DAMAGE_TYPE, damageType.getLocationString());
	}

	/**
	 * 判断是否为近战EGO物品
	 */
	public static boolean isCloseCombatEgo(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem() instanceof CloseEgoWeapon;
	}
}
