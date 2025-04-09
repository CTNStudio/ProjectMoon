package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.item.CloseCombatEgo;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;

public class DetonatingBatonItem extends CloseCombatEgo {

    public DetonatingBatonItem(Properties properties, float maxDamage, float minDamage, float attackSpeed) {
        super(properties, PmDamageTypes.Types.PHYSICS, maxDamage, minDamage, attackSpeed);
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
