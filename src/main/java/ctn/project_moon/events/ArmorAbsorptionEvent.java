package ctn.project_moon.events;

import ctn.project_moon.common.entity.abnos.Abnos;
import ctn.project_moon.common.entity.abnos.AbnosEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/** 盔甲减伤逻辑
 * <p>
 * isReturn 变量决定是否返回处理的伤害到逻辑中
 * <p>
 * 本事件在 第8步之后，第9步之前，严格来说属于第9步之中
 * @see DamageContainer for more information on the damage sequence
 * */
public abstract class ArmorAbsorptionEvent extends LivingEvent {
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

    /** 原版护甲处理前 */
    public static class Pre extends ArmorAbsorptionEvent{
        public Pre(Entity entity, DamageSource damageSource, float damageAmount) {
            super(entity, damageSource, damageAmount);
        }
    }

    /** 原版护甲处理后 */
    public static class Post extends ArmorAbsorptionEvent{
        public Post(Entity entity, DamageSource damageSource, float damageAmount) {
            super(entity, damageSource, damageAmount);
        }
    }
}
