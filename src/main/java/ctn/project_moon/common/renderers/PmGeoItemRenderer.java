package ctn.project_moon.common.renderers;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PmGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
    public PmGeoItemRenderer(GeoModel<T> model) {
        super(model);
    }
}
