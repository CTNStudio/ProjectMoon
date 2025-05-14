package ctn.project_moon.common.item.weapon.abstract_ltem;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/** 特殊EGO武器 */
public abstract class SpecialEgoWeapon extends EgoWeapon {
	public SpecialEgoWeapon(Properties properties, Builder builder) {
		super(properties.component(IS_RESTRAIN, false), true, builder);
	}

}
