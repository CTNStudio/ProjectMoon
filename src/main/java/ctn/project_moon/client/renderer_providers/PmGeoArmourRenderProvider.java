package ctn.project_moon.client.renderer_providers;

import ctn.project_moon.client.models.PmGeoArmorModel;
import ctn.project_moon.client.renderers.PmGeoArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/** 盔甲渲染提供程序 */
public class PmGeoArmourRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
	private         PmGeoArmorRenderer<T> renderer;
	protected final PmGeoArmorModel<T>    roughHandModel;
	protected final PmGeoArmorModel<T>    fineHandedModel;



	public PmGeoArmourRenderProvider(GeoBuilder<T> builder) {
		this(builder.roughHandModel, builder.fineHandModel, builder.renderer);
	}

	public PmGeoArmourRenderProvider(PmGeoArmorModel<T> roughHandModel, PmGeoArmorModel<T> fineHandedModel, PmGeoArmorRenderer<T> renderer) {
		this.roughHandModel  = roughHandModel;
		this.fineHandedModel = fineHandedModel;
		this.renderer        = renderer;
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public <E extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity,
	                                                                     ItemStack itemStack,
	                                                                     @Nullable EquipmentSlot equipmentSlot,
	                                                                     @Nullable HumanoidModel<E> original) {
//		original.body
		if (this.renderer == null) {
			if (fineHandedModel == null) {
				this.renderer = new PmGeoArmorRenderer<>(roughHandModel);
			}
		}
		return this.renderer;
	}

	public static class GeoBuilder<T extends Item & GeoItem> {
		private PmGeoArmorRenderer<T> renderer;
		private PmGeoArmorModel<T> roughHandModel;
		private PmGeoArmorModel<T> fineHandModel;
		private Supplier<Item>     boots;
		private Supplier<Item>     leggings;
		private Supplier<Item> chestplate;
		private Supplier<Item> helmet;

		public PmGeoArmorRenderer<T> renderer() {
			return renderer;
		}

		public GeoBuilder<T> renderer(PmGeoArmorRenderer<T> renderer) {
			this.renderer = renderer;
			return this;
		}

		public PmGeoArmorModel<T> roughHandModel() {
			return roughHandModel;
		}

		public GeoBuilder<T> roughHandModel(PmGeoArmorModel<T> roughHandModel) {
			this.roughHandModel = roughHandModel;
			return this;
		}

		public GeoBuilder<T> roughHandModel(String roughHandModelName) {
			this.roughHandModel = new PmGeoArmorModel<>(roughHandModelName);
			return this;
		}

		public PmGeoArmorModel<T> fineHandModel() {
			return fineHandModel;
		}

		public GeoBuilder<T> fineHandModel(PmGeoArmorModel<T> fineHandModel) {
			this.fineHandModel = fineHandModel;
			return this;
		}

		public GeoBuilder<T> fineHandModel(String fineHandModelName) {
			this.fineHandModel = new PmGeoArmorModel<>(fineHandModelName);
			return this;
		}

		public Supplier<Item> boots() {
			return boots;
		}

		public GeoBuilder<T> boots(Supplier<Item> boots) {
			this.boots = boots;
			return this;
		}

		public Supplier<Item> leggings() {
			return leggings;
		}

		public GeoBuilder<T> leggings(Supplier<Item> leggings) {
			this.leggings = leggings;
			return this;
		}

		public Supplier<Item> chestplate() {
			return chestplate;
		}

		public GeoBuilder<T> chestplate(Supplier<Item> chestplate) {
			this.chestplate = chestplate;
			return this;
		}

		public Supplier<Item> helmet() {
			return helmet;
		}

		public GeoBuilder<T> helmet(Supplier<Item> helmet) {
			this.helmet = helmet;
			return this;
		}
	}
}
