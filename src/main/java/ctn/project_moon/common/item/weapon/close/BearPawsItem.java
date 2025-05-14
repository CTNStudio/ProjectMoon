package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.common.item.weapon.abstract_ltem.CloseEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import ctn.project_moon.init.PmDamageTypes;

public class BearPawsItem extends CloseEgoWeapon implements SetInvulnerabilityTick {
	public BearPawsItem(Weapon.Builder builder) {
		super(builder, PmDamageTypes.Types.PHYSICS);
	}

	@Override
	public int getTicks() {
		return 10;
	}
}
