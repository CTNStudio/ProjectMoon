package ctn.project_moon.common.item.armor.ego;

import ctn.project_moon.common.item.armor.Armor;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

import static ctn.project_moon.init.PmDataComponents.IS_RESTRAIN;

public class EgoArmor extends Armor {

    public EgoArmor(Builder builder) {
        this(builder.build(), builder);
    }

    public EgoArmor(Properties properties, Builder builder) {
        super(properties.component(IS_RESTRAIN, false), builder);
    }
}
