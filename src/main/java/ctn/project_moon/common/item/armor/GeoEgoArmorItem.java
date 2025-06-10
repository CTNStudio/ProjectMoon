package ctn.project_moon.common.item.armor;

import ctn.project_moon.client.renderer_providers.PmGeoArmourRenderProvider;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class GeoEgoArmorItem extends EgoArmorItem implements GeoItem {
	protected final PmGeoArmourRenderProvider.GeoBuilder<GeoEgoArmorItem> geoBuilder;
	private final   AnimatableInstanceCache                               cache = GeckoLibUtil.createInstanceCache(this);

	public GeoEgoArmorItem(Builder builder, PmGeoArmourRenderProvider.GeoBuilder<GeoEgoArmorItem> geoBuilder) {
		super(builder);
		this.geoBuilder = geoBuilder;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new PmGeoArmourRenderProvider<>(geoBuilder));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}