package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.common.models.item.DetonatingBatonModel;

import static ctn.project_moon.init.PmDamageSources.physicsDamage;

public class DetonatingBatonItem extends EgoWeaponItem {

    public DetonatingBatonItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, egoAttribute.damageType(physicsDamage()));
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
