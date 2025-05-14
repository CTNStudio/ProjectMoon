package ctn.project_moon.client.geo;

import ctn.project_moon.common.models.PmGeoArmorModel;
import ctn.project_moon.common.renderers.PmGeoArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import javax.annotation.Nullable;

public class PmGeoArmourRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
	private         PmGeoArmorRenderer<T> renderer;
	protected final PmGeoArmorModel<T>    defaultModel;

	public PmGeoArmourRenderProvider(PmGeoArmorModel<T> defaultModel) {
		this.defaultModel = defaultModel;
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public <E extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity,
	                                                                     ItemStack itemStack,
	                                                                     @Nullable EquipmentSlot equipmentSlot,
	                                                                     @Nullable HumanoidModel<E> original) {
		if (this.renderer == null) {
			this.renderer = new PmGeoArmorRenderer<>(defaultModel);
		}
		return this.renderer;
	}
}
