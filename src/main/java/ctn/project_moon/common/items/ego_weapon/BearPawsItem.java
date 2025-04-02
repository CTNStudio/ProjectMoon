package ctn.project_moon.common.items.ego_weapon;

import ctn.project_moon.common.items.EgoWeaponItem;

import static ctn.project_moon.create.PmDamageSources.physicsDamage;

public class BearPawsItem extends EgoWeaponItem {
    public BearPawsItem(Properties properties) {
        super(properties, physicsDamage());
    }
}
