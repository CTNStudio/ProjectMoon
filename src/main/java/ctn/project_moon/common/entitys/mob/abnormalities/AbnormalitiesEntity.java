package ctn.project_moon.common.entitys.mob.abnormalities;

import ctn.project_moon.common.items.EgoItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public abstract class AbnormalitiesEntity extends Mob implements EgoItem{
    protected AbnormalitiesEntity(EntityType<? extends Mob> entityType, net.minecraft.world.level.Level level) {
        super(entityType, level);
    }

}
