package ctn.project_moon.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

/** 创造模式物品栏 */
public class PmCreativeModeTab extends CreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> PROJECT_MOON_TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_WEAPON =
			register("ego_weapon", (name) -> createCreativeModeTab(name,
					(parameters, output) -> {
						output.accept(PmItems.DETONATING_BATON.get());
						output.accept(PmItems.WRIST_CUTTER.get());
						output.accept(PmItems.BEAR_PAWS.get());
						output.accept(PmItems.LOVE_HATE.get());
						output.accept(PmItems.PARADISE_LOST.get());
					}, () -> PmItems.EGO_WEAPON_ICON.get().getDefaultInstance()));

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_SUIT =
			register("ego_suit", (name) -> createCreativeModeTab(name,
					(parameters, output) -> {
						output.accept(PmItems.SUIT);
						output.accept(PmItems.DRESS_PANTS);
						output.accept(PmItems.LOAFERS);
					}, () -> PmItems.EGO_SUIT_ICON.get().getDefaultInstance()));

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_CURIOS =
			register("ego_curios", (name) -> createCreativeModeTab(name,
					(parameters, output) -> {

					}, () -> PmItems.EGO_CURIOS_ICON.get().getDefaultInstance()));

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TOOL =
			register("creative_tool", (name) -> createCreativeModeTab(name,
					(parameters, output) -> {
						output.accept(PmItems.CREATIVE_SPIRIT_TOOL.get());
						output.accept(PmItems.CHAOS_SWORD.get());
					}, () -> PmItems.CREATIVE_TOOL_ICON.get().getDefaultInstance()));


	public static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, Function<String, CreativeModeTab.Builder> builder) {
		return PROJECT_MOON_TAB_REGISTER.register(name, builder.apply(name)::build);
	}

	public static CreativeModeTab.Builder createCreativeModeTab(
			String name,
			CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
			Supplier<ItemStack> icon,
			ResourceKey<CreativeModeTab> withTabsBefore) {
		return createCreativeModeTab(name, displayItemsGenerator, icon).withTabsBefore(withTabsBefore);
	}

	public static CreativeModeTab.Builder createCreativeModeTab(
			String name,
			CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
			Supplier<ItemStack> icon) {
		return createCreativeModeTab(name, displayItemsGenerator).icon(icon);
	}

	public static CreativeModeTab.Builder createCreativeModeTab(String name, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
		return CreativeModeTab.builder().title(getComponent(name)).displayItems(displayItemsGenerator);
	}

	private static @NotNull MutableComponent getComponent(String imagePath) {
		return Component.translatable("itemGroup." + MOD_ID + "." + imagePath);
	}
}
