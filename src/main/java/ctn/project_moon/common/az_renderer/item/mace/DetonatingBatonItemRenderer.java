package ctn.project_moon.common.az_renderer.item.mace;

import ctn.project_moon.common.az_animators.item.mace.DetonatingBatonItemAnimator;
import net.minecraft.resources.ResourceLocation;

public class DetonatingBatonItemRenderer extends MaceItemRenderer{
    public static final ResourceLocation MODEL = MaceItemRenderer.modelPath("detonating_baton");
    public static final ResourceLocation TEXTURE = MaceItemRenderer.texturePath("detonating_baton");

    public DetonatingBatonItemRenderer() {
        super(MODEL, TEXTURE, DetonatingBatonItemAnimator::new);
    }
}
