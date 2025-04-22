package ctn.project_moon.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.api.PmApi;
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
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import static ctn.project_moon.init.PmParticleTypes.DAMAGE_PARTICLE_TYPE;

/**
 * 本文件参考汇流来世的伤害显示粒子制作
 */
public class DamageParticle extends TextureSheetParticle {
    private final Component text;
    private final float factorOld = 0.025f;
    private final float factor = 0;
    private double tickIn = 0;

    protected DamageParticle(ClientLevel level, double x, double y, double z, Component text) {
        super(level, x, y, z);
        this.text = text;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera camera, float pPartialTicks) {
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

        tickIn += 0.005;
        final float f = tickIn >= 1 ? 0 : Math.max(0, 1 - (float) smoothEntryFactor(tickIn)) * 0.15f;
        poseStack.scale(f, f, f);  // 文本大小
        int width = minecraft.font.width(text);
        Matrix4f matrix = new Matrix4f(poseStack.last().pose());
        matrix.translate(0, 0, 0.03f);
        minecraft.font.drawInBatch(
                text, -width / 2f, 0, PmApi.colorConversion("#ff0000"), true,
                matrix, bufferSource, Font.DisplayMode.NORMAL, PmApi.colorConversion("#ff0000"), PmApi.colorConversion("#ff0000"));
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
        if (age>100){
            remove();
        }
    }

    @Override
    public void remove() {
        super.remove();
    }

    public static class Provider implements ParticleProvider<Options> {
        @Override
        public @Nullable Particle createParticle(
                Options type, ClientLevel level,
                double x, double y, double z,
                double xSpeed, double ySpeed, double zSpeed) {
            return new DamageParticle(level, x, y, z, type.getComponent());
        }
    }

    public static class Options implements ParticleOptions {
        private final Component text;

        public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec(
                (thisOptionsInstance) -> thisOptionsInstance.group(
                        ComponentSerialization.CODEC.fieldOf("text").forGetter((thisOptions) -> thisOptions.text)
                ).apply(thisOptionsInstance, Options::new
        ));

        public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
                ComponentSerialization.STREAM_CODEC, opt -> opt.text,
                Options::new
        );

        public Options(Component text){
            this.text = text;
        }

        public Component getComponent() {
            return text;
        }

        @Override
        public ParticleType<DamageParticle.Options> getType() {
            return DAMAGE_PARTICLE_TYPE.get();
        }
    }
}
