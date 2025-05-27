package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;


public class WristCutterItem extends CloseEgoWeapon implements SetInvulnerabilityTick {
	public WristCutterItem(Weapon.Builder builder) {
		super(builder, PmDamageTool.FourColorType.SPIRIT);
		setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
	}

	@Override
	public int getTicks() {
		return 15;
	}
}
