package ctn.project_moon.common.entity.projectile;


import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.init.PmDamageTypes;
import ctn.project_moon.init.PmEntityTypes;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 魔弹实体
 *
 * @author dusttt
 * @date 2025-5-23
 */
public class MagicBulletEntity extends PmBulletEntity implements IRandomDamage {
	/**
	 * 追踪速度与转向速率
	 */
	private static final float                   TURN_RATE                     = 0.6f;//越小转向角越大，但越容易形成圆周运动
	private static final float                   BULLET_SPEED                  = 1.0f;
	private final        float                   distance                      = 30.0f;//追踪距离
	private final        int                     duration_ticks                = 100;//可存在时间（tick）
	private final        boolean                 canTrack                      = true;//追踪是否可用 TODO：看看是否加入配置文件，或删除
	private final        float                   TrackingDistance              = 4.0f;//追踪距离（水平）
	/**
	 * 已跟踪过的生物列表
	 */
	private final        ArrayList<LivingEntity> hasTracked                    = new ArrayList<>();
	private              int                     maxDamage;
	private              int                     minDamage;
	private              boolean                 canGoThroughWallsWhenNoTarget = false;//无目标时是否可以穿墙
	private              boolean                 dealDamageToAllies            = false;//是否对盟友造成伤害
	private              LivingEntity            trackingTarget;//正在追踪的目标
	
