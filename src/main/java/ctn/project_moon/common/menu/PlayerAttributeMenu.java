package ctn.project_moon.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

public class PlayerAttributeMenu extends AbstractContainerMenu {
    private final Inventory playerInventory;
    private final Player player;

    public PlayerAttributeMenu(int containerId, Inventory playerInventory) {
        super(PLAYER_ATTRIBUTE_MENU.get(), containerId);
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;
    }

    public PlayerAttributeMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(containerId, playerInventory);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
