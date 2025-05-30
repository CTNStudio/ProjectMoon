package ctn.project_moon.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.tool.PmTool;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import static ctn.project_moon.init.PmParticleTypes.DAMAGE_PARTICLE_TYPE;
import static net.minecraft.world.damagesource.DamageTypes.GENERIC;

// TODO 优化伤害显示粒子
/**
 * 本文件参考汇流来世的伤害显示粒子制作
 */
@OnlyIn(Dist.CLIENT)
public class DamageParticle extends TextureSheetParticle {
	private final Component        text;
	private final ResourceLocation damageTypeId;
	private final float            maxSize;
	private final float            maxTick;
//	private final int              color;
//	private final int              backgroundColor;

	protected DamageParticle(ClientLevel level, double x, double y, double z, Component text, ResourceLocation damageTypeId) {
		super(level, x, y, z);
		this.text         = text;
		this.damageTypeId = damageTypeId;
		maxSize = 0.05f;
		maxTick = 20 * 4;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	@Override
	public void render(@NotNull VertexConsumer pBuffer, Camera camera, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
		Vec3 camPos = camera.getPosition();
		double dx = this.x - camPos.x;
		double dy = Mth.lerp(pPartialTicks, yo, y) - camPos.y;
		double dz = this.z - camPos.z;
		PoseStack poseStack = new PoseStack();
		poseStack.pushPose();
		poseStack.translate(dx, dy, dz);
		poseStack.mulPose(camera.rotation());
		poseStack.mulPose(Axis.XP.rotationDegrees(180));

		// 将原来的 f 计算替换成：
		float partialAge = (age + pPartialTicks); // 使用 age + 插值时间
		final float duration = 10.0f; // 控制动画总帧数
		final float t = Mth.clamp(partialAge / duration, 0.0f, 1.0f);
		final float f = Mth.lerp(t, 0.0f, maxSize);

		poseStack.scale(f, f, f);  // 文本大小
		int width = minecraft.font.width(text);
		Matrix4f matrix = new Matrix4f(poseStack.last().pose());
		matrix.translate(0, 0, 0.03f);
		minecraft.font.drawInBatch(
				text, -width / 2f, 0, PmTool.colorConversion("#ff0000"), true,
				matrix, bufferSource, Font.DisplayMode.NORMAL, PmTool.colorConversion("#ff0000"), PmTool.colorConversion("#ff0000"));
		matrix.translate(0, 0, 0.03f);
		bufferSource.endBatch();
		poseStack.popPose();
	}

	private static double smoothEntryFactor(double tickIn) {
		return Math.pow(Math.pow((-2 + tickIn), 2) * tickIn, 2);
	}

	@Override
	public void tick() {
		age++;
		if (age > maxTick) {
			remove();
		}
	}

	@Override
	public void remove() {
		super.remove();
	}

	/** 粒子提供者*/
	public static class Provider implements ParticleProvider<Options> {
		@Override
		public @Nullable Particle createParticle(
				Options type, @NotNull ClientLevel level,
				double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			var id = type.damageTypeId().split(":");
			return new DamageParticle(level, x, y, z, type.component(), ResourceLocation.fromNamespaceAndPath(id[0], id[1]));
		}
	}

	/** 粒子参数*/
	@OnlyIn(Dist.CLIENT)
	public record Options(Component component, String damageTypeId) implements ParticleOptions {
		public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec(
				(thisOptionsInstance) -> thisOptionsInstance.group(
						ComponentSerialization.CODEC.fieldOf("component").forGetter(Options::component),
						Codec.STRING.fieldOf("damage_type").forGetter(Options::damageTypeId)
				).apply(thisOptionsInstance, Options::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
				ComponentSerialization.STREAM_CODEC, Options::component,
				ByteBufCodecs.STRING_UTF8, Options::damageTypeId,
				Options::new
		);

		public Options(Component text){
			this(text, GENERIC.location().toString());
		}

		@Override
		public @NotNull ParticleType<DamageParticle.Options> getType() {
			return DAMAGE_PARTICLE_TYPE.get();
		}
	}
}
