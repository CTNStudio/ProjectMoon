package ctn.project_moon.client.renderers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/** 一个特殊的物品渲染可在GUI和世界中以不同的模型渲染 */
public class PmGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
	private final GeoModel<T> guiModel;
	
	public PmGeoItemRenderer(GeoModel<T> model, GeoModel<T> guiModel) {
		super(model);
		this.guiModel = guiModel;
	}
	
	public PmGeoItemRenderer(GeoModel<T> model) {
		super(model);
		this.guiModel = null;
	}
	
	@Override
	public GeoModel<T> getGeoModel() {
		return guiModel != null && renderPerspective == ItemDisplayContext.GUI ? guiModel : model;
	}
}
