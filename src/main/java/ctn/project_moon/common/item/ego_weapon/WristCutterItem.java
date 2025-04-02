package ctn.project_moon.common.item.ego_weapon;

import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.init.PmDamageSources;


public class WristCutterItem extends EgoWeaponItem {
    public WristCutterItem(Properties properties) {
        super(properties, PmDamageSources.spiritDamage());
    }
}
