package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.init.PmDamageTypes;

public class BearPawsItem extends CloseCombatEgo {
    public BearPawsItem(Weapon.Builder builder) {
        super(builder, PmDamageTypes.Types.PHYSICS);
    }
}
