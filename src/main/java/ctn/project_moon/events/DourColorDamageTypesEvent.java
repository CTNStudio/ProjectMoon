package ctn.project_moon.events;

import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;

/**
 * {@link ICancellableEvent} 用于防止其他继续覆盖伤害类型
 */
public class DourColorDamageTypesEvent extends LivingEvent implements ICancellableEvent {
    @Nullable
    private final DamageContainer container;
    private final DamageSource source;
    private PmDamageTypes.Types dourColorDamageTypes;

    public DourColorDamageTypesEvent(LivingEntity entity, DamageSource source, DamageContainer container) {
        super(entity);
        this.source = source;
        this.container = container;
    }

    public DourColorDamageTypesEvent(LivingEntity entity, DamageSource source) {
        super(entity);
        this.source = source;
        this.container = null;
    }

    public DamageContainer getContainer() {
        return container;
    }

    public DamageSource getSource() {
        return source;
    }

    public PmDamageTypes.Types getDamageTypes() {
        return dourColorDamageTypes;
    }

    public void setDourColorDamageTypes(PmDamageTypes.Types dourColorDamageTypes) {
        this.dourColorDamageTypes = dourColorDamageTypes;
    }
}
