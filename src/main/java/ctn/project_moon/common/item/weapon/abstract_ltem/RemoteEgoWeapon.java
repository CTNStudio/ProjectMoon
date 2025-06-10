package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.capability.item.IEgoItem;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeapon extends RemoteWeapon implements IEgoItem {
	public RemoteEgoWeapon(Properties properties, Builder builder, boolean isConsumingBullets) {
		super(properties.component(IS_RESTRAIN, false), builder, isConsumingBullets);
	}
}
