package ctn.project_moon.common.models;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmGeoItemModel<T extends GeoAnimatable> extends GeoModel<T> {
    private String path;

    public PmGeoItemModel(String path) {
        this.path = path;
    }

    public PmGeoItemModel() {
    }

    public static ResourceLocation fromNamespaceAndPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation modelPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/item/" + path + ".geo.json");
    }

    public static ResourceLocation texturePath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/geo/" + path + ".png");
    }

    public static ResourceLocation animationsPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "animations/" + path + ".json");
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return modelPath(path);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texturePath(path);
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animationsPath(path);
    }
}
