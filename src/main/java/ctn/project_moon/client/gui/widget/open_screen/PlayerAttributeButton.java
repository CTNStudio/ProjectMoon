package ctn.project_moon.client.gui.widget.open_screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.PmPayloadTool.openPlayerAttributeScreen;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public final class PlayerAttributeButton extends OpenScreenButton {
	public static final String                     MESSAGE = MOD_ID + ".gui.player_attribute_button.message";
	public static final WidgetSprites              DEFAULT = new WidgetSprites(getPath("button"), getPath("button_press"));
	
	public PlayerAttributeButton(AbstractContainerScreen<?> parentGui) {
		super(
				4, 4, 12, 12, DEFAULT, (button) -> {
					Minecraft minecraft = Minecraft.getInstance();
					LocalPlayer player = minecraft.player;
					if (player != null) {
						ItemStack stack = player.containerMenu.getCarried();
						player.containerMenu.setCarried(ItemStack.EMPTY);
						openPlayerAttributeScreen(stack);
					}
				}, Component.translatable(MESSAGE), parentGui);
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_attribute/" + path);
	}
}
