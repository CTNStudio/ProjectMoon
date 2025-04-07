package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;


public class WristCutterItem extends EgoCloseCombat {
    public WristCutterItem(Properties properties, EgoAttribute egoAttribute) {
//        super(properties,PmDamageTypes.Types.PHYSICS, egoAttribute);
        super(properties, PmDamageTypes.Types.SPIRIT, egoAttribute);
//        super(properties,PmDamageTypes.Types.EROSION, egoAttribute);
//        super(properties,PmDamageTypes.Types.THE_SOUL, egoAttribute);
        setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
    }
}
