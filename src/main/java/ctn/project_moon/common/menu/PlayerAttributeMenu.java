package ctn.project_moon.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;

import java.util.Map;

import static ctn.project_moon.datagen.DatagenCuriosTest.*;
import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

public class PlayerAttributeMenu extends AbstractContainerMenu {
	private final Player player;
	public final ICuriosItemHandler curiosHandler;
	/** 是否开关装饰饰品 */
	public boolean isViewingCosmetics;

	public PlayerAttributeMenu(int containerId, Inventory playerInventory) {
		super(PLAYER_ATTRIBUTE_MENU.get(), containerId);
		this.player = playerInventory.player;
		this.curiosHandler = CuriosApi.getCuriosInventory(this.player).orElse(null);
		setSlot();
	}

	/** 设置物品槽位 */
	private void setSlot() {
		this.slots.clear();
		// 添加副手
		addSlot(new Slot(player.getInventory(), 40, 6, 157) {
			@OnlyIn(Dist.CLIENT)
			@Override
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
			}
		});
		// 添加快捷栏
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(player.getInventory(), column, 29 + column * 18, 157));
		}
		// 添加背包
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(player.getInventory(),
						column + (row + 1) * 9,
						29 + 18 * column,
						99 + 18 * row));
			}
		}

		// 添加饰品槽位 没做多同饰品处理
		addCuriosSlots:
		{
			if (this.curiosHandler != null) {
				Map<String, ICurioStacksHandler> curioMap = this.curiosHandler.getCurios();
				for (String identifier : curioMap.keySet()) {
					int x, y;
					boolean isCosmetic = false;
					ICurioStacksHandler stacksHandler = curioMap.get(identifier);
					IDynamicStackHandler stackHandler = stacksHandler.getStacks();
					if (this.isViewingCosmetics) {
						isCosmetic = true;
						stackHandler = stacksHandler.getCosmeticStacks();
					}
					switch (identifier) {
						case HEADWEAR_CURIOS:
							x = 28;
							y = 23;
							break;
						case HEAD_CURIOS:
							x = 28;
							y = 40;
							break;
						case HINDBRAIN_CURIOS:
							x = 97;
							y = 23;
							break;
						case EYE_AREA_CURIOS:
							x = 97;
							y = 40;
							break;
						case FACE_CURIOS:
							x = 97;
							y = 57;
							break;
						case CHEEK_CURIOS:
							x = 97;
							y = 74;
							break;
						case MASK_CURIOS:
							x = 28;
							y = 57;
							break;
						case MOUTH_CURIOS:
							x = 28;
							y = 74;
							break;
						case NECK_CURIOS:
							x = 5;
							y = 23;
							break;
						case CHEST_CURIOS:
							x = 5;
							y = 40;
							break;
						case HAND_CURIOS:
							x = 5;
							y = 57;
							break;
						case GLOVE_CURIOS:
							x = 5;
							y = 74;
							break;
						case RIGHT_BACK_CURIOS:
							x = 5;
							y = 91;
							break;
						case LEFT_BACK_CURIOS:
							x = 5;
							y = 108;
							break;
						default:
							break addCuriosSlots;
					}
					for (int i = 0; i < stackHandler.getSlots(); i++) {
						this.addSlot(new CurioSlot(this.player,
								stackHandler, i, identifier, x, y,
								stacksHandler.getRenders(), stacksHandler.canToggleRendering(),
								false, isCosmetic));
					}
				}
			}
		}
	}

	/** 切换为装饰饰品 */
	public void toggleCosmetics() {
		this.isViewingCosmetics = !this.isViewingCosmetics;
		this.resetSlots();
	}

	public void resetSlots() {
		this.setSlot();
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
//
//		if (slot.hasItem()) {
//			ItemStack itemstack1 = slot.getItem();
//			itemstack = itemstack1.copy();
//			EquipmentSlot entityequipmentslot = player.getEquipmentSlotForItem(itemstack);
//			if (index == 0) {
//
//				if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
//					return ItemStack.EMPTY;
//				}
//				slot.onQuickCraft(itemstack1, itemstack);
//			} else if (index < 5) {
//
//				if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (index < 9) {
//
//				if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (entityequipmentslot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR
//					&& !this.slots.get(8 - entityequipmentslot.getIndex()).hasItem()) {
//				int i = 8 - entityequipmentslot.getIndex();
//
//				if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
//					return ItemStack.EMPTY;
//				}
//			}/* else if (index < 46 &&
//					!CuriosApi.getItemStackSlots(itemstack, player.level()).isEmpty()) {
//
//				if (!this.moveItemStackTo(itemstack1, 46, this.slots.size(), false)) {
//					int page = this.findAvailableSlot(itemstack1);
//
//					if (page != -1) {
//						this.moveToPage = page;
//						this.moveFromIndex = index;
//					} else {
//						return ItemStack.EMPTY;
//					}
//				}
//			} */else if (entityequipmentslot == EquipmentSlot.OFFHAND && !(this.slots.get(45))
//					.hasItem()) {
//
//				if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (index < 36) {
//				if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (index < 45) {
//				if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
//				return ItemStack.EMPTY;
//			}
//
//			if (itemstack1.isEmpty()) {
//				slot.set(ItemStack.EMPTY);
//			} else {
//				slot.setChanged();
//			}
//
//			if (itemstack1.getCount() == itemstack.getCount()) {
//				return ItemStack.EMPTY;
//			}
//			slot.onTake(player, itemstack1);
//
//			if (index == 0) {
//				player.drop(itemstack1, false);
//			}
//		}

		return itemstack;
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}
}
