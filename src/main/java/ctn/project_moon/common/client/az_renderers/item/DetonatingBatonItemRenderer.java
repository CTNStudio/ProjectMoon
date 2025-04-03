package ctn.project_moon.common.client.az_renderers.item;

import ctn.project_moon.common.client.az_animators.item.DetonatingBatonItemAnimator;
import net.minecraft.resources.ResourceLocation;

public class DetonatingBatonItemRenderer extends PmAzItemRenderer {
    public static final ResourceLocation MODEL = modelPath("detonating_baton");
    public static final ResourceLocation TEXTURE = texturePath("detonating_baton");

    public DetonatingBatonItemRenderer() {
        super(MODEL, TEXTURE, DetonatingBatonItemAnimator::new);
    }
}
