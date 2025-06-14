package ctn.project_moon.common.item.weapon.special;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.common.item.weapon.abstract_ltem.EgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;

import javax.annotation.CheckForNull;
import java.util.List;

/** 以爱与恨之名 */
public class LoveHateItem extends EgoWeapon {
	public LoveHateItem(Weapon.Builder builder) {
		super(builder.build(), builder);
		setItemModel("love_hate");
	}

	@CheckForNull
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.values());
	}
}
