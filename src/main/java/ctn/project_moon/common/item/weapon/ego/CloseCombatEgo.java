package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.item.ItemStack;

import static ctn.project_moon.init.PmDamageTypes.getDamageTypeLocation;
import static ctn.project_moon.init.PmDataComponents.CURRENT_DAMAGE_TYPE;

public abstract class CloseCombatEgo extends EgoWeapon {
    public CloseCombatEgo(ctn.project_moon.common.item.weapon.Weapon.Builder builder, PmDamageTypes.Types defaultDamageType) {
        this(builder.build(), builder, defaultDamageType);
    }

    public CloseCombatEgo(Properties properties, ctn.project_moon.common.item.weapon.Weapon.Builder builder, PmDamageTypes.Types defaultDamageType) {
        super(properties.component(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(defaultDamageType)), builder);
    }

    protected void setDamageType(ItemStack itemStack, PmDamageTypes.Types damageType) {
        itemStack.set(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(damageType));
    }
}
