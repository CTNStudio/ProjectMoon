package ctn.project_moon.client.gui.widget.open_screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.tool.PmPayloadTool.openPlayerSkillScreen;

/**
 * @author 尽
 */
public final class PlayerSkillButton extends OpenScreenButton {
	public static final String                     MESSAGE = MOD_ID + ".gui.player_skill.message";
	public static final WidgetSprites              DEFAULT = new WidgetSprites(getPath("button"), getPath("button_press"));
	
	public PlayerSkillButton(AbstractContainerScreen<?> parentGui) {
		super(
				17, 4, 12, 12, DEFAULT, (button) -> {
					Minecraft minecraft = Minecraft.getInstance();
					LocalPlayer player = minecraft.player;
					if (player != null) {
						ItemStack stack = player.containerMenu.getCarried();
						player.containerMenu.setCarried(ItemStack.EMPTY);
						openPlayerSkillScreen(stack);
					}
				}, Component.translatable(MESSAGE), parentGui);
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_skill/" + path);
	}
}
