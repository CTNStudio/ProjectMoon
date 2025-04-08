package ctn.project_moon.common.entity.abnos;

import ctn.project_moon.api.GradeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import static ctn.project_moon.events.SpiritEvents.createAttribute;

public class AbnosEntity extends Mob implements Abnos {
    public static final String ENTITY_LEVEL = createAttribute("nbt.entity_level");
    private final GradeType.Level entityLevel;

    protected AbnosEntity(EntityType<? extends Mob> entityType, Level level, GradeType.Level entityLevel) {
        super(entityType, level);
        this.entityLevel = entityLevel;
    }

    public static GradeType.Level getEntityLevel(Entity entity) {
        if (!(entity instanceof ctn.project_moon.common.entity.abnos.AbnosEntity)) {
            return GradeType.Level.ZAYIN;
        }
        return ((ctn.project_moon.common.entity.abnos.AbnosEntity) entity).getEntityLevel();
    }

    public GradeType.Level getEntityLevel() {
        return GradeType.Level.getEntityLevel(this);
    }

    @Override
    public void load(CompoundTag compound){
        super.load(compound);
        compound.putString(ENTITY_LEVEL, entityLevel.getName());
    }
}
