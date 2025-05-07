package ctn.project_moon.client.screen;

import ctn.project_moon.common.menu.PlayerAttributeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuConstructor;
import org.jetbrains.annotations.Nullable;

import static ctn.project_moon.PmMain.MOD_ID;

public class PlayerAttributeScreen extends EffectRenderingInventoryScreen<PlayerAttributeMenu> implements MenuProvider {
//    private final Component title;
//    private final MenuConstructor menuConstructor;

    public PlayerAttributeScreen(PlayerAttributeMenu menu, Player player) {
        super(menu, player.getInventory(), Component.translatable("menu.title" + MOD_ID + ".player_attribute_menu"));
//        this.menuConstructor = menuConstructor;
//        this.title = title;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return null;
    }
}
