package ctn.project_moon.common.client.geo_models;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import static ctn.project_moon.PmMain.MOD_ID;

public abstract class EgoWeaponItemGeoModel extends GeoModel<GeoAnimatable> {
    @Override
    public abstract ResourceLocation getModelResource(GeoAnimatable animatable);

    @Override
    public abstract ResourceLocation getTextureResource(GeoAnimatable animatable);

    @Override
    public abstract ResourceLocation getAnimationResource(GeoAnimatable animatable);

    private static ResourceLocation fromNamespaceAndPath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation modelPath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/" + path + ".geo.json");
    }

    public static ResourceLocation texturePath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/geo/" + path + ".png");
    }

    public static ResourceLocation animationsPath(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "animations/" + path + ".json");
    }
}
