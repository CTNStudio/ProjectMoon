package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.client.models.PmGeoEntityModel;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TrainingRabbits extends AbnosEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TrainingRabbits(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createAbnosAttributes()
				.add(PmEntityAttributes.THE_SOUL_RESISTANCE, 1.0)
				.add(PmEntityAttributes.EROSION_RESISTANCE, 1.0);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static class TrainingRabbitsRenderer extends GeoEntityRenderer<TrainingRabbits> {
		public TrainingRabbitsRenderer(EntityRendererProvider.Context context) {
			super(context, new PmGeoEntityModel<>("training_rabbits"));
		}

		@Override
		public @NotNull ResourceLocation getTextureLocation(@NotNull TrainingRabbits animatable) {
			return PmGeoEntityModel.texturePath("training_rabbits");
		}
	}
}
