package ctn.project_moon.common.entity.projectile;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class PmBulletEntity extends ThrowableProjectile {
	protected PmBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
		super(entityType, level);
	}

	protected PmBulletEntity(EntityType<? extends ThrowableProjectile> entityType, double x, double y, double z, Level level) {
		super(entityType, x, y, z, level);
	}

	protected PmBulletEntity(EntityType<? extends ThrowableProjectile> entityType, LivingEntity shooter, Level level) {
		super(entityType, shooter, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public boolean isInWater() {
		return false;
	}
}
