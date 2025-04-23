package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.tool.GradeTypeTool;
import ctn.project_moon.common.models.PmGeoEntityModel;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TrainingRabbits extends AbnosEntity{
    private final AnimatableInstanceCache ANIMS = GeckoLibUtil.createInstanceCache(this);

    public TrainingRabbits(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level, GradeTypeTool.Level.ZAYIN);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return createAbnosAttributes()
                .add(PmAttributes.THE_SOUL_RESISTANCE, 1.0)
                .add(PmAttributes.EROSION_RESISTANCE, 1.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return ANIMS;
    }

    public static class TrainingRabbitsRenderer extends GeoEntityRenderer<TrainingRabbits> {
        public TrainingRabbitsRenderer(EntityRendererProvider.Context context) {
            super(context, new PmGeoEntityModel<>("training_rabbits"));
        }

        @Override
        public ResourceLocation getTextureLocation(TrainingRabbits animatable) {
            return PmGeoEntityModel.texturePath("training_rabbits");
        }
    }
}
