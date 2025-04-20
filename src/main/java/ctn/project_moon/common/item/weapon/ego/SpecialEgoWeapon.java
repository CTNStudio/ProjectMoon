package ctn.project_moon.common.item.weapon.ego;

public abstract class SpecialEgoWeapon extends EgoWeapon {
    public SpecialEgoWeapon(Builder builder) {
        super(builder, true);
    }

    public SpecialEgoWeapon(Properties properties, Builder builder) {
        super(properties, true, builder);
    }

}
