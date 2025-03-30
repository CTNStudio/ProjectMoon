package ctn.project_moon.create;

import ctn.project_moon.common.items.ego_weapon.mace.DetonatingBatonItem;
import ctn.project_moon.common.items.ego_weapon.mace.MaceItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmItems{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredItem<Item> EGO_CURIOS_ICON = registerSimpleIconItem("ego_curios_icon");
    public static final DeferredItem<Item> EGO_SUIT_ICON = registerSimpleIconItem("ego_suit_icon");
    public static final DeferredItem<Item> EGO_WEAPON_ICON = registerSimpleIconItem("ego_weapon_icon");

    public static final DeferredItem<Item> DETONATING_BATON = createBatonItem("detonating_baton", DetonatingBatonItem::new,
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> WRIST_CUTTER = createBatonItem("wrist_cutter", DetonatingBatonItem::new,
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> BEAR_PAWS = createBatonItem("bear_paws", DetonatingBatonItem::new,
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IN_THE_NAME_OF_LOVE_AND_HATE = createBatonItem("in_the_name_of_love_and_hate", DetonatingBatonItem::new,
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> PARADISE_LOST = createBatonItem("paradise_lost", DetonatingBatonItem::new,
            new Item.Properties().stacksTo(1));

    public static DeferredItem<Item> registerSimpleItem(String name, Item.Properties props){
        return ITEMS.registerSimpleItem(name, props);
    }

    public static DeferredItem<BlockItem> registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(name, block);
    }

    public static DeferredItem<Item> registerSimpleIconItem(String name){
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    public static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item, Item.Properties properties){
        return ITEMS.registerItem(name, item, properties);
    }

    public static DeferredItem<Item> createBatonItem(String name, Function<Item.Properties, ? extends MaceItem> item, Item.Properties properties){
        return createItem(name, item, properties);
    }
}
