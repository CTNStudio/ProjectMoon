package ctn.project_moon.init;

import ctn.project_moon.PmMain;
import ctn.project_moon.api.Attribute;
import ctn.project_moon.api.DoubleAttributeCodec;
import ctn.project_moon.core.PMRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// TODO - Make all attribute in register.
public class PmAttribute {
    public static final DeferredRegister<Attribute<?>> REGISTER = DeferredRegister.create(PMRegistries.ATTRIBUTE, PmMain.MOD_ID);

    public static final DeferredHolder<Attribute<?>, Attribute<Double>> SPIRIT = REGISTER.register("spirit",
            () -> new Attribute<>(new DoubleAttributeCodec("spirit")));
}
