package ctn.project_moon.init;

import ctn.project_moon.common.item.CreativeSpiritToolItem;
import ctn.project_moon.common.item.weapon.ChaosKnifeItem;
import ctn.project_moon.common.item.weapon.DetonatingBatonItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.item.weapon.ego.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredItem<Item> EGO_CURIOS_ICON = registerSimpleIconItem("ego_curios_icon");
    public static final DeferredItem<Item> EGO_SUIT_ICON = registerSimpleIconItem("ego_suit_icon");
    public static final DeferredItem<Item> EGO_WEAPON_ICON = registerSimpleIconItem("ego_weapon_icon");
    public static final DeferredItem<Item> CREATIVE_TOOL_ICON = registerSimpleIconItem("creative_tool_icon");

    public static final DeferredItem<Item> CREATIVE_SPIRIT_TOOL = creativeToolItem("creative_spirit_tool", CreativeSpiritToolItem::new);
    public static final DeferredItem<Item> CHAOS_SWORD = creativeToolItem("chaos_sword", ChaosKnifeItem::new);

    public static final DeferredItem<Item> DETONATING_BATON = createWeaponItem("detonating_baton", DetonatingBatonItem::new, new Weapon.Builder(3F, 4F, -2.4F));
    public static final DeferredItem<Item> WRIST_CUTTER = createEgoWeaponItem("wrist_cutter", WristCutterItem::new, new Weapon.Builder(2F, 3F, 0.2f));
    public static final DeferredItem<Item> BEAR_PAWS = createEgoWeaponItem("bear_paws", BearPawsItem::new, new Weapon.Builder(7F, 7F, -1F));
    // 原称 in the name of love and hate
    public static final DeferredItem<Item> LOVE_HATE = createEgoWeaponItem("love_hate", LoveHateItem::new, new Weapon.Builder(3F, 5F, -2F));
    public static final DeferredItem<Item> PARADISE_LOST = createEgoWeaponItem("paradise_lost", ParadiseLostItem::new, new Weapon.Builder(12F, 16F, -2.3F));

    public static DeferredItem<Item> registerSimpleItem(String name, Item.Properties props) {
        return ITEMS.registerSimpleItem(name, props);
    }

    public static DeferredItem<BlockItem> registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(name, block);
    }

    public static DeferredItem<Item> registerSimpleIconItem(String name) {
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> creativeToolItem(String name) {
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> creativeToolItem(String name, Function<Item.Properties, ? extends Item> item) {
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item, Item.Properties properties) {
        return ITEMS.registerItem(name, item, properties.stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item) {
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> createWeaponItem(String name, Function<Weapon.Builder, ? extends Weapon> weaponItem, Weapon.Builder builder) {
        return ITEMS.register(name, () -> weaponItem.apply(builder));
    }

    public static DeferredItem<Item> createEgoWeaponItem(String name, Function<Weapon.Builder, ? extends EgoWeapon> weaponItem, Weapon.Builder builder) {
        return ITEMS.register(name, () -> weaponItem.apply(builder));
    }
}
