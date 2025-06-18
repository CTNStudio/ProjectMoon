package ctn.project_moon.init;

import ctn.project_moon.common.menu.PlayerAttributeMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

/** 菜单类型 */
public class PmMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);
	
	private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String key, MenuType.MenuSupplier<T> factory) {
		return MENU_TYPE_REGISTER.register(key, () -> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
	}
	
	public static final Supplier<MenuType<PlayerAttributeMenu>> PLAYER_ATTRIBUTE_MENU = register("player_attribute_menu", PlayerAttributeMenu::new);
	
	
}
