package ctn.project_moon.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.client.gui.widget.RatingWidget;
import ctn.project_moon.client.gui.widget.SwitchButton;
import ctn.project_moon.client.gui.widget.player_attribute.CompositeWidget;
import ctn.project_moon.client.gui.widget.player_attribute.CurioCosmeticButton;
import ctn.project_moon.client.gui.widget.player_attribute.CurioRenderButton;
import ctn.project_moon.client.gui.widget.player_attribute.ToggleCapacityButton;
import ctn.project_moon.common.menu.PlayerAttributeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
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
import static ctn.project_moon.api.FourColorAttribute.*;

@OnlyIn(Dist.CLIENT)
public class PlayerAttributeScreen extends EffectRenderingInventoryScreen<PlayerAttributeMenu> implements ICuriosScreen {
	public static final ResourceLocation GUI = getResourceLocation("textures/gui/container/player_attribute.png");
	private boolean isRenderButtonHovered;
	private boolean isCapacity = true;
	private RatingWidget[] ratingImages;
	private RatingWidget compositeRating;
	private Player player;
	public static final ResourceLocation[] ATTRIBUTE = {
			getResourceLocation("textures/gui/sprites/player_attribute/fortitude.png"),
			getResourceLocation("textures/gui/sprites/player_attribute/prudence.png"),
			getResourceLocation("textures/gui/sprites/player_attribute/temperance.png"),
			getResourceLocation("textures/gui/sprites/player_attribute/justice.png")
	};
	public static final ResourceLocation[] RESISTANCE = {
			getResourceLocation("textures/particle/physics_particle.png"),
			getResourceLocation("textures/particle/spirit_particle.png"),
			getResourceLocation("textures/particle/erosion_particle.png"),
			getResourceLocation("textures/particle/the_soul_particle.png")
	};
	public static final String[] ATTRIBUTE_TOOLTIP = {
			FourColorAttribute.Type.FORTITUDE.getSerializedName(),
			FourColorAttribute.Type.PRUDENCE.getSerializedName(),
			FourColorAttribute.Type.TEMPERANCE.getSerializedName(),
			FourColorAttribute.Type.JUSTICE.getSerializedName()
	};
	public static final String[] RESISTANCE_TOOLTIP = {
			MOD_ID+ ".gui.player_attribute.physics.message",
			MOD_ID+ ".gui.player_attribute.spirit.message",
			MOD_ID+ ".gui.player_attribute.erosion.message",
			MOD_ID+ ".gui.player_attribute.the_soul.message"
	};


	public PlayerAttributeScreen(PlayerAttributeMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		player = playerInventory.player;
	}

	// 取消原版的文本标签
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	/** 初始化 */
	@Override
	public void init() {
		imageWidth = 198;
		imageHeight = 182;
		leftPos = (this.width - this.imageWidth) / 2 - 11;
		topPos = (this.height - this.imageHeight) / 2 - 8;
		ratingImages = new RatingWidget[]{
				new RatingWidget(leftPos + 141, topPos + 25, false),
				new RatingWidget(leftPos + 141, topPos + 42, false),
				new RatingWidget(leftPos + 141, topPos + 59, false),
				new RatingWidget(leftPos + 141, topPos + 76, false)
		};
		compositeRating = new RatingWidget(leftPos + 92, topPos + 2, true);
		for (Slot inventorySlot : this.menu.slots) {
			if (inventorySlot instanceof CurioSlot curioSlot && !(inventorySlot instanceof CosmeticCurioSlot) && curioSlot.canToggleRender()) {
				this.addRenderableWidget(new CurioRenderButton(this, curioSlot, GUI,
						this.leftPos + inventorySlot.x + 12,
						this.topPos + inventorySlot.y + 12,
						5, 5,
						199, 31,
						(button) -> PacketDistributor.sendToServer(new CPacketToggleRender(curioSlot.getIdentifier(), inventorySlot.getSlotIndex()))));
			}
		}
		for (int i = 0; i < 4; i++) {
			int width = 16;
			int height = 16;
			ImageWidget imageWidget1 = ImageWidget.texture(width, height, ATTRIBUTE[i], width, height);
			imageWidget1.setMessage(Component.translatable(ATTRIBUTE_TOOLTIP[i]));
			ImageWidget imageWidget2 = ImageWidget.texture(width, height, RESISTANCE[i], width, height);
			imageWidget2.setMessage(Component.translatable(RESISTANCE_TOOLTIP[i]));
			CompositeWidget<ImageWidget> compositeWidget = new CompositeWidget<>(
					this.leftPos + 123,
					this.topPos + 24 + ((16 + 1) * i),
					width, height,
					imageWidget1,
					imageWidget2);
			addRenderableWidget(compositeWidget);
			addRenderableWidget(ratingImages[i]);
		}
		addRenderableWidget(compositeRating);
		addRenderableWidget(new ToggleCapacityButton(this, GUI,
				this.leftPos + 178, this.topPos + 8,
				12, 12, 199, 18));
		addRenderableWidget(new CurioCosmeticButton(this, GUI,
				this.leftPos + 10, this.topPos + 11,
				11, 6, 199, 37));
	}

