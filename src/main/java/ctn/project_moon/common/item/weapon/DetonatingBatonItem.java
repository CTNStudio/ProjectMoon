package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;

public class DetonatingBatonItem extends EgoCloseCombat {

    public DetonatingBatonItem(Properties properties, EgoAttribute egoAttribute) {
        super(properties, PmDamageTypes.Types.PHYSICS, egoAttribute);
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
