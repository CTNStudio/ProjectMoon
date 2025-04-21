package ctn.project_moon.common.item;

import ctn.project_moon.api.SpiritApi;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.common.item.PmDataComponents.MODE_BOOLEAN;
import static ctn.project_moon.api.SpiritApi.getSpiritValue;

public class CreativeSpiritToolItem extends Item {
    public CreativeSpiritToolItem(Properties properties) {
        super(properties.component(MODE_BOOLEAN, false));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (player.isCreative()) {
            if (player.isShiftKeyDown()) {
                itemStack.set(MODE_BOOLEAN, !itemStack.get(MODE_BOOLEAN));
                return InteractionResultHolder.success(itemStack);
            }
            if (!level.isClientSide()) {
                SpiritApi.incrementSpiritValue(player, itemStack.get(MODE_BOOLEAN) ? -1 : 1);
                return InteractionResultHolder.success(itemStack);
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof Player player && !level.isClientSide() && isSelected) {
            getSpirit("当前的精神值为：" + getSpiritValue(player));
        }
    }

    private void getSpirit(String player) {
        Minecraft.getInstance().gui.setOverlayMessage(Component.literal(player), false);
    }
}