	/** 绘制背景 */
	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		if (this.minecraft != null && this.minecraft.player != null) {
			guiGraphics.blit(GUI, leftPos, topPos,
					imageWidth, imageHeight,
					0, 0,
					imageWidth, imageHeight,
					256, 256);
			CuriosApi.getCuriosInventory(this.minecraft.player).ifPresent((handler) -> {
				for (Slot inventorySlot : this.menu.slots) {
					if (!(inventorySlot instanceof CurioSlot curioSlot) || !curioSlot.isCosmetic()) {
						continue;
					}
					RenderSystem.enableBlend();
					guiGraphics.blit(GUI,
							leftPos + inventorySlot.x, topPos + inventorySlot.y,
							199, 79, 16, 16);
					RenderSystem.disableBlend();
				}
			});
			InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics,
					leftPos + 47, topPos + 24,
					leftPos + 96, topPos + 91,
					30, 0.0625F,
					mouseX, mouseY, this.minecraft.player);
		}
	}


//	@Override
//	protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
//		super.renderSlot(guiGraphics, slot);
//
//	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		compositeRating.setRating(getCompositeRatting(player)-1);
		if (isCapacity) {
			ratingImages[0].setRating(getFortitudeRating(player) - 1);
			ratingImages[1].setRating(getPrudenceRating(player) - 1);
			ratingImages[2].setRating(getTemperanceRating(player) - 1);
			ratingImages[3].setRating(getJusticeRating(player) - 1);
		}
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		isRenderButtonHovered = false;
		for (Renderable renderable : this.renderables) {
			if (renderable instanceof SwitchButton button) {
				button.renderWidgetOverlay(guiGraphics, mouseX, mouseY, partialTick);
				if (button.isHovered()) {
					isRenderButtonHovered = true;
				}
			}
		}
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderTooltip(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		super.renderTooltip(guiGraphics, mouseX, mouseY);
		LocalPlayer clientPlayer = Minecraft.getInstance().player;

		if (!isRenderButtonHovered && clientPlayer != null &&
				clientPlayer.inventoryMenu.getCarried().isEmpty() &&
				getSlotUnderMouse() != null) {
			Slot slot = getSlotUnderMouse();
			if (slot instanceof CurioSlot slotCurio && minecraft != null) {
				ItemStack stack = slotCurio.getSlotExtension().getDisplayStack(slotCurio.getSlotContext(), slot.getItem());
				if (stack.isEmpty()) {
					List<Component> slotTooltips = slotCurio.getSlotExtension()
							.getSlotTooltip(slotCurio.getSlotContext(), ClientTooltipFlag.of(this.minecraft.options.advancedItemTooltips
									? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL));

					if (!slotTooltips.isEmpty()) {
						guiGraphics.renderComponentTooltip(font, slotTooltips, mouseX, mouseY);
					} else {
						guiGraphics.renderTooltip(font, Component.literal(slotCurio.getSlotName()), mouseX, mouseY);
					}
				}
			}
		}

	}

	private static @NotNull ResourceLocation getResourceLocation(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	/** 切换属性或抗性 */
	public void toggleCapacity() {
		isCapacity = !isCapacity;
		for (Renderable renderable : this.renderables) {
			if (renderable instanceof CompositeWidget button){
				button.setState(isCapacity);
			}
		}
		for (int i = 0; i < 4; i++) {
			ratingImages[i].visible = isCapacity;
		}
	}
}
