package ctn.project_moon.common.item.creative_tool;

import ctn.project_moon.events.SpiritEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static ctn.project_moon.common.PmDataComponents.PmDataComponents.MODE_BOOLEAN;

public class CreativeSpiritToolItem extends Item {
    public CreativeSpiritToolItem(Properties properties) {
        super(properties.component(MODE_BOOLEAN, false));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if(player.isShiftKeyDown()){
            itemstack.set(MODE_BOOLEAN, !itemstack.get(MODE_BOOLEAN));
            // TODO
//            itemstack.
            return InteractionResultHolder.success(itemstack);
        }
        if (!level.isClientSide()) {
            SpiritEvents.updateSpiritValue(player, itemstack.get(MODE_BOOLEAN) ? -1 : 1);
        }
        return super.use(level, player, usedHand);
    }
}
