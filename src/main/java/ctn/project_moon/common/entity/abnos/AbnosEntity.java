package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.api.GradeType;
import ctn.project_moon.init.PmAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class AbnosEntity extends Mob implements Abnos {
    protected AbnosEntity(EntityType<? extends Mob> entityType, Level level, GradeType.Level entityLevel) {
        super(entityType, level);
        setEntityLevel(this, entityLevel);
    }

    public static void setEntityLevel(LivingEntity entity,GradeType.Level entityLevel) {
        Objects.requireNonNull(entity.getAttribute(PmAttributes.ENTITY_LEVEL)).setBaseValue(entityLevel.getLevelValue());
    }

    public static GradeType.Level getEntityLevel(LivingEntity entity) {
        return GradeType.Level.getEntityLevel(entity);
    }
}
