package ctn.project_moon.common.item.weapon.ego;

import ctn.project_moon.common.item.AnimItem;
import net.minecraft.world.entity.player.Player;

public abstract class SpecialEgoWeapon extends EgoWeapon {
    public SpecialEgoWeapon(Builder builder) {
        super(builder, true);
    }

    public SpecialEgoWeapon(Properties properties, Builder builder) {
        super(properties, true, builder);
    }

}
