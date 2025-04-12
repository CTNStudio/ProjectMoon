package c;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class DamageIndicatorParticle extends TextureSheetParticle {
    private final Component text;
    private float factor=0;
    private float factorOld=0;
    private int transparency=0x11;
    private int transparencyOld=0x11;
    private final boolean big;

    public DamageIndicatorParticle(ClientLevel pLevel, double pX, double pY, double pZ, Component text, boolean big){
        super(pLevel, pX, pY, pZ);
        this.text = text;
        lifetime = 30;
        this.big = big;
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.CUSTOM;
    }

    @Override
    public void render(@NotNull VertexConsumer pBuffer, Camera camera, float pPartialTicks){
        super.render(pBuffer, camera, pPartialTicks);
        Minecraft minecraft = Minecraft.getInstance();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

        Vec3 camPos = camera.getPosition();
        double dx = this.x - camPos.x;
        double dy = Mth.lerp(pPartialTicks, yo, y) - camPos.y;
         double dz = this.z - camPos.z;
//        DisplayRenderer.TextDisplayRenderer.
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.translate(dx, dy, dz);

        poseStack.mulPose(camera.rotation());
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        float f = Mth.lerp(pPartialTicks, factorOld,factor);
        poseStack.scale(f, f, f);  // 文本大小
        int width = minecraft.font.width(text);
        Matrix4f matrix = new Matrix4f(poseStack.last().pose());
        minecraft.font.drawInBatch(text.getVisualOrderText(),
            -width / 2f, 0, Mth.lerpInt(pPartialTicks, transparencyOld, transparency) << 24,
                false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, getLightColor(pPartialTicks));
        matrix.translate(0, 0, 0.03f);
        minecraft.font.drawInBatch(text.getVisualOrderText(),
            -width / 2f, 0, Mth.lerpInt(pPartialTicks, transparencyOld, transparency) << 24,
                true, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, getLightColor(pPartialTicks));
        System.out.println("1");
        bufferSource.endBatch();
        poseStack.popPose();
    }

    @Override
    public void tick(){
        transparencyOld = transparency;
        factorOld=factor;
        yo = y;
        if(age <= 4){
            transparency =Math.min(transparency + 80, 255);
            factor = c.DeathAnimUtils.cubicBezier(age / 5f, 0, 1, 1, 1) * (big ? 0.06f : 0.04f);
        }
        float add= c.DeathAnimUtils.cubicBezier(age / (float)lifetime, 0,0.8f,1f,1) * 0.1f;
        float yOffset = 0.1f - add;
        y += yOffset;
        if(age >= lifetime - 3){
            transparency = Math.max(transparency - 60, 0);
        }
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        super.tick();
    }

    @Override
    protected int getLightColor(float pPartialTick){
        return 15 << 20 | 15 << 4;
    }

    public static class Provider implements ParticleProvider<c.DamageIndicatorOptions> {
        @Nullable
        @Override
        public Particle createParticle(@NotNull c.DamageIndicatorOptions options, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            if(!c.ClientConfigs.damageIndicator && options.type() == c.DamageIndicatorOptions.Type.DAMAGE
                || !c.ClientConfigs.healIndicator && options.type() == c.DamageIndicatorOptions.Type.HEAL){
                return null;
            }
            return new DamageIndicatorParticle(pLevel, pX, pY, pZ, options.text(), options.big());
        }
    }
}
