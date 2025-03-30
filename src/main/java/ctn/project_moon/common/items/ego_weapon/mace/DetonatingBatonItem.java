package ctn.project_moon.common.items.ego_weapon.mace;


import ctn.project_moon.common.az_dispatchers.item.mace.DetonatingBatonItemDispatcher;
import ctn.project_moon.datagen.PmDamageTypes;

public class DetonatingBatonItem extends MaceItem {
    public final DetonatingBatonItemDispatcher dispatcher;

    public DetonatingBatonItem(Properties properties) {
        super(properties);
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
