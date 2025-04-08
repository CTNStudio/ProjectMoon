package ctn.project_moon.common.item.weapon;

import ctn.project_moon.common.item.EgoCloseCombat;
import ctn.project_moon.common.item.SetInvulnerabilityTicks;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;


public class WristCutterItem extends EgoCloseCombat implements SetInvulnerabilityTicks {
    public WristCutterItem(Properties properties, float maxDamage, float minDamage, float attackSpeed) {
        super(properties, PmDamageTypes.Types.SPIRIT, maxDamage, minDamage, attackSpeed);
        setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
    }

    @Override
    public int getTicks() {
        return 14;
    }
}
