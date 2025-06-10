package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.capability.item.IEgoItem;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * EGO武器
 */
public abstract class EgoWeapon extends Weapon implements IEgoItem {
	public EgoWeapon(Properties properties, Weapon.Builder builder) {
		super(properties.component(IS_RESTRAIN, false), builder);
	}

	public EgoWeapon(Weapon.Builder builder) {
		this(builder.build(), builder);
	}
}
