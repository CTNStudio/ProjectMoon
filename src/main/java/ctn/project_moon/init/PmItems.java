package ctn.project_moon.init;

import ctn.project_moon.common.item.CreativeSpiritToolItem;
import ctn.project_moon.common.item.armor.PmArmor;
import ctn.project_moon.common.item.armor.ego.EgoArmor;
import ctn.project_moon.common.item.weapon.ChaosKnifeItem;
import ctn.project_moon.common.item.weapon.DetonatingBatonItem;
import ctn.project_moon.common.item.weapon.Weapon;
import ctn.project_moon.common.item.weapon.ego.*;
import net.minecraft.world.item.ArmorItem;
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
    // 图标
    public static final DeferredItem<Item> EGO_CURIOS_ICON = registerSimpleIconItem("ego_curios_icon");
    public static final DeferredItem<Item> EGO_SUIT_ICON = registerSimpleIconItem("ego_suit_icon");
    public static final DeferredItem<Item> EGO_WEAPON_ICON = registerSimpleIconItem("ego_weapon_icon");
    public static final DeferredItem<Item> CREATIVE_TOOL_ICON = registerSimpleIconItem("creative_tool_icon");

    // 开发者或测试物品
    public static final DeferredItem<Item> CREATIVE_SPIRIT_TOOL = creativeToolItem("creative_spirit_tool", CreativeSpiritToolItem::new);
    public static final DeferredItem<Item> CHAOS_SWORD = creativeToolItem("chaos_sword", ChaosKnifeItem::new);

    // 武器（不一定是EGO）
    public static final DeferredItem<Item> DETONATING_BATON = createWeaponItem("detonating_baton",
            DetonatingBatonItem::new, new Weapon.Builder(3, 4, -2.4F));
    public static final DeferredItem<Item> WRIST_CUTTER = createEgoWeaponItem("wrist_cutter",
            WristCutterItem::new, new Weapon.Builder(2, 3, 0.2F, -0.1F));
    public static final DeferredItem<Item> BEAR_PAWS = createEgoWeaponItem("bear_paws",
            BearPawsItem::new, new Weapon.Builder(7, 7, -1F, -0.3F));
    // 原称 in the name of love and hate
    public static final DeferredItem<Item> LOVE_HATE = createEgoWeaponItem("love_hate",
            LoveHateItem::new, new Weapon.Builder(3, 5, -2F));
    public static final DeferredItem<Item> PARADISE_LOST = createEgoWeaponItem("paradise_lost",
            ParadiseLostItem::new, new Weapon.Builder(12, 16, -2.3F));

    // 护甲
    public static final DeferredItem<Item> SUIT = createArmorItem("suit",
            PmArmor::new, new PmArmor.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> DRESS_PANTS = createArmorItem("dress_pants",
            PmArmor::new, new PmArmor.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> LOAFERS = createArmorItem("loafers",
            PmArmor::new, new PmArmor.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.BOOTS));

    // 饰品


    private static DeferredItem<Item> registerSimpleItem(String name, Item.Properties props) {
        return ITEMS.registerSimpleItem(name, props);
    }

    private static DeferredItem<BlockItem> registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(name, block);
    }

    private static DeferredItem<Item> registerSimpleIconItem(String name) {
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    private static DeferredItem<Item> creativeToolItem(String name) {
        return ITEMS.registerSimpleItem(name, new Item.Properties().stacksTo(1));
    }

    private static DeferredItem<Item> creativeToolItem(String name, Function<Item.Properties, ? extends Item> item) {
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    private static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item, Item.Properties properties) {
        return ITEMS.registerItem(name, item, properties.stacksTo(1));
    }

    private static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item) {
        return ITEMS.registerItem(name, item, new Item.Properties().stacksTo(1));
    }

    private static DeferredItem<Item> createArmorItem(String name, Function<PmArmor.Builder, ? extends PmArmor> armorItem, PmArmor.Builder builder){
        return ITEMS.register(name, () -> armorItem.apply(builder));
    }
    private static DeferredItem<Item> createEgoArmorItem(String name, Function<PmArmor.Builder, ? extends EgoArmor> armorItem, PmArmor.Builder builder){
        return ITEMS.register(name, () -> armorItem.apply(builder));
    }

    private static DeferredItem<Item> createWeaponItem(String name, Function<Weapon.Builder, ? extends Weapon> weaponItem, Weapon.Builder builder) {
        return ITEMS.register(name, () -> weaponItem.apply(builder));
    }

    private static DeferredItem<Item> createEgoWeaponItem(String name, Function<Weapon.Builder, ? extends EgoWeapon> weaponItem, Weapon.Builder builder) {
        return ITEMS.register(name, () -> weaponItem.apply(builder));
    }
}
