package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.common.item.weapon.abstract_ltem.EgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.List;

/** 熊熊抱武器 */
public class BearPawsItem extends EgoWeapon implements IInvincibleTickItem {
	public BearPawsItem(Weapon.Builder builder) {
		super(builder);
	}

	@Override
	public int getInvincibleTick(ItemStack stack) {
		return 10;
	}

	@CheckForNull
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.PHYSICS);
	}
}
