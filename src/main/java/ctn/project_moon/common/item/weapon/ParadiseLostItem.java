package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.EgoWeaponItem;

import static ctn.project_moon.init.PmDamageSources.theSoulDamage;

public class ParadiseLostItem extends EgoWeaponItem {
    public ParadiseLostItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, egoAttribute.damageType(theSoulDamage()));
    }
}
