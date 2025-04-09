package ctn.project_moon.mixin;

import ctn.project_moon.events.ArmorAbsorptionEvent;
import net.minecraft.core.Holder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static ctn.project_moon.events.PmCommonHooks.entityArmorAbsorptionPost;
import static ctn.project_moon.events.PmCommonHooks.entityArmorAbsorptionPre;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
    @Shadow protected abstract void hurtArmor(DamageSource damageSource, float damageAmount);
    @Shadow public abstract int getArmorValue();
    @Shadow public abstract double getAttributeValue(Holder<Attribute> attribute);

//    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
//    protected void getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
//        ArmorAbsorptionEvent event = entityArmorAbsorptionPre(this, damageSource, damageAmount);
//        if (event.isReturn()) {
//            cir.setReturnValue(event.getDamageAmount());
//        }
//    }

    /**
     * @author 小尽
     * @reason 为了调用原版的盔甲逻辑故如此处理，日后需要兼容请调用{@link ArmorAbsorptionEvent}
     */
    @Overwrite
    protected float getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount) {
        ArmorAbsorptionEvent.Pre pre = entityArmorAbsorptionPre(this, damageSource, damageAmount);
        if (pre.isReturn()) {
            damageAmount = pre.getDamageAmount();
        }

        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            this.hurtArmor(damageSource, damageAmount);
            damageAmount = CombatRules.getDamageAfterAbsorb(
                    (LivingEntity) (Entity) this, damageAmount, damageSource, (float)this.getArmorValue(), (float)this.getAttributeValue(Attributes.ARMOR_TOUGHNESS)
            );
        }

        ArmorAbsorptionEvent.Post post = entityArmorAbsorptionPost(this, damageSource, damageAmount);
        if (post.isReturn()) {
            damageAmount = post.getDamageAmount();
        }

        return damageAmount;
    }
}
