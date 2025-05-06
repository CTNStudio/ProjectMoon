package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.Ego;
import ctn.project_moon.common.item.weapon.Weapon;

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;


public class EgoWeapon extends Weapon implements Ego{
    public EgoWeapon(Weapon.Builder builder) {
        this(builder, false);
    }

    public EgoWeapon(Weapon.Builder builder, boolean isSpecialTemplate) {
        this(builder.build(), isSpecialTemplate, builder);
    }

    public EgoWeapon(Properties properties, Weapon.Builder builder) {
        super(properties.component(IS_RESTRAIN, false), builder);
    }

    public EgoWeapon(Properties properties, boolean isSpecialTemplate, Weapon.Builder builder) {
        super(properties.component(IS_RESTRAIN, false), isSpecialTemplate, builder);
    }
}
