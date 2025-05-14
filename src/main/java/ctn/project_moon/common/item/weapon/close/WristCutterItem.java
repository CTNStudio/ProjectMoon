package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;


public class WristCutterItem extends CloseEgoWeapon implements SetInvulnerabilityTick {
	public WristCutterItem(Weapon.Builder builder) {
		super(builder, PmDamageTypes.Types.SPIRIT);
		setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
	}

	@Override
	public int getTicks() {
		return 15;
	}
}
