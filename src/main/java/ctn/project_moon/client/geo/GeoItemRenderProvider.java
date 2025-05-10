package ctn.project_moon.client.geo;

import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.renderers.PmGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;

public class GeoItemRenderProvider implements GeoRenderProvider {
	private PmGeoItemRenderer<Weapon> renderer;
	protected final GeoModel<Weapon> defaultModel;
	protected final GeoModel<Weapon> guiModel;

	public GeoItemRenderProvider(GeoModel<Weapon> defaultModel, GeoModel<Weapon> guiModel) {
		this.defaultModel = defaultModel;
		this.guiModel = guiModel;
	}

	@Override
	public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
		if (this.renderer == null) {
			this.renderer = new PmGeoItemRenderer<>(defaultModel, guiModel);
		}

		return this.renderer;
	}
}
