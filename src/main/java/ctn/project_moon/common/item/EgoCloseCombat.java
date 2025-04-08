package ctn.project_moon.common.item;

import ctn.project_moon.init.PmDamageTypes;
import net.minecraft.world.item.ItemStack;

import static ctn.project_moon.common.item.components.PmDataComponents.CURRENT_DAMAGE_TYPE;
import static ctn.project_moon.init.PmDamageTypes.getDamageTypeLocation;

public abstract class EgoCloseCombat extends EgoWeaponItem {
    public EgoCloseCombat(Properties properties, PmDamageTypes.Types defaultDamageType, float maxDamage, float minDamage, float attackSpeed) {
        super(properties.component(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(defaultDamageType)), maxDamage, minDamage, attackSpeed);
    }

    protected void setDamageType(ItemStack itemStack, PmDamageTypes.Types damageType) {
        itemStack.set(CURRENT_DAMAGE_TYPE, getDamageTypeLocation(damageType));
    }
}
