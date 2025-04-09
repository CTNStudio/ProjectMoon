package ctn.project_moon.events;

import ctn.project_moon.common.entity.abnos.Abnos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
    private boolean isReturn = true;
    private float newDamageAmount;
    private final ItemStack[] armorSlots;
    private final DamageSource damageSource;
    private final float damageAmount;

    public ArmorAbsorptionEvent(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        super(entity);
        this.armorSlots = new ItemStack[4];
        this.damageSource = damageSource;
        this.damageAmount = damageAmount;
        this.newDamageAmount = this.damageAmount;

        final var flag = !(entity instanceof Abnos);
        final var itor = entity.getArmorAndBodyArmorSlots().iterator();
        for (int i = 0; i < 4; i++) {
            this.armorSlots[i] = flag ? itor.next() : ItemStack.EMPTY;
        }
    }

    public float getNewDamageAmount() {
        return newDamageAmount;
    }

    public void setNewDamageAmount(float newDamageAmount) {
        this.newDamageAmount = newDamageAmount;
    }

    public float getDamageAmount() {
        return damageAmount;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public ItemStack[] getArmorSlots() {
        return armorSlots;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    /** 原版护甲处理前 */
    public static class Pre extends ArmorAbsorptionEvent{
        public Pre(LivingEntity entity, DamageSource damageSource, float damageAmount) {
            super(entity, damageSource, damageAmount);
        }
    }

    /** 原版护甲处理后 */
    public static class Post extends ArmorAbsorptionEvent{
        public Post(LivingEntity entity, DamageSource damageSource, float damageAmount) {
            super(entity, damageSource, damageAmount);
        }
    }
}
