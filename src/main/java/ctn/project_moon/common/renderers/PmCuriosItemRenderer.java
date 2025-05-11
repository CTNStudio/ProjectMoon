package ctn.project_moon.common.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.project_moon.common.item.curios.CuriosItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmCuriosItemRenderer implements ICurioRenderer {
    private final String name ;
    private final GeoModel<CuriosItem> model;
    private final ResourceLocation TEXTURE;

    //TODO: 添加饰品模型
    public PmCuriosItemRenderer(String name){
        this.name = name;
        this.model = new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(MOD_ID,"geo/"+name+".geo.json"));
        this.TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID,"textures/item/"+name+"_geo_model.png");
    }
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slotContext.visible()){
//            HumanoidModel<?> humanoidModel = (HumanoidModel<?>) renderLayerParent.getModel();
//            matrixStack.pushPose();
//            humanoidModel.body.translateAndRotate(matrixStack);
//            matrixStack.translate(0.0D, 0.0D, 0.125D);
//            matrixStack.scale(1.0F, 1.0F, 1.0F);
        }
    }
}
