package ctn.project_moon.common.item.ego_weapon;

import ctn.project_moon.common.item.EgoWeaponItem;

import static ctn.project_moon.init.PmDamageSources.physicsDamage;

public class BearPawsItem extends EgoWeaponItem {
    public BearPawsItem(Properties properties) {
        super(properties, physicsDamage());
    }
}
