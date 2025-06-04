package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;

public class BearPawsItem extends CloseEgoWeapon implements SetInvulnerabilityTick {
	public BearPawsItem(Weapon.Builder builder) {
		super(builder, PmDamageTool.FourColorType.PHYSICS);
	}

	@Override
	public int getTicks() {
		return 10;
	}
}
