package ctn.project_moon.common.renderers;

import ctn.project_moon.common.models.PmGeoArmorModel;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * 盔甲渲染
 */
public class PmGeoArmorRenderer<T extends Item & GeoItem> extends GeoArmorRenderer<T> {
	public PmGeoArmorRenderer(PmGeoArmorModel<T> model) {
		super(model);
	}
}
