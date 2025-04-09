package ctn.project_moon.mixin;

import ctn.project_moon.events.ArmorAbsorptionEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
    @Shadow protected abstract void hurtArmor(DamageSource damageSource, float damageAmount);

    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    protected void getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        final LivingEntity thiz = (LivingEntity) (Object) this;

        final var pre = NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Pre(thiz, damageSource, damageAmount));
        damageAmount = pre.getNewDamageAmount();

        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            this.hurtArmor(damageSource, damageAmount);
            damageAmount = CombatRules.getDamageAfterAbsorb(thiz, damageAmount, damageSource, thiz.getArmorValue(),
                    (float) thiz.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        final var post = NeoForge.EVENT_BUS.post(new ArmorAbsorptionEvent.Post(thiz, damageSource, damageAmount));
        damageAmount = pre.getNewDamageAmount();

        cir.setReturnValue(damageAmount);
    }
}
