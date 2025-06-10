package ctn.project_moon.common.item.weapon;


import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;

import javax.annotation.CheckForNull;
import java.util.List;

/// 镇爆棍
public class DetonatingBatonItem extends Weapon {
	public DetonatingBatonItem(Weapon.Builder builder) {
		super(builder);
		setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
	}

	@CheckForNull
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.PHYSICS);
	}
}
