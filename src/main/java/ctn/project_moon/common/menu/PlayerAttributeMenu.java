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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ctn.project_moon.datagen.DatagenCuriosTest.*;
import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

/**
 * @author 小尽
 */
public class PlayerAttributeMenu extends AbstractContainerMenu {
	private final Player player;
	public final ICuriosItemHandler curiosHandler;
	private final List<ProxySlot> proxySlots = new ArrayList<>();
	private int moveToPage = - 1;
	private int moveFromIndex = - 1;
	/** 是否开关装饰饰品 */
	public        boolean                         isViewingCosmetics;

	public PlayerAttributeMenu(int containerId, Inventory playerInventory) {
		super(PLAYER_ATTRIBUTE_MENU.get(), containerId);
		this.player = playerInventory.player;
		this.curiosHandler = CuriosApi.getCuriosInventory(this.player).orElse(null);
		setSlot();
	}

	protected int findAvailableSlot(ItemStack stack) {
		int result = -1;

		if (stack.isStackable()) {

			for (ProxySlot proxySlot : this.proxySlots) {
				Slot slot = proxySlot.slot();
				ItemStack itemstack = slot.getItem();

				if (!itemstack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemstack)) {
					int j = itemstack.getCount() + stack.getCount();
					int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());

					if (j <= maxSize || itemstack.getCount() < maxSize) {
						result = proxySlot.page();
						break;
					}
				}
			}
		}

		if (!stack.isEmpty() && result == -1) {

			for (ProxySlot proxySlot : this.proxySlots) {
				Slot slot1 = proxySlot.slot();
				ItemStack itemstack1 = slot1.getItem();
				if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
					result = proxySlot.page();
					break;
				}
			}
		}
		return result;
	}

	/** 设置物品槽位 */
	private void setSlot() {
		this.slots.clear();
		// 添加快捷栏
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(player.getInventory(), column, 30 + column * 18, 158));
		}
		// 添加背包
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(player.getInventory(),
						column + (row + 1) * 9,
						30 + 18 * column,
						100 + 18 * row));
			}
		}

		// 添加副手
		addSlot(new Slot(player.getInventory(), 40, 6, 158) {
			@OnlyIn(Dist.CLIENT)
			@Override
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
			}
		});
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
							x = 29;
							y = 24;
							break;
						case HEAD_CURIOS:
							x = 29;
							y = 41;
							break;
						case HINDBRAIN_CURIOS:
							x = 98;
							y = 24;
							break;
						case EYE_AREA_CURIOS:
							x = 98;
							y = 41;
							break;
						case FACE_CURIOS:
							x = 98;
							y = 58;
							break;
						case CHEEK_CURIOS:
							x = 98;
							y = 75;
							break;
						case MASK_CURIOS:
							x = 29;
							y = 58;
							break;
						case MOUTH_CURIOS:
							x = 29;
							y = 75;
							break;
						case NECK_CURIOS:
							x = 6;
							y = 24;
							break;
						case CHEST_CURIOS:
							x = 6;
							y = 41;
							break;
						case HAND_CURIOS:
							x = 6;
							y = 58;
							break;
						case GLOVE_CURIOS:
							x = 6;
							y = 75;
							break;
						case RIGHT_BACK_CURIOS:
							x = 6;
							y = 92;
							break;
						case LEFT_BACK_CURIOS:
							x = 6;
							y = 109;
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
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int quickMovedSlotIndex) {
		if (this.slots == null) return ItemStack.EMPTY;

		Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
		if (quickMovedSlot == null || !quickMovedSlot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack rawStack = quickMovedSlot.getItem();
		if (rawStack.isEmpty()) return ItemStack.EMPTY;

		final int QUICK_BAR_SLOT_HEAD = 0;
		final int QUICK_BAR_SLOT_END = 9;
		final int DEPUTY_HAND_SLOT = 36;
		final int CURIO_BAR_SLOT_HEAD = 37;
		final int CURIO_BAR_SLOT_END = this.slots.size();

		if (quickMovedSlotIndex > DEPUTY_HAND_SLOT) {
			if (!this.moveItemStackTo(rawStack, QUICK_BAR_SLOT_HEAD, DEPUTY_HAND_SLOT, false)) {
				return ItemStack.EMPTY;
			}
		} else if (quickMovedSlotIndex < QUICK_BAR_SLOT_END) {
			if (tryMoveStack(rawStack, CURIO_BAR_SLOT_HEAD, CURIO_BAR_SLOT_END, QUICK_BAR_SLOT_END, DEPUTY_HAND_SLOT)) {
				return ItemStack.EMPTY;
			}
		} else if (quickMovedSlotIndex < DEPUTY_HAND_SLOT) {
			if (tryMoveStack(rawStack, CURIO_BAR_SLOT_HEAD, CURIO_BAR_SLOT_END, QUICK_BAR_SLOT_HEAD, QUICK_BAR_SLOT_END)) {
				return ItemStack.EMPTY;
			}
		} else {
			if (tryMoveStack(rawStack, CURIO_BAR_SLOT_HEAD, CURIO_BAR_SLOT_END, QUICK_BAR_SLOT_END, DEPUTY_HAND_SLOT, QUICK_BAR_SLOT_HEAD, QUICK_BAR_SLOT_END)) {
				return ItemStack.EMPTY;
			}
		}

		quickMovedSlot.setChanged(); // 确保槽位变化被通知
		return rawStack;
	}

	/**
	 * 尝试在指定的范围内移动物品堆栈
	 * <p>
	 * 此方法通过遍历指定的范围对物品堆栈进行移动操作如果堆栈可以移动到任一指定范围内，
	 * 则移动成功，否则返回true表示移动失败
	 *
	 * @param stack 要移动的物品堆栈
	 * @param ranges 可变参数，表示要移动到的范围起始和结束位置的数组，必须成对出现
	 * @return 如果无法在指定范围内移动物品堆栈，则返回true；否则返回false
	 */
	private boolean tryMoveStack(ItemStack stack, Integer... ranges) {
		if (ranges.length % 2 != 0) {
			throw new IllegalArgumentException("Must appear in pairs.");
		}
		// 遍历每个范围对物品堆栈进行移动尝试
		for (int i = 0; i < ranges.length; i += 2) {
			// 获取当前范围的起始位置
			int start = ranges[i];
			// 获取当前范围的结束位置
			int end = ranges[i + 1];
			// 尝试在当前范围内移动物品堆栈如果移动成功，则返回false表示移动操作执行
			if (this.moveItemStackTo(stack, start, end, false)) {
				return false;
			}
		}
		// 如果所有范围都尝试过，但物品堆栈都无法移动，则返回true
		return true;
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}

	private record ProxySlot(int page, Slot slot) {

	}
}
