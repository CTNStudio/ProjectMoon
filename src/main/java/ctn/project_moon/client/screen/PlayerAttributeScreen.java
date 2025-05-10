package ctn.project_moon.client.screen;

import ctn.project_moon.client.gui.widget.CurioCosmeticButton;
import ctn.project_moon.client.gui.widget.CurioRenderButton;
import ctn.project_moon.client.gui.widget.SwitchButton;
import ctn.project_moon.common.menu.PlayerAttributeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientTooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.ICuriosScreen;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;

import javax.annotation.Nonnull;
import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeScreen extends EffectRenderingInventoryScreen<PlayerAttributeMenu> implements ICuriosScreen {
	public static final ResourceLocation GUI = getResourceLocation("textures/gui/container/player_attribute.png");
	private boolean isRenderButtonHovered;

	public PlayerAttributeScreen(PlayerAttributeMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	// 取消原版的文本标签
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	/** 初始化 */
	@Override
	public void init() {
		imageWidth = 197;
		imageHeight = 181;
		leftPos = (this.width - this.imageWidth) / 2 - 10;
		topPos = (this.height - this.imageHeight) / 2 - 7;
		for (Slot inventorySlot : this.menu.slots) {
			if (inventorySlot instanceof CurioSlot curioSlot && !(inventorySlot instanceof CosmeticCurioSlot) && curioSlot.canToggleRender()) {
				this.addRenderableWidget(new CurioRenderButton(curioSlot, GUI,
						this.leftPos + inventorySlot.x + 12,
						this.topPos + inventorySlot.y + 12,
						5, 5,
						198, 30,
						(button) -> PacketDistributor.sendToServer(new CPacketToggleRender(curioSlot.getIdentifier(), inventorySlot.getSlotIndex()))));
			}
		}
		addRenderableWidget(new CurioCosmeticButton(this, GUI,
				this.leftPos + 9, this.topPos + 7,
				11, 6,
				198, 36));
	}

	/** 绘制背景 */
	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		if (this.minecraft != null && this.minecraft.player != null) {
			guiGraphics.blit(GUI, leftPos, topPos,
					197, 181,
					0, 0,
					197, 181,
					256, 256);
			CuriosApi.getCuriosInventory(this.minecraft.player).ifPresent((handler) -> {
			});
			InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics,
					leftPos + 46, topPos + 23,
					leftPos + 95, topPos + 90,
					30, 0.0625F,
					mouseX, mouseY, this.minecraft.player);
		}
	}

	//
//	@Override
//	protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
//		super.renderSlot(guiGraphics, slot);
//	}
//
	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		boolean isButtonHovered = false;

		for (Renderable renderable : this.renderables) {
			if (renderable instanceof SwitchButton button) {
				button.renderWidgetOverlay(guiGraphics, mouseX, mouseY, partialTick);
				if (button.isHovered()) {
					isButtonHovered = true;
				}
			}
		}
		isRenderButtonHovered = isButtonHovered;
		LocalPlayer clientPlayer = Minecraft.getInstance().player;

		if (!isRenderButtonHovered &&
				clientPlayer != null &&
				clientPlayer.inventoryMenu.getCarried().isEmpty() &&
				getSlotUnderMouse() != null) {
			Slot slot = getSlotUnderMouse();
			if (slot instanceof CurioSlot slotCurio && minecraft != null) {
				ItemStack stack = slotCurio.getSlotExtension().getDisplayStack(slotCurio.getSlotContext(), slot.getItem());
				if (stack.isEmpty()) {
					List<Component> slotTooltips = slotCurio.getSlotExtension().getSlotTooltip(slotCurio.getSlotContext(), ClientTooltipFlag.of(this.minecraft.options.advancedItemTooltips
							? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL));

					if (!slotTooltips.isEmpty()) {
						guiGraphics.renderComponentTooltip(font, slotTooltips, mouseX, mouseY);
					} else {
						guiGraphics.renderTooltip(
								font, Component.literal(slotCurio.getSlotName()), mouseX, mouseY);
					}
				}
			}
		}

		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderTooltip(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		Minecraft minecraft = this.minecraft;

		if (minecraft != null) {
			LocalPlayer clientPlayer = minecraft.player;

			if (clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty()) {

				if (this.isRenderButtonHovered) {
					guiGraphics.renderTooltip(this.font, Component.translatable("gui.curios.toggle"), mouseX, mouseY);
				} else if (this.hoveredSlot != null) {
					ItemStack stack = this.hoveredSlot.getItem();

					if (this.hoveredSlot instanceof CurioSlot curioSlot) {
						stack = curioSlot.getSlotExtension().getDisplayStack(curioSlot.getSlotContext(), stack);
					}

					if (!stack.isEmpty()) {
						guiGraphics.renderTooltip(this.font, stack, mouseX, mouseY);
					}
				}
			}
		}
	}

	private static @NotNull ResourceLocation getResourceLocation(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
