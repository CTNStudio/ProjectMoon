package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageSources;

import static ctn.project_moon.init.PmDamageSources.spiritDamage;


public class WristCutterItem extends EgoWeaponItem {
    public WristCutterItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, egoAttribute.damageType(spiritDamage()));
        setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
    }
}
