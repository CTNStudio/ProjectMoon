package ctn.project_moon.common.item.weapon;


import ctn.project_moon.common.models.PmGeoItemModel;

public class DetonatingBatonItem extends Weapon {
    public DetonatingBatonItem(Weapon.Builder builder) {
        super(builder);
        setDefaultModel(new PmGeoItemModel<>("detonating_baton"));
    }
}
