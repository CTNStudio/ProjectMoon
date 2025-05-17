package ctn.project_moon.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

/**
 * @author 小尽
 */
@OnlyIn(Dist.CLIENT)
public class PlayerAttributeMenu extends AbstractContainerMenu {
	private final Player player;

	public PlayerAttributeMenu(int containerId, Inventory playerInventory) {
		super(PLAYER_ATTRIBUTE_MENU.get(), containerId);
		this.player = playerInventory.player;
		this.slots.clear();
		// 添加快捷栏
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
		}
		// 添加背包
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory,
						column + (row + 1) * 9,
						8 + 18 * column,
						84 + 18 * row));
			}
		}
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		if (index < 0) {
			return ItemStack.EMPTY;
		}
		Slot quickMovedSlot = this.slots.get(index);
		if (!quickMovedSlot.hasItem()) {
			return ItemStack.EMPTY;
		}
		ItemStack rawStack = quickMovedSlot.getItem();
		if (rawStack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		if (index < 9) {
			if (!moveItemStackTo(rawStack, 9, slots.size(), false)) {
				return ItemStack.EMPTY;
			}
		} else {
			if (!moveItemStackTo(rawStack, 0, 9, false)) {
				return ItemStack.EMPTY;
			}
		}

		quickMovedSlot.setChanged();
		return rawStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	public Player getPlayer() {
		return player;
	}
}
