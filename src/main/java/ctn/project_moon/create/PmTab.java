package ctn.project_moon.create;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmTab extends CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> PROJECT_MOON_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_WEAPON =
            register("ego_weapon",
                    CreativeModeTabs.COMBAT, () -> PmItems.EGO_WEAPON_ICON.get().getDefaultInstance(),
                    (parameters, output) -> {
                            output.accept(PmItems.DETONATING_BATON.get());
                            output.accept(PmItems.WRIST_CUTTER.get());
                            output.accept(PmItems.BEAR_PAWS.get());
                            output.accept(PmItems.IN_THE_NAME_OF_LOVE_AND_HATE.get());
                            output.accept(PmItems.PARADISE_LOST.get());
                    });
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_SUIT =
            register("ego_suit",
                    CreativeModeTabs.COMBAT, () -> PmItems.EGO_SUIT_ICON.get().getDefaultInstance(),
                    (parameters, output) -> {

                    });
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_CURIOS =
            register("ego_curios", CreativeModeTabs.COMBAT, () -> PmItems.EGO_CURIOS_ICON.get().getDefaultInstance(),
                    (parameters, output) -> {

                    });

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> register(
            String name,
            ResourceKey<CreativeModeTab> withTabsBefore,
            Supplier<ItemStack> icon) {
        return register(name, withTabsBefore, icon, (parameters, output) -> {});
    }

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> register(
            String name,
            ResourceKey<CreativeModeTab> withTabsBefore,
            Supplier<ItemStack> icon,
            CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
        return PROJECT_MOON_TAB.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + MOD_ID + "." + name))
                .withTabsBefore(withTabsBefore)
                .icon(icon)
                .displayItems(displayItemsGenerator)
                .withSearchBar()
                .build());
    }
}
