package ctn.project_moon.common.item.weapon.abstract_ltem;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeapon extends RemoteWeapon {
	public RemoteEgoWeapon(Properties properties, Builder builder, boolean isConsumingBullets) {
		super(properties.component(IS_RESTRAIN, false), builder, isConsumingBullets);
	}
}