	public MagicBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
		super(entityType, level);
		this.setNoGravity(true);
	}
	
	public MagicBulletEntity(Level level, LivingEntity shooter) {
		super(PmEntityTypes.MAGIC_BULLET_ENTITY.get(), shooter, level);
		this.setNoGravity(true);
	}
	
	public static MagicBulletEntity create(Level level, LivingEntity shooter, int maxDamage, int minDamage) {
		MagicBulletEntity magicBullet = new MagicBulletEntity(level, shooter);
		magicBullet.maxDamage = maxDamage;
		magicBullet.minDamage = minDamage;
		magicBullet.setOwner(shooter);
		return magicBullet;
	}
	
	public void setTrackingTarget(LivingEntity trackingTarget) {
		this.trackingTarget = trackingTarget;
		hasTracked.add(trackingTarget);
	}
	
	public void setDealDamageToAllies(boolean dealDamageToAllies) {
		this.dealDamageToAllies = dealDamageToAllies;
	}
	
	public void setCanGoThroughWallsWhenNoTarget(boolean canGoThroughWallsWhenNoTarget) {
		this.canGoThroughWallsWhenNoTarget = canGoThroughWallsWhenNoTarget;
	}
	
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}
	
	//击中实体与地面时
	@Override
	protected void onHit(@NotNull HitResult result) {
		super.onHit(result);
	}
	
	/**
	 * 命中实体时
	 *
	 * @param result
	 */
	@Override
	protected void onHitEntity(@NotNull EntityHitResult result) {
		if (!this.level().isClientSide()) {
			Entity entity = result.getEntity();
			if (entity instanceof LivingEntity target) {
				if (target.isAlive() && target.isAttackable()) {
					dealDamageTo(target);
				}
				if (target.equals(this.trackingTarget)) {
					this.trackingTarget = null;
				}
			}
		}
	}
	
	/**
	 * 命中方块时
	 * 有追踪目标时均可穿墙
	 *
	 * @param result
	 */
	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		if (!this.level().isClientSide()) {
			if (this.trackingTarget == null && !this.canGoThroughWallsWhenNoTarget) {
				this.discard();
			}
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		//添加尾迹粒子
		if (this.level().isClientSide) {
			if (getParticle() != null) {
				this.level().addParticle(this.getParticle(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
			}
		}
		//存在时间检测
		if (this.tickCount > this.duration_ticks) {
			this.discard();
		}
		Entity owner = getOwner();
		if (owner == null) {
			return;
		}
		//距离检查
		if (this.distance < this.distanceTo(owner)) {
			this.discard();
		}
		//追踪
		if (this.canTrack && !level().isClientSide) {
			
			// 获取追踪目标
			if (this.trackingTarget == null) {
				//获取范围内实体
				AABB aabb = new AABB(
						this.getX() - this.TrackingDistance, this.getY() - 1.0f, this.getZ() - this.TrackingDistance,
						this.getX() + this.TrackingDistance, this.getY() + 1.0f, this.getZ() + this.TrackingDistance);
				List<Entity> entityList = level().getEntitiesOfClass(
						Entity.class, aabb, (entity) -> {
							if (!(entity instanceof LivingEntity)) {
								return false;
							}
							if (this.hasTracked.contains(entity)) {
								return false;
							}
							if (!entity.isAttackable() || !entity.isAlive() || (entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
								return false;
							}
							
							// 默认只对非玩家队友生物进行追踪，如果要追踪玩家
							if (!dealDamageToAllies && entity.isAlliedTo(owner)) {
								return false;
							}
							
							//限制追踪距离（削圆）
							if (entity.distanceTo(this) > this.TrackingDistance) {
								return false;
							}
							// 此处搜索时不追踪自己，但只是此处
							return !entity.getUUID().equals(owner.getUUID());
						});
				int i = entityList.size();
				if (i > 0) {
					this.setTrackingTarget((LivingEntity) entityList.get(level().getRandom().nextInt(i)));
				}
			}
			
			// 追踪目标
			else {
				// 计算目标方向向量
				//TODO: 现在是追踪目标眼睛的位置（但有些生物如末影龙眼睛处没有碰撞就打不到）
				Vec3 targetPos = trackingTarget.position().add(0, trackingTarget.getEyeHeight(), 0);
				Vec3 direction = targetPos.subtract(position()).normalize();
				
				// 调整当前运动方向
				Vec3 currentMotion = getDeltaMovement();
				Vec3 newMotion = currentMotion.scale(1 - TURN_RATE).add(direction.scale(TURN_RATE * BULLET_SPEED));
				
				// 更新运动参数
				setDeltaMovement(newMotion.normalize().scale(BULLET_SPEED));
				yRotO = (float) Math.toDegrees(Math.atan2(newMotion.x, newMotion.z));
				xRotO = (float) Math.toDegrees(Math.atan2(newMotion.y, Math.sqrt(newMotion.x * newMotion.x + newMotion.z * newMotion.z)));
				this.setXRot(xRotO);
				this.setYRot(yRotO);
			}
		}
	}
	
	public ParticleOptions getParticle() {
		return ParticleTypes.FLAME;//TODO:测试现形用，待修改
	}
	
	private void dealDamageTo(Entity target) {
		final ResourceKey<DamageType> EROSION = PmDamageTypes.EROSION;
		if (getOwner() instanceof LivingEntity livingentity) {
			/**
			 * 注释掉的是队友与玩家过滤
			 * 解除注释后普通子弹（无tag）将不会伤害玩家与队友
			 */
//            if(!dealDamageToAllies && (livingentity.isAlliedTo(target) || target == livingentity)){
//                return;
//            }
			if (!target.isAlive() || target.isInvulnerable()) {
				return;
			}
			float damage = getDamageValue(target.getRandom());
			DamageSource source = damageSources().source(EROSION, this, livingentity);
			IModDamageSource damageSource = (IModDamageSource) source;
			damageSource.setFourColorDamageTypes(PmDamageTool.ColorType.EROSION);
			damageSource.setDamageLevel(PmDamageTool.Level.WAW);
			target.hurt(source, damage);
		}
	}
	
	@Override
	public boolean shouldBeSaved() {
		return true;
	}
	
	@Override
	public int getMaxDamage() {
		return maxDamage;
	}
	
	@Override
	public int getMinDamage() {
		return minDamage;
	}
}
