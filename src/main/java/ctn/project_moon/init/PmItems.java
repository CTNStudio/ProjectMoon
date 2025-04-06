package ctn.project_moon.init;

import ctn.project_moon.common.item.EgoWeaponItem;
import ctn.project_moon.common.item.creative_tool.ChaosKnifeItem;
import ctn.project_moon.common.item.creative_tool.CreativeSpiritToolItem;
import ctn.project_moon.common.item.weapon.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmItems{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredItem<Item> EGO_CURIOS_ICON = registerSimpleIconItem("ego_curios_icon");
    public static final DeferredItem<Item> EGO_SUIT_ICON = registerSimpleIconItem("ego_suit_icon");
    public static final DeferredItem<Item> EGO_WEAPON_ICON = registerSimpleIconItem("ego_weapon_icon");
    public static final DeferredItem<Item> CREATIVE_TOOL_ICON = registerSimpleIconItem("creative_tool_icon");

    public static final DeferredItem<Item> CREATIVE_SPIRIT_TOOL = creativeToolItem("creative_spirit_tool", CreativeSpiritToolItem::new);
    public static final DeferredItem<Item> CHAOS_KNIFE = creativeToolItem("chaos_knife", ChaosKnifeItem::new);

    public static final DeferredItem<Item> DETONATING_BATON = createItem("detonating_baton", DetonatingBatonItem::new, EgoWeaponItem.EgoAttribute.builder());
    public static final DeferredItem<Item> WRIST_CUTTER = createItem("wrist_cutter", WristCutterItem::new, EgoWeaponItem.EgoAttribute.builder());
    public static final DeferredItem<Item> BEAR_PAWS = createItem("bear_paws", BearPawsItem::new, EgoWeaponItem.EgoAttribute.builder());
    // 原称 in the name of love and hate
    public static final DeferredItem<Item> LOVE_HATE = createItem("love_hate", LoveHateItem::new);
    public static final DeferredItem<Item> PARADISE_LOST = createItem("paradise_lost", ParadiseLostItem::new, EgoWeaponItem.EgoAttribute.builder());

    public static DeferredItem<Item> registerSimpleItem(String name, Item.Properties props){
        return ITEMS.registerSimpleItem(name, props);
    }

    public static DeferredItem<BlockItem> registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(name, block);
    }

    public static DeferredItem<Item> registerSimpleIconItem(String name){
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> creativeToolItem(String name){
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> creativeToolItem(String name, Function<Item.Properties, ? extends Item> item){
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item, Item.Properties properties){
        return ITEMS.registerItem(name, item, properties.stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item){
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, BiFunction<Item.Properties, EgoWeaponItem.EgoAttribute, ? extends EgoWeaponItem> egoItem, EgoWeaponItem.EgoAttribute egoAttribute, Item.Properties properties){
        return createItem(name, (p) -> egoItem.apply(p, egoAttribute), properties);
    }

    public static DeferredItem<Item> createItem(String name, BiFunction<Item.Properties, EgoWeaponItem.EgoAttribute, ? extends EgoWeaponItem> egoItem, EgoWeaponItem.EgoAttribute egoAttribute){
        return createItem(name, (p) -> egoItem.apply(p, egoAttribute));
    }
}
