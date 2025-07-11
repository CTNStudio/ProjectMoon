package ctn.project_moon.client.gui.widget.player_attribute;

import ctn.project_moon.common.payloadInit.data.OpenPlayerAttributeScreenData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public final class PlayerAttributeButton extends ImageButton {
	public static final String                     MESSAGE = MOD_ID + ".gui.player_attribute_button.message";
	public static final WidgetSprites              DEFAULT = new WidgetSprites(getPath("button"), getPath("button_press"));
	private final       AbstractContainerScreen<?> parentGui;
	
	public PlayerAttributeButton(AbstractContainerScreen<?> parentGui) {
		super(
				12, 12, DEFAULT, (button) -> {
					Minecraft minecraft = Minecraft.getInstance();
					LocalPlayer player = minecraft.player;
					if (player != null) {
						ItemStack stack = player.containerMenu.getCarried();
						player.containerMenu.setCarried(ItemStack.EMPTY);
						PacketDistributor.sendToServer(new OpenPlayerAttributeScreenData(stack));
					}
				}, Component.translatable(MESSAGE));
		this.parentGui = parentGui;
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_attribute/" + path);
	}
	
	@Override
	public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY,
			float partialTicks) {
		if (!(parentGui instanceof InventoryScreen ||
		      parentGui instanceof CreativeModeInventoryScreen creativeInventory &&
		      creativeInventory.isInventoryOpen())) {
			this.active = false;
			return;
		}
		this.active = true;
		int x = switch (parentGui) {
			case InventoryScreen ignored -> parentGui.getXSize() - getWidth() - 4;
			case CreativeModeInventoryScreen ignored -> parentGui.getXSize() - getWidth() - 4;
			default -> 4;
		};
		this.setX(parentGui.getGuiLeft() + x);
		this.setY(parentGui.getGuiTop() + 4);
		super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
		if (isHovered()) {
			guiGraphics.renderTooltip(parentGui.getMinecraft().font, getMessage(), mouseX, mouseY);
		}
	}
}
