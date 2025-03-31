package ctn.project_moon.common.items.ego_weapon;

import ctn.project_moon.common.items.EgoWeaponItem;

import static ctn.project_moon.create.PmDamageSources.spiritDamage;

public class WristCutterItem extends EgoWeaponItem {
    public WristCutterItem(Properties properties) {
        super(properties, spiritDamage());
    }
}
