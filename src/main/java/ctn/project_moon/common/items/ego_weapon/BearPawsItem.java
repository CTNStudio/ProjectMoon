package ctn.project_moon.common.items.ego_weapon;

import ctn.project_moon.common.items.EgoWeaponItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.BiFunction;

import static ctn.project_moon.create.PmDamageSources.physicsDamage;
import static ctn.project_moon.create.PmDamageSources.spiritDamage;

public class BearPawsItem extends EgoWeaponItem {
    public BearPawsItem(Properties properties) {
        super(properties, physicsDamage());
    }
}
