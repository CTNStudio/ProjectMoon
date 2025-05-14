package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.common.item.Ego;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * EGO武器
 */
public abstract class EgoWeapon extends Weapon implements Ego {
	public EgoWeapon(Properties properties, Weapon.Builder builder) {
		super(properties.component(IS_RESTRAIN, false), builder);
	}

	public EgoWeapon(Properties properties, boolean isSpecialTemplate, Weapon.Builder builder) {
		super(properties.component(IS_RESTRAIN, false), isSpecialTemplate, builder);
	}
}
