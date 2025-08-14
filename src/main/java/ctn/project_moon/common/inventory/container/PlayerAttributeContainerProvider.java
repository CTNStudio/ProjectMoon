package ctn.project_moon.common.inventory.container;

import ctn.project_moon.common.menu.PlayerAttributeMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * 玩家技能容器提供程序
 */
public class PlayerAttributeContainerProvider implements MenuProvider {
	
	@Nonnull
	@Override
	public Component getDisplayName() {
		return Component.empty();
	}
	
	@CheckForNull
	@Override
	public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory,
			@Nonnull Player playerEntity) {
		return new PlayerAttributeMenu(i, playerInventory);
	}
}
