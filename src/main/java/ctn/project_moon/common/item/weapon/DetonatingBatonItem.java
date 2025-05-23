package ctn.project_moon.common.item.weapon;


import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;

public class DetonatingBatonItem extends Weapon {
	public DetonatingBatonItem(Weapon.Builder builder) {
		super(builder);
		setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
	}
}
