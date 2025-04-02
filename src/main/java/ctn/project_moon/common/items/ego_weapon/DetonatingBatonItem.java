package ctn.project_moon.common.items.ego_weapon;


import ctn.project_moon.client.az_dispatchers.item.DetonatingBatonItemDispatcher;
import ctn.project_moon.common.items.EgoWeaponItem;

import static ctn.project_moon.create.PmDamageSources.physicsDamage;

public class DetonatingBatonItem extends EgoWeaponItem {
    public final DetonatingBatonItemDispatcher dispatcher;

    public DetonatingBatonItem(Properties properties) {
        super(properties, physicsDamage());
        dispatcher = new DetonatingBatonItemDispatcher();
    }

//    @Override
//    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
//        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
//        if (livingEntity instanceof Player player && !level.isClientSide()) {
//            // This is where you now trigger an animation to play
//            dispatcher.firing(player, stack);
//        }
//    }
}
