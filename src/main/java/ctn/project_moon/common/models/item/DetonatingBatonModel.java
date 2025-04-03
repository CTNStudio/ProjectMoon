package ctn.project_moon.common.models.item;

import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.common.item.weapon.DetonatingBatonItem;
import ctn.project_moon.common.models.PmGeoItemModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class DetonatingBatonModel extends PmGeoItemModel<EgoWeaponItem> {
    public DetonatingBatonModel(String path){
        super(path);
    }
}
