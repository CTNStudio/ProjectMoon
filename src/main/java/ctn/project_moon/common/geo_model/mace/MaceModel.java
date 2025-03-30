package ctn.project_moon.common.geo_model.mace;

import ctn.project_moon.common.geo_model.EgoWeaponItemGeoModel;
import net.minecraft.resources.ResourceLocation;


public abstract class MaceModel extends EgoWeaponItemGeoModel {
    public static ResourceLocation modelPath(String path) {
        return EgoWeaponItemGeoModel.modelPath("mace/" + path);
    }

    public static ResourceLocation texturePath(String path) {
        return EgoWeaponItemGeoModel.texturePath("mace/" + path);
    }

    public static ResourceLocation animationsPath(String path) {
        return EgoWeaponItemGeoModel.animationsPath("mace/" + path);
    }
}
