package ctn.project_moon.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ResourceManagerReloadListener {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void projectMoon$render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci) {
		if (itemStack.isEmpty() && displayContext != ItemDisplayContext.GUI && itemStack.getItem() instanceof Weapon) {
		}
	}
}
