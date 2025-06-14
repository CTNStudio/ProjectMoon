package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.item.weapon.abstract_ltem.EgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.List;

/// 割腕者
public class WristCutterItem extends EgoWeapon {
	public WristCutterItem(Weapon.Builder builder) {
		super(builder, PmDamageTool.ColorType.SPIRIT);
		setDefaultModel(new PmGeoItemModel<>("wrist_cutter"));
	}

	@Override
	public int getInvincibleTick(ItemStack stack) {
		return 15;
	}


	@CheckForNull
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.SPIRIT);
	}
}
