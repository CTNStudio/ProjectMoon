package ctn.project_moon.common.item.weapon.remote;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.client.models.GuiItemModel;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.common.item.RequestItem;
import ctn.project_moon.common.item.components.ItemColorUsageReq;
import ctn.project_moon.common.item.weapon.abstract_ltem.RemoteEgoWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;

public class MagicBullet extends RemoteEgoWeapon implements RequestItem {
	public MagicBullet(Builder builder) {
		super(builder.build().component(ITEM_COLOR_USAGE_REQ,
				ItemColorUsageReq.empty().setValue(FourColorAttribute.Type.TEMPERANCE, FourColorAttribute.Rating.III)),
				builder, false);
		setDefaultModel(new PmGeoItemModel<>("magic_bullet"));
		setGuiModel(new GuiItemModel<>("magic_bullet"));
	}

	@Override
	public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {}

	@Override
	public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {}

	@Override
	public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {}

	@Override
	public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {}

	@Override
	public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {}
}
