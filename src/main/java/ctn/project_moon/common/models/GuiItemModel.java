package ctn.project_moon.common.models;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;

import static ctn.project_moon.PmMain.MOD_ID;

public class GuiItemModel<T extends GeoAnimatable> extends PmGeoItemModel<T>{
    public GuiItemModel(String path) {
        super(path);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return modelPath("gui_item_model");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/item/" + path + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return super.getAnimationResource(animatable);
    }
}
