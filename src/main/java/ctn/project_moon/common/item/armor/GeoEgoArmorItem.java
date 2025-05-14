package ctn.project_moon.common.item.armor;

import ctn.project_moon.client.geo.PmGeoArmourRenderProvider;
import ctn.project_moon.common.models.PmGeoArmorModel;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GeoEgoArmorItem extends EgoArmorItem implements GeoItem {
	protected final Supplier<Item> boots;
	protected final Supplier<Item> leggings;
	protected final Supplier<Item> chestplate;
	protected final Supplier<Item> helmet;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final PmGeoArmorModel<GeoEgoArmorItem> model;

	public GeoEgoArmorItem(Builder builder, PmGeoArmorModel<GeoEgoArmorItem> model) {
		this(builder, model, null, null, null, null);
	}

	public GeoEgoArmorItem(Builder builder, PmGeoArmorModel<GeoEgoArmorItem> model, Supplier<Item> boots, Supplier<Item> leggings, Supplier<Item> chestplate, Supplier<Item> helmet) {
		super(builder);
		this.boots      = boots;
		this.leggings   = leggings;
		this.chestplate = chestplate;
		this.helmet     = helmet;
		this.model      = model;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new PmGeoArmourRenderProvider<>(model));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}