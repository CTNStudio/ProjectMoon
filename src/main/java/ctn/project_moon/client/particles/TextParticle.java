package ctn.project_moon.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.mixin_extend.IModParticleEngine$MutableSpriteSet;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import javax.annotation.CheckForNull;

import static ctn.project_moon.init.PmParticleTypes.TEXT_PARTICLE_TYPE;
import static ctn.project_moon.tool.PmColourTool.colorConversion;
import static net.minecraft.world.damagesource.DamageTypes.GENERIC_KILL;

// TODO 优化伤害显示粒子 等待添加材质

/**
 * 本类参考汇流来世的伤害显示粒子制作
 */
@OnlyIn(Dist.CLIENT)
public class TextParticle extends TextureSheetParticle {
	private final Component              text;
	private final ResourceLocation       damageTypeId;
	private final PmDamageTool.ColorType colorDamageType;
	private final boolean                isHeal;
	private final boolean                isRationality;
	private final float                  maxSize;
	private final float                  maxTick;
	private final int                    fontColor;
	private final int                    strokeColor;
	private final SpriteSet              spriteSet;
	private       TextureAtlasSprite     sprite;
	
	protected TextParticle(ClientLevel level, double x, double y, double z,
			Component text, ResourceLocation damageTypeId,
			@Nullable PmDamageTool.ColorType colorDamageType,
			boolean isHeal, boolean isRationality, int fontColor, int strokeColor, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.spriteSet = spriteSet;
		float maxSize;
		float maxTick;
		this.damageTypeId    = damageTypeId;
		this.colorDamageType = colorDamageType;
		this.isHeal          = isHeal;
		this.isRationality   = isRationality;
		maxSize              = 0.05f;
		maxTick              = 20 * 3;
		if (isHeal) {
			maxTick = 20;
		}
		if (fontColor == 0 && strokeColor == 0) {
			if (this.isRationality) {
				fontColor   = colorConversion("#78f5ff");
				strokeColor = colorConversion("#2c80d0");
				if (this.isHeal) {
					this.sprite = getSprite(5);
				} else {
					this.sprite = getSprite(4);
				}
			} else if (this.isHeal) {
				fontColor   = colorConversion("#89ff6a");
				strokeColor = colorConversion("#1c501f");
			} else {
				if (isEquals(GENERIC_KILL)) {
					text = Component.literal("KILL");
				}
				if (this.colorDamageType != null) {
					switch (this.colorDamageType) {
						case PHYSICS -> {
							fontColor   = colorConversion("#ff0e0e");
							strokeColor = colorConversion("#4d0000");
							this.sprite = getSprite(0);
						}
						case SPIRIT -> {
							fontColor   = colorConversion("#ffffeb");
							strokeColor = colorConversion("#9c4e80");
							this.sprite = getSprite(1);
						}
						case EROSION -> {
							fontColor   = colorConversion("#6e2881");
							strokeColor = colorConversion("#28054a");
							this.sprite = getSprite(2);
						}
						case THE_SOUL -> {
							fontColor   = colorConversion("#60f5fa");
							strokeColor = colorConversion("#074161");
							this.sprite = getSprite(3);
						}
					}
				} else {
					fontColor   = colorConversion("#ff0e0e");
					strokeColor = colorConversion("#4d0000");
				}
			}
		}
		this.text        = text;
		this.maxSize     = maxSize;
		this.maxTick     = maxTick;
		this.fontColor   = fontColor;
		this.strokeColor = strokeColor;
		setSprite(sprite);
	}
	
	/**
	 * 创建文本粒子
	 *
	 * @param damageType    伤害类型
	 * @param colorType     四色伤害类型
	 * @param world         世界
	 * @param text          文本
	 * @param isHeal        是否是治疗
	 * @param isRationality 是否是理智操作
	 * @param fontColor     文本颜色
	 * @param strokeColor   描边颜色
	 * @param x             生成的X坐标
	 * @param y             生成的Y坐标
	 * @param z             生成的Z坐标
	 */
	public static void createTextParticles(ResourceKey<DamageType> damageType, PmDamageTool.ColorType colorType, ServerLevel world, Component text, boolean isHeal, boolean isRationality, int fontColor, int strokeColor, double x, double y, double z, double xOffset, double yOffset, double zOffset) {
		world.sendParticles(new TextParticle.Options(text, damageType, colorType, isHeal, isRationality, fontColor, strokeColor), x, y, z, 1, xOffset, yOffset, zOffset, 0);
	}
	
