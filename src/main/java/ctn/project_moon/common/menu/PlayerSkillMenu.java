package ctn.project_moon.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.init.PmMenuTypes.PLAYER_SKILL_MENU;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PlayerSkillMenu extends AbstractContainerMenu {
	private final Player player;
	
	public PlayerSkillMenu(int containerId, Inventory playerInventory) {
		super(PLAYER_SKILL_MENU.get(), containerId);
		this.player = playerInventory.player;
	}
	
	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}
	
	public Player getPlayer() {
		return player;
	}
}
