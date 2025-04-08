package ctn.project_moon.events;

import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class ArmorAbsorptionEvent extends LivingEvent {
    private Iterable<ItemStack> armorSlots;
    private final DamageSource damageSource;
    private float damageAmount;
    private boolean isReturn = true;

    public ArmorAbsorptionEvent(Entity entity, DamageSource damageSource, float damageAmount) {
        super((LivingEntity) entity);
        if (!(entity instanceof Abnos)){
            this.armorSlots = ((LivingEntity) entity).getArmorAndBodyArmorSlots();
        }
        this.damageSource = damageSource;
        this.damageAmount = damageAmount;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public float getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(float damageAmount) {
        this.damageAmount = damageAmount;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean bool) {
        isReturn = bool;
    }

    public Iterable<ItemStack> getArmorSlots() {
        return armorSlots;
    }
}