	public static void createTextParticles(ResourceKey<DamageType> damageType, PmDamageTool.ColorType colorType, LivingEntity entity, Component text, boolean isHeal, boolean isRationality, int fontColor, int strokeColor) {
		Vec3 pos = entity.position();
		double x = pos.x;
		double y = pos.y + entity.getBbHeight();
		double z = pos.z;
		AABB aabb = entity.getHitbox();
		double xOffset = aabb.maxX - aabb.minX;
		double zOffset = aabb.maxZ - aabb.minZ;
		if (!(entity.level() instanceof ServerLevel serverLevel)) {
			return;
		}
		serverLevel.sendParticles(
				new TextParticle.Options(text, damageType, colorType, isHeal, isRationality, fontColor, strokeColor),
				x, y, z,
				1,
				xOffset / 2 * 0.5f,
				0,
				zOffset / 2 * 0.5f, 0);
	}
	
	/// 伤害版本
	public static void createDamageParticles(ResourceKey<DamageType> damageType, PmDamageTool.ColorType colorType, LivingEntity entity, Component text, boolean isRationality) {
		createTextParticles(damageType, colorType, entity, text, false, isRationality, 0, 0);
	}
	
	/// 伤害版本
	public static void createDamageParticles(ResourceKey<DamageType> damageType, PmDamageTool.ColorType colorType, ServerLevel world, Component text, boolean isRationality, double x, double y, double z, double xOffset, double yOffset, double zOffset) {
		createTextParticles(damageType, colorType, world, text, false, isRationality, 0, 0, x, y, z, xOffset, yOffset, zOffset);
	}
	
	/// 治疗版本
	public static void createHealParticles(LivingEntity entity, Component text, boolean isRationality) {
		createTextParticles(null, null, entity, text, true, isRationality, 0, 0);
	}
	
	/// 治疗版本
	public static void createHealParticles(ServerLevel world, Component text, boolean isRationality, double x, double y, double z, double xOffset, double yOffset, double zOffset) {
		createTextParticles(null, null, world, text, true, isRationality, 0, 0, x, y, z, xOffset, yOffset, zOffset);
	}
	
	private TextureAtlasSprite getSprite(int index) {
		return ((IModParticleEngine$MutableSpriteSet) spriteSet).getSprites().get(index);
	}
	
	/**
	 * @param buffer 顶点缓冲区写入接口
	 * @param camera 相机
	 */
	// TODO
	@Override
	public void render(@NotNull VertexConsumer buffer, Camera camera, float partialTicks) {
		if (sprite != null) {
			setSprite(sprite);
			super.render(buffer, camera, partialTicks);
		}
		Minecraft minecraft = Minecraft.getInstance();
		// 获取相机位置
		Vec3 camPos = camera.getPosition();
		double dx = this.x - camPos.x;
		double dy = Mth.lerp(partialTicks, yo, y) - camPos.y;
		double dz = this.z - camPos.z;
		
		// 变换矩阵堆栈
		PoseStack poseStack = new PoseStack();
		poseStack.pushPose();
		float partialAge = (age + partialTicks); // 使用 age + 插值时间
		// 使用正弦函数计算粒子大小，使其由小变大再变小
		float sizeFactor = Math.abs((float) Math.sin((partialAge / maxTick) * Math.PI));
		float s = sizeFactor * maxSize;
		poseStack.translate(dx, dy, dz);
		poseStack.translate(0, partialAge * 0.05f, 0);
		// 根据视角旋转
		poseStack.mulPose(camera.rotation());
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		
		poseStack.scale(s, s, s);  // 文本大小
		int width = minecraft.font.width(text);
		int height = minecraft.font.lineHeight;
		Matrix4f matrix = new Matrix4f(poseStack.last().pose());
		
		int getLightColor = this.getLightColor(partialTicks);
		
		float x = -width / 2f;
		float y = -height / 2f;
		
		// 渲染缓冲区
		MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
		// 渲染描边
		renderStroke(x, y, minecraft, matrix, bufferSource, getLightColor);
		matrix.translate(0, 0, -0.03f);
		minecraft.font.drawInBatch(text, x, y, fontColor, false, matrix, bufferSource, Font.DisplayMode.NORMAL, strokeColor, getLightColor);
//		bufferSource.endBatch();
		poseStack.popPose();
	}
	
