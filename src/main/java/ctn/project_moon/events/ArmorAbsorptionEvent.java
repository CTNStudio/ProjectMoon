package ctn.project_moon.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class ArmorAbsorptionEvent extends LivingEvent {
    private final DamageSource damageSource;
    private final float damageAmount;
    private boolean isReturn = true;

    public ArmorAbsorptionEvent(Entity entity, DamageSource damageSource, float damageAmount) {
        super((LivingEntity) entity);
        this.damageSource = damageSource;
        this.damageAmount = damageAmount;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public float getDamageAmount() {
        return damageAmount;
    }

    public float setDamageAmount() {
        return damageAmount;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean bool) {
        isReturn = bool;
    }
}
