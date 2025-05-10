package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;


public class WristCutterItem extends CloseCombatEgo implements SetInvulnerabilityTick {
	public WristCutterItem(Weapon.Builder builder) {
		super(builder, PmDamageTypes.Types.SPIRIT);
		setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
	}

	@Override
	public int getTicks() {
		return 15;
	}
}
