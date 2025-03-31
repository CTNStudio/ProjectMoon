package ctn.project_moon.common.items.ego_weapon;

import ctn.project_moon.common.items.EgoWeaponItem;

import static ctn.project_moon.create.PmDamageSources.theSoulDamage;

public class ParadiseLostItem extends EgoWeaponItem {
    public ParadiseLostItem(Properties properties) {
        super(properties, theSoulDamage());
    }
}
