package ctn.project_moon.mixin;

import ctn.project_moon.events.ArmorAbsorptionEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ctn.project_moon.events.PmCommonHooks.entityArmorAbsorption;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    protected void getDamageAfterArmorAbsorb(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        ArmorAbsorptionEvent event = entityArmorAbsorption(this, damageSource, damageAmount);
        if (event.isReturn()) {
            cir.setReturnValue(event.getDamageAmount());
        }
    }
}
