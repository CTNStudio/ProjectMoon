package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.capability.entity.IAbnos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

import static net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE;

public abstract class AbnosEntity extends Mob implements IAbnos, GeoEntity {
	protected AbnosEntity(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAbnosAttributes() {
		return createMobAttributes().add(KNOCKBACK_RESISTANCE, 1);
	}
}
