package ctn.project_moon.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.project_moon.common.entity.projectile.MagicBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static ctn.project_moon.PmMain.MOD_ID;

public class MagicBulletEntityRenderer extends EntityRenderer<MagicBulletEntity> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/magic_bullet.png");
	
	public MagicBulletEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public void render(MagicBulletEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
	
	}
	
	@Override
	public ResourceLocation getTextureLocation(MagicBulletEntity magicBulletEntity) {
		return TEXTURE;
	}
}
