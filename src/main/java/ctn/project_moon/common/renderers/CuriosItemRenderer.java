package ctn.project_moon.common.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.project_moon.common.item.curio.EgoCurioItem;
import ctn.project_moon.common.models.GeoCurioModel;
import ctn.project_moon.datagen.DatagenCuriosTest;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosItemRenderer implements ICurioRenderer {
    protected final AnimatableInstanceCache        cache;
    protected final GeoArmorRenderer<EgoCurioItem> renderer;
    protected final GeoCurioModel<EgoCurioItem>    model;
    protected final BakedGeoModel                  bakedGeoModel;
    protected final GeoModel<EgoCurioItem>         geoModel;
    protected final EgoCurioItem                   animatableItem;
    protected final RenderType                     type = RenderType.CUTOUT;

    public CuriosItemRenderer(EgoCurioItem curioItem) {
        cache = curioItem.getAnimatableInstanceCache();
        model = curioItem.getModel();
        renderer = new GeoArmorRenderer<>(model);
        geoModel = renderer.getGeoModel();
        animatableItem = curioItem;
        bakedGeoModel = geoModel.getBakedModel(geoModel.getModelResource(animatableItem, renderer));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>>
    void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        EquipmentSlot slot = switch (slotContext.identifier()) {
            case DatagenCuriosTest.HEADWEAR_CURIOS,
                 DatagenCuriosTest.HEAD_CURIOS,
                 DatagenCuriosTest.HINDBRAIN_CURIOS,
                 DatagenCuriosTest.EYE_AREA_CURIOS,
                 DatagenCuriosTest.FACE_CURIOS,
                 DatagenCuriosTest.CHEEK_CURIOS,
                 DatagenCuriosTest.MASK_CURIOS,
                 DatagenCuriosTest.MOUTH_CURIOS -> EquipmentSlot.HEAD;
            case DatagenCuriosTest.NECK_CURIOS,
                 DatagenCuriosTest.CHEST_CURIOS,
                 DatagenCuriosTest.RIGHT_BACK_CURIOS,
                 DatagenCuriosTest.LEFT_BACK_CURIOS -> EquipmentSlot.CHEST;
            case DatagenCuriosTest.HAND_CURIOS,
                 DatagenCuriosTest.GLOVE_CURIOS-> EquipmentSlot.MAINHAND;
	        default -> EquipmentSlot.BODY;
        };

        // 预推送当前渲染状态机
        renderer.prepForRender(slotContext.entity(), stack, slot, (HumanoidModel<?>) renderLayerParent.getModel(), renderTypeBuffer, partialTicks, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
        VertexConsumer consumer = renderTypeBuffer.getBuffer(type);
        // 进行渲染
        renderer.defaultRender(matrixStack, animatableItem, renderTypeBuffer, null, null, netHeadYaw, partialTicks, light);
    }
}
