package ctn.project_moon.client.renderer_providers;

import ctn.project_moon.client.renderers.PmGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;

/** 物品渲染提供程序 */
public class PmGeoItemRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
	protected final GeoModel<T>          defaultModel;
	protected final GeoModel<T>          guiModel;
	private         PmGeoItemRenderer<T> renderer;
	
	public PmGeoItemRenderProvider(GeoModel<T> defaultModel, GeoModel<T> guiModel) {
		this.defaultModel = defaultModel;
		this.guiModel     = guiModel;
	}
	
	@Override
	public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
		if (this.renderer == null) {
			this.renderer = new PmGeoItemRenderer<>(defaultModel, guiModel);
		}
		
		return this.renderer;
	}
}
