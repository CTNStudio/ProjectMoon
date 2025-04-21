package ctn.project_moon.mixin;

import ctn.project_moon.events.entity.ArmorAbsorptionEvent;
import ctn.project_moon.init.PmCommonHooks;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ctn.project_moon.init.PmCommonHooks.entityArmorAbsorptionPost;
import static ctn.project_moon.init.PmCommonHooks.entityArmorAbsorptionPre;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
    @Shadow protected abstract void hurtArmor(DamageSource damageSource, float damageAmount);

    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    protected void getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        final LivingEntity thiz = (LivingEntity) (Object) this;

        ArmorAbsorptionEvent pre = PmCommonHooks.entityArmorAbsorptionPre(thiz, damageSource, damageAmount);
        if (pre.isReturn()) {
            damageAmount = pre.getNewDamageAmount();
        }

        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            this.hurtArmor(damageSource, damageAmount);
            damageAmount = CombatRules.getDamageAfterAbsorb(thiz, damageAmount, damageSource,
                    thiz.getArmorValue(), (float) thiz.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        ArmorAbsorptionEvent post = PmCommonHooks.entityArmorAbsorptionPost(thiz, damageSource, damageAmount);
        if (post.isReturn()) {
            damageAmount = post.getNewDamageAmount();
            cir.setReturnValue(damageAmount);
        }
    }
}
