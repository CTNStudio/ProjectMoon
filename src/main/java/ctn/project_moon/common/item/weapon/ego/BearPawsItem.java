package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.SetInvulnerabilityTicks;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.init.PmDamageTypes;

public class BearPawsItem extends CloseCombatEgo implements SetInvulnerabilityTicks {
    public BearPawsItem(Weapon.Builder builder) {
        super(builder, PmDamageTypes.Types.PHYSICS);
    }

    @Override
    public int getTicks() {
        return 10;
    }
}
