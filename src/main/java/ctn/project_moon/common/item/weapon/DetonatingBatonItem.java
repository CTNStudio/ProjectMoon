package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.item.weapon.ego.CloseCombatEgo;
import ctn.project_moon.common.models.PmGeoItemModel;
import ctn.project_moon.init.PmDamageTypes;

public class DetonatingBatonItem extends Weapon {
    public DetonatingBatonItem(Weapon.Builder builder) {
        super(builder);
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
