package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.item.ItemStack;

import static ctn.project_moon.init.PmDamageTypes.getDamageTypeLocation;
import static ctn.project_moon.init.PmItemDataComponents.CURRENT_DAMAGE_TYPE;

/**
 * 近战EGO武器
 */
public abstract class CloseEgoWeapon extends EgoWeapon {
	public CloseEgoWeapon(Weapon.Builder builder, PmDamageTypes.Types defaultDamageType) {
		this(builder.build(), builder, defaultDamageType);
	}

	public CloseEgoWeapon(Properties properties, Weapon.Builder builder, PmDamageTypes.Types defaultDamageType) {
		super(properties.component(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(defaultDamageType)), builder);
	}

	protected void setDamageType(ItemStack itemStack, PmDamageTypes.Types damageType) {
		itemStack.set(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(damageType));
	}

	/**
	 * 判断是否为近战EGO物品
	 */
	public static boolean isCloseCombatEgo(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem() instanceof CloseEgoWeapon;
	}
}
