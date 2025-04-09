package ctn.project_moon.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ctn.project_moon.init.PmAttributes.*;

@Mixin(AttributeSupplier.Builder.class)
public abstract class AttributeSupplier$BuilderMixin{
    @Inject(method = "<init>*", at = @At("TAIL"))
    public void Builder(CallbackInfo ci) {
        create(PHYSICS_RESISTANCE);
        create(SPIRIT_RESISTANCE);
        create(EROSION_RESISTANCE);
        create(THE_SOUL_RESISTANCE);
    }

    @Shadow protected abstract AttributeInstance create(Holder<Attribute> attribute);
}
