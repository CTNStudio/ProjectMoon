package ctn.project_moon.common.entity.projectile;

import ctn.project_moon.api.SpiritAttribute;
import ctn.project_moon.client.models.PmGeoEntityModel;
import ctn.project_moon.common.RandomDamageProcessor;
import ctn.project_moon.common.SetInvulnerabilityTick;
import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;

import static ctn.project_moon.api.tool.PmDamageTool.Level.ALEPH;
import static ctn.project_moon.init.PmEntity.PARADISE_LOST_SPIKEWEED;
import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public class ParadiseLostSpikeweed extends Entity implements TraceableEntity, GeoEntity, RandomDamageProcessor, SetInvulnerabilityTick {
	private final AnimatableInstanceCache ANIMS = GeckoLibUtil.createInstanceCache(this);
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;
	private final int lifeTicks = 22;
	private boolean clientSideAttackStarted;
	private int targetNumber = 1;
	private boolean isAttack = false;

	private LivingEntity targetEntity;

	public ParadiseLostSpikeweed(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	public static ParadiseLostSpikeweed create(Level level, double x, double y, double z, int targetNumber, LivingEntity owner) {
		ParadiseLostSpikeweed entity = new ParadiseLostSpikeweed(PARADISE_LOST_SPIKEWEED.get(), level);
		entity.targetNumber = targetNumber == 0 ? 1 : targetNumber;
		entity.setPos(x, y, z);
		entity.setOwner(owner);
		return entity;
	}

	public static ParadiseLostSpikeweed create(Level level, double x, double y, double z, int targetNumber, LivingEntity owner, LivingEntity targetEntity) {
		ParadiseLostSpikeweed entity = create(level, x, y, z, targetNumber, owner);
		entity.targetEntity = targetEntity;
		return entity;
	}

	public static ParadiseLostSpikeweed create(Level level, Vec3 vec3, int targetNumber, LivingEntity owner) {
		ParadiseLostSpikeweed entity = new ParadiseLostSpikeweed(PARADISE_LOST_SPIKEWEED.get(), level);
		entity.targetNumber = targetNumber == 0 ? 1 : targetNumber;
		entity.setPos(vec3);
		entity.setOwner(owner);
		return entity;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AttributeSupplier.builder().add(PmEntityAttributes.ENTITY_LEVEL, ALEPH.getLevelValue());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {

	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			// TODO 等资源重置 之后写入动画
			return;
		}
		if (!isAttack && tickCount < 2) {
			List<Entity> entityList = level().getEntitiesOfClass(Entity.class, getBoundingBox(), (entity) -> {
				if (entity instanceof ItemEntity) {
					return false;
				}
				if (entity instanceof ExperienceOrb) {
					return false;
				}
				if (entity instanceof ParadiseLostSpikeweed) {
					return false;
				}
				if (entity instanceof Projectile) {
					return false;
				}
				return getOwner() != null && !entity.getUUID().equals(getOwner().getUUID());
			});
			int i = entityList.size();
			if (i > 0) {
				for (int j = 0; j < i; j++) {
					Entity livingEntity = (targetEntity != null && targetEntity.isAlive() && targetEntity.isAttackable()) ?
							targetEntity : entityList.get(level().getRandom().nextInt(i));
					if (!dealDamageTo(livingEntity)) {
						continue;
					}
					hit(livingEntity);
					isAttack = true;
					break;
				}
			}
		}

		if (tickCount > 60) {
			remove(RemovalReason.DISCARDED);
		}

	}

	public void hit(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 10, 3));
		}
		LivingEntity livingentity = getOwner();
		if (livingentity == null) {
			return;
		}
		int value = livingentity.getRandom().nextInt(2, 4 + 1);
		livingentity.heal(value);
		SpiritAttribute.incrementSpiritValue(livingentity, value);
	}

	private boolean dealDamageTo(Entity target) {
		LivingEntity livingentity = getOwner();
		float damage = getDamage(target.getRandom());
		final ResourceKey<DamageType> THE_SOUL = PmDamageTypes.THE_SOUL;
		if (livingentity == null && !(target.isAlive() && !target.isInvulnerable() && target.getUUID().equals(livingentity.getUUID()))) {
			return target.hurt(damageSources().source(THE_SOUL, this, null), damage);
		} else {
			if (livingentity.isAlliedTo(target)) {
				return false;
			}
			return target.hurt(damageSources().source(THE_SOUL, this, livingentity), damage);
		}
	}

	@Override
	public @Nullable ItemStack getWeaponItem() {
		return owner != null ? owner.getMainHandItem() : null;
	}

	@Override
	protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
		if (compound.hasUUID("Owner")) {
			ownerUUID = compound.getUUID("Owner");
		}
	}

	@Override
	protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
		if (ownerUUID != null) {
			compound.putUUID("Owner", ownerUUID);
		}
	}

	@Override
	public @Nullable LivingEntity getOwner() {
		if (owner == null && ownerUUID != null && level() instanceof ServerLevel) {
			Entity entity = ((ServerLevel) level()).getEntity(ownerUUID);
			if (entity instanceof LivingEntity) {
				owner = (LivingEntity) entity;
			}
		}

		return owner;
	}

	/**
	 * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
	 */
	@Override
	public void handleEntityEvent(byte id) {
		super.handleEntityEvent(id);
		if (id == 4) {
			clientSideAttackStarted = true;
			if (!isSilent()) {
				level().playLocalSound(
						getX(), getY(), getZ(),
						SoundEvents.EVOKER_FANGS_ATTACK,
						getSoundSource(),
						1.0F,
						random.nextFloat() * 0.2F + 0.85F,
						false);
			}
		}
	}

	@Override
	public int getTicks() {
		return 10;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return ANIMS;
	}

	public int getTargetNumber() {
		return targetNumber;
	}

	@Override
	public int getMaxDamage() {
		return extracted(28, 23, 20);
	}

	@Override
	public int getMinDamage() {
		return extracted(22, 19, 16);
	}

	private int extracted(int damage, int damage1, int damage2) {
		if (targetNumber != 1) {
			if (targetNumber >= 2) {
				damage = damage1;
			}
			if (targetNumber >= 6) {
				damage = damage2;
			}
		}
		return damage;
	}

	public float getAnimationProgress(float partialTicks) {
		if (!clientSideAttackStarted) {
			return 0.0F;
		} else {
			int i = lifeTicks - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - partialTicks) / 20.0F;
		}
	}

	public void setOwner(@Nullable LivingEntity owner) {
		this.owner = owner;
		ownerUUID = owner == null ? null : owner.getUUID();
	}

	public static class TrainingRabbitsRenderer extends GeoEntityRenderer<ParadiseLostSpikeweed> {
		public TrainingRabbitsRenderer(EntityRendererProvider.Context context) {
			super(context, new PmGeoEntityModel<>("paradise_lost_spikeweed"));
		}

		@Override
		public @NotNull ResourceLocation getTextureLocation(@NotNull ParadiseLostSpikeweed animatable) {
			return PmGeoEntityModel.texturePath("paradise_lost_spikeweed");
		}
	}
}
