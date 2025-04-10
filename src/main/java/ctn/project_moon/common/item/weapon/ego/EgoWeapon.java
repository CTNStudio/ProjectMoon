package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.Ego;

import static ctn.project_moon.init.PmDataComponents.IS_RESTRAIN;


public abstract class EgoWeapon extends ctn.project_moon.common.item.weapon.Weapon implements Ego{
    public EgoWeapon(ctn.project_moon.common.item.weapon.Weapon.Builder builder) {
        this(builder, false);
    }

    public EgoWeapon(ctn.project_moon.common.item.weapon.Weapon.Builder builder, boolean isSpecialTemplate) {
        this(builder.build(), isSpecialTemplate, builder);
    }

    public EgoWeapon(Properties properties, ctn.project_moon.common.item.weapon.Weapon.Builder builder) {
        super(properties.component(IS_RESTRAIN, false), builder);
    }

    public EgoWeapon(Properties properties, boolean isSpecialTemplate, ctn.project_moon.common.item.weapon.Weapon.Builder builder) {
        super(properties.component(IS_RESTRAIN, false), isSpecialTemplate, builder);
    }
}
