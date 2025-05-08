package ctn.project_moon.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

public class PlayerAttributeMenu extends AbstractContainerMenu {
    private Inventory playerInventory;
    private Player player;

    public PlayerAttributeMenu(int containerId, Inventory playerInventory) {
        super(PLAYER_ATTRIBUTE_MENU.get(), containerId);
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;
    }

    public PlayerAttributeMenu(int containerId, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(containerId, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
