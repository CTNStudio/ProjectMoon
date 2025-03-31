package ctn.project_moon.common.entitys.mob.abnos;

import ctn.project_moon.common.items.EgoItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public abstract class AbnosEntity extends Mob implements EgoItem{
    protected AbnosEntity(EntityType<? extends Mob> entityType, net.minecraft.world.level.Level level) {
        super(entityType, level);
    }

}
