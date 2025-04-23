package ctn.project_moon.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.GradeTypeTool.Level.ALEPH;
import static ctn.project_moon.tool.GradeTypeTool.Level.ZAYIN;

public class PmAttributes {
    public static final DeferredRegister<Attribute> PM_ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, MOD_ID);

    public static final Holder<Attribute> PHYSICS_RESISTANCE = registerRangedAttribute("generic.physics_resistance", "attribute.name.generic.physics_resistance", 1.0, -1024, 1024);
    public static final Holder<Attribute> SPIRIT_RESISTANCE = registerRangedAttribute("generic.spirit_resistance", "attribute.name.generic.spirit_resistance", 1.0, -1024, 1024);
    public static final Holder<Attribute> EROSION_RESISTANCE = registerRangedAttribute("generic.erosion_resistance", "attribute.name.generic.erosion_resistance", 1.5, -1024, 1024);
    public static final Holder<Attribute> THE_SOUL_RESISTANCE = registerRangedAttribute("generic.the_soul_resistance", "attribute.name.generic.the_soul_resistance", 2.0, -1024, 1024);
    public static final Holder<Attribute> ENTITY_LEVEL = registerRangedAttribute("generic.entity_level", "attribute.name.generic.entity_level", ZAYIN.getLevelValue(), ZAYIN.getLevelValue(), ALEPH.getLevelValue());
    public static final Holder<Attribute> MAX_SPIRIT = registerRangedAttribute("generic.max_spirit", "attribute.name.generic.max_spirit", 20, 0, 4096);
    public static final Holder<Attribute> SPIRIT_NATURAL_RECOVERY_RATE = registerRangedAttribute("generic.spirit_natural_recovery_rate", "attribute.name.generic.spirit_natural_recovery_rate", 0.5, 0, 4096);
    public static final Holder<Attribute> SPIRIT_RECOVERY_AMOUNT = registerRangedAttribute("generic.spirit_recovery_amount", "attribute.name.generic.spirit_recovery_amount", 1, 0, 4096);

    public static Holder<Attribute> registerRangedAttribute(String name, String descriptionId, double defaultValue, double min, double max){
        return PM_ATTRIBUTE.register(name, () -> new RangedAttribute(MOD_ID + "." + descriptionId, defaultValue, min, max).setSyncable(true));
    }
}
