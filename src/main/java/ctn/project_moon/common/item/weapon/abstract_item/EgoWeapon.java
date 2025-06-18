package ctn.project_moon.common.item.weapon.abstract_item;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.item.IEgoItem;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * EGO武器
 */
public abstract class EgoWeapon extends Weapon implements IEgoItem {
	public EgoWeapon(Weapon.Builder builder) {
		this(builder.build(), builder);
	}
	
	public EgoWeapon(Weapon.Builder builder, PmDamageTool.ColorType initialColorDamageType) {
		this(builder.build(), builder, initialColorDamageType);
	}
	
	public EgoWeapon(Properties properties, Weapon.Builder builder) {
		super(properties.component(IS_RESTRAIN, false), builder);
	}
	
	public EgoWeapon(Properties properties, Weapon.Builder builder, PmDamageTool.ColorType initialColorDamageType) {
		super(properties.component(IS_RESTRAIN, false), builder, initialColorDamageType);
	}
}