	@Override
	public void tick() {
		setSprite(sprite);
		age++;
		if (age > maxTick) {
			remove();
		}
	}
	
	/// 判断是否是指定伤害类型
	private boolean isEquals(@NotNull ResourceKey<DamageType> damageType) {
		if (this.damageTypeId == null) {
			return false;
		}
		return this.damageTypeId.equals(damageType.location());
	}
	
	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}
	
	/// 绘制描边
	private void renderStroke(float oldX, float oldY, Minecraft minecraft, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource, int getLightColor) {
		// 从左上开始
		float x = oldX + 1;
		float y = oldY + 1;
		int quantity = 0;
		for (int i = 0; i < 3; i++) {
			float x2 = x;
			for (int j = 0; j < 3; j++) {
				quantity++;
				if (quantity == 5) {
					x2 -= 1;
					continue;
				}
				minecraft.font.drawInBatch(text, x2, y, strokeColor, false, matrix, bufferSource, Font.DisplayMode.NORMAL, strokeColor, getLightColor);
				x2 -= 1;
			}
			y -= 1;
		}
	}
	
	@Override
	public void remove() {
		super.remove();
	}
	
	/** 粒子提供者 */
	public static class Provider implements ParticleProvider<Options> {
		private final SpriteSet spriteSet;
		
		// The registration function passes a SpriteSet, so we accept that and store it for further use.
		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}
		
		@Override
		@CheckForNull
		public Particle createParticle(Options type,
				@NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new TextParticle(
					level, x, y, z, type.component(), type.damageTypeId(),
					PmDamageTool.ColorType.is(type.colorDamageTypeId()),
					type.isHeal(), type.isRationality(),
					type.fontColor(), type.strokeColor(), spriteSet);
		}
	}
	
	/** 粒子参数 */
	@OnlyIn(Dist.CLIENT)
	public record Options(@NotNull Component component, @NotNull ResourceLocation damageTypeId,
	                      @NotNull String colorDamageTypeId, boolean isHeal, boolean isRationality, int fontColor,
	                      int strokeColor) implements ParticleOptions {
		public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec(
				(thisOptionsInstance) -> thisOptionsInstance.group(
						ComponentSerialization.CODEC.fieldOf("component").forGetter(Options::component),
						ResourceLocation.CODEC.fieldOf("damage_type").forGetter(Options::damageTypeId),
						Codec.STRING.fieldOf("color_damage_type").forGetter(Options::colorDamageTypeId),
						Codec.BOOL.fieldOf("is_heal").forGetter(Options::isHeal),
						Codec.BOOL.fieldOf("is_rationality").forGetter(Options::isRationality),
						Codec.INT.fieldOf("font_color").forGetter(Options::fontColor),
						Codec.INT.fieldOf("stroke_color").forGetter(Options::strokeColor)
				).apply(thisOptionsInstance, Options::new));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = NeoForgeStreamCodecs.composite(
				ComponentSerialization.STREAM_CODEC, Options::component,
				ResourceLocation.STREAM_CODEC, Options::damageTypeId,
				ByteBufCodecs.STRING_UTF8, Options::colorDamageTypeId,
				ByteBufCodecs.BOOL, Options::isHeal,
				ByteBufCodecs.BOOL, Options::isRationality,
				ByteBufCodecs.INT, Options::fontColor,
				ByteBufCodecs.INT, Options::strokeColor,
				Options::new
		);
		
		public static final Options BUILDER = new Options(
				Component.empty(),
				DamageTypes.GENERIC, PmDamageTool.ColorType.PHYSICS,
				false, false, 0, 0);
		
		public Options(Component component, ResourceKey<DamageType> damageType, PmDamageTool.ColorType colorDamageType, boolean isHeal, boolean isRationality, int fontColor, int strokeColor) {
			this(
					component, damageType == null ? ResourceLocation.parse("") : damageType.location(),
					colorDamageType == null ? "" : colorDamageType.getName(),
					isHeal, isRationality,
					fontColor, strokeColor);
		}
		
		@Override
		public @NotNull ParticleType<Options> getType() {
			return TEXT_PARTICLE_TYPE.get();
		}
	}
}
