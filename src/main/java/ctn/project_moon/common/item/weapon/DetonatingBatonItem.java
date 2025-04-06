package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.common.models.item.DetonatingBatonModel;
import ctn.project_moon.init.PmDamageTypes;

import static ctn.project_moon.init.PmDamageSources.physicsDamage;

public class DetonatingBatonItem extends EgoCloseCombat {

    public DetonatingBatonItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, PmDamageTypes.Types.PHYSICS, egoAttribute);
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
