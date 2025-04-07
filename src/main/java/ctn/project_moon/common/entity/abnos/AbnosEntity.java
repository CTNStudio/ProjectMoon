package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.api.GradeType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class AbnosEntity extends Mob implements Abnos {
    private final GradeType.Level AbnosLevel;

    protected AbnosEntity(EntityType<? extends Mob> entityType, Level level, GradeType.Level abnosLevel) {
        super(entityType, level);
        AbnosLevel = abnosLevel;
    }

    public static GradeType.Level getEntityLevel(Entity entity) {
        if (!(entity instanceof ctn.project_moon.common.entity.abnos.AbnosEntity)) {
            return GradeType.Level.ZAYIN;
        }
        return ((ctn.project_moon.common.entity.abnos.AbnosEntity) entity).getAbnosLevel();
    }

    public GradeType.Level getAbnosLevel() {
        return AbnosLevel;
    }
}
