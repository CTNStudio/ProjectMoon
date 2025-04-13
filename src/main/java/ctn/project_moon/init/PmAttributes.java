package ctn.project_moon.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.GradeType.Level.ALEPH;
import static ctn.project_moon.api.GradeType.Level.ZAYIN;

public class PmAttributes {
    public static final DeferredRegister<Attribute> PM_ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, MOD_ID);

    public static final Holder<Attribute> PHYSICS_RESISTANCE = registerRangedAttribute("generic.physics_resistance", "attribute.name.generic.physics_resistance", 1.0, -1024, 1024);
    public static final Holder<Attribute> SPIRIT_RESISTANCE = registerRangedAttribute("generic.spirit_resistance", "attribute.name.generic.spirit_resistance", 1.0, -1024, 1024);
    public static final Holder<Attribute> EROSION_RESISTANCE = registerRangedAttribute("generic.erosion_resistance", "attribute.name.generic.erosion_resistance", 1.5, -1024, 1024);
    public static final Holder<Attribute> THE_SOUL_RESISTANCE = registerRangedAttribute("generic.the_soul_resistance", "attribute.name.generic.the_soul_resistance", 2.0, -1024, 1024);
    public static final Holder<Attribute> ENTITY_LEVEL = registerRangedAttribute("generic.entity_level", "attribute.name.generic.entity_level", ZAYIN.getLevelValue(), ZAYIN.getLevelValue(), ALEPH.getLevelValue());

    public static Holder<Attribute> registerRangedAttribute(String name, String descriptionId, double defaultValue, double min, double max){
        return PM_ATTRIBUTE.register(name, () -> new RangedAttribute(MOD_ID + "." + descriptionId, defaultValue, min, max).setSyncable(true));
    }
}
