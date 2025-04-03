package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.EgoWeaponItem;
import software.bernie.geckolib.model.GeoModel;

import static ctn.project_moon.init.PmDamageSources.physicsDamage;

public class BearPawsItem extends EgoWeaponItem {
    public BearPawsItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, egoAttribute.damageType(physicsDamage()));
    }
}
