package ctn.project_moon.common.item.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.project_moon.PmMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.CheckForNull;
import java.util.Objects;

public class WingOfParadiseLost extends CuriosItem implements ICurioRenderer, GeoAnimatable {
    // 创建目标模型
    private static final GeoModel<WingOfParadiseLost> model =
            new DefaultedGeoModel<>(ResourceLocation.fromNamespaceAndPath(PmMain.MOD_ID, "wing_of_paradise_lost")) {
                @Override
                protected String subtype() {
                    // 对应纹理文件夹，此处就是 textures/curios/wing_of_paradise_lost.png
                    return "curios";
                }
            };

    // 渲染器，不要预实例化，留 null，打标，因为客户端实例的比较早，此时还没有模型
    @CheckForNull
    private static GeoArmorRenderer<WingOfParadiseLost> renderer;

    public WingOfParadiseLost() {
        super(10, 10, 10, 10);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>>
    void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        // 用的时候把渲染器实例化
        if (Objects.isNull(renderer)) {
            renderer = new GeoArmorRenderer<>(model);
        }

        // 预推送当前渲染状态机
        renderer.prepForRender(Minecraft.getInstance().player, stack, EquipmentSlot.BODY,
                (HumanoidModel<?>) renderLayerParent.getModel(), renderTypeBuffer, partialTicks,
                limbSwing, limbSwingAmount, netHeadYaw, headPitch);

        // 开始渲染，buffer 位留空就行，渲染的时候会自动生成，不需要管
        renderer.defaultRender(matrixStack, this, renderTypeBuffer, RenderType.CUTOUT, null, netHeadYaw, partialTicks, light);
    }
}
