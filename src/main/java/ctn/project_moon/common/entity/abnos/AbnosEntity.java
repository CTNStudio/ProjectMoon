package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE;

public abstract class AbnosEntity extends Mob implements Abnos, GeoEntity {
	protected AbnosEntity(EntityType<? extends Mob> entityType, Level level, PmDamageTool.Level entityLevel) {
		super(entityType, level);
		setEntityLevel(this, entityLevel);
	}

	public static AttributeSupplier.Builder createAbnosAttributes() {
		return createMobAttributes().add(KNOCKBACK_RESISTANCE, 1);
	}

	public static void setEntityLevel(LivingEntity entity, PmDamageTool.Level entityLevel) {
		Objects.requireNonNull(entity.getAttribute(PmEntityAttributes.ENTITY_LEVEL)).setBaseValue(entityLevel.getLevelValue());
	}
}
