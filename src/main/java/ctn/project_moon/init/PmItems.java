package ctn.project_moon.init;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.client.models.GeoCurioModel;
import ctn.project_moon.client.models.PmGeoArmorModel;
import ctn.project_moon.client.renderer_providers.PmGeoArmourRenderProvider.GeoBuilder;
import ctn.project_moon.common.item.CreativeSpiritToolItem;
import ctn.project_moon.common.item.armor.EgoArmorItem;
import ctn.project_moon.common.item.armor.GeoEgoArmorItem;
import ctn.project_moon.common.item.armor.PmArmorItem;
import ctn.project_moon.common.item.curio.EgoCurioItem;
import ctn.project_moon.common.item.weapon.DetonatingBatonItem;
import ctn.project_moon.common.item.weapon.abstract_ltem.EgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.RemoteEgoWeapon;
import ctn.project_moon.common.item.weapon.abstract_ltem.Weapon;
import ctn.project_moon.common.item.weapon.close.BearPawsItem;
import ctn.project_moon.common.item.weapon.close.ChaosKnifeItem;
import ctn.project_moon.common.item.weapon.close.WristCutterItem;
import ctn.project_moon.common.item.weapon.remote.MagicBulletItem;
import ctn.project_moon.common.item.weapon.special.LoveHateItem;
import ctn.project_moon.common.item.weapon.special.ParadiseLostItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmItems {
	public static final DeferredRegister.Items ITEM_REGISTER      = DeferredRegister.createItems(MOD_ID);
	/// 图标

	public static final DeferredItem<Item>     EGO_CURIOS_ICON    = registerSimpleIconItem("ego_curios_icon");
	public static final DeferredItem<Item>     EGO_SUIT_ICON      = registerSimpleIconItem("ego_suit_icon");
	public static final DeferredItem<Item>     EGO_WEAPON_ICON    = registerSimpleIconItem("ego_weapon_icon");
	public static final DeferredItem<Item>     CREATIVE_TOOL_ICON = registerSimpleIconItem("creative_tool_icon");

	/// 开发者或测试物品

	public static final DeferredItem<Item> CREATIVE_SPIRIT_TOOL = creativeToolItem("creative_spirit_tool", CreativeSpiritToolItem::new);
	public static final DeferredItem<Item> CHAOS_SWORD          = creativeToolItem("chaos_sword", ChaosKnifeItem::new);

	/// 武器（不一定是EGO）

	/// ZAYIN

	public static final DeferredItem<Item> DETONATING_BATON = createWeaponItem(
			"detonating_baton",
			DetonatingBatonItem::new, new Weapon.Builder(3, 4, -2.4F));

	/// TETH

	public static final DeferredItem<Item> WRIST_CUTTER = createEgoWeaponItem(
			"wrist_cutter",
			WristCutterItem::new, new Weapon.Builder(2, 3, 0.2F, -0.1F));

	///  HE

	public static final DeferredItem<Item> BEAR_PAWS = createEgoWeaponItem(
			"bear_paws",
			BearPawsItem::new, new Weapon.Builder(7, 7, -1F, -0.3F)
					.fortitudeRating(FourColorAttribute.Rating.II));

	///  WAW

	// 原称 in the name of love and hate
	public static final DeferredItem<Item> LOVE_HATE = createEgoWeaponItem(
			"love_hate",
			LoveHateItem::new, new Weapon.Builder(3, 5, -2F)
					.fortitudeRating(FourColorAttribute.Rating.III)
					.justiceRating(FourColorAttribute.Rating.III)
					.compositeRating(FourColorAttribute.Rating.IV));

	public static final DeferredItem<Item> MAGIC_BULLET = createRemoteEgoWeaponItem(
			"magic_bullet",
			MagicBulletItem::new, new Weapon.Builder(10, 11, -2.7F)
					.temperanceRating(FourColorAttribute.Rating.III));


	/// ALEPH

	public static final DeferredItem<Item> PARADISE_LOST = createEgoWeaponItem(
			"paradise_lost",
			ParadiseLostItem::new, new Weapon.Builder(12, 16, -2.3F)
					.fortitudeRating(FourColorAttribute.Rating.V)
					.prudenceRating(FourColorAttribute.Rating.V)
					.temperanceRating(FourColorAttribute.Rating.V)
					.justiceRating(FourColorAttribute.Rating.V));


	/// 护甲

	/// ZAYIN

	public static final DeferredItem<Item> SUIT = createArmorItem(
			"suit", PmArmorItem::new,
			new PmArmorItem.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.CHESTPLATE));

	public static final DeferredItem<Item> DRESS_PANTS = createArmorItem(
			"dress_pants", PmArmorItem::new,
			new PmArmorItem.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.LEGGINGS));

	public static final DeferredItem<Item> LOAFERS = createArmorItem(
			"loafers", PmArmorItem::new,
			new PmArmorItem.Builder(PmArmorMaterials.SUIT, ArmorItem.Type.BOOTS));

	/// TETH

	///  HE

	public static final DeferredItem<Item> MAGIC_BULLET_BOOTS = createGeoArmorItem(
			"magic_bullet_boots", GeoEgoArmorItem::new,
			new GeoBuilder<>().roughHandModel(new PmGeoArmorModel<>("magic_bullet_armor")),
			new PmArmorItem.Builder(PmArmorMaterials.HE, ArmorItem.Type.BOOTS)
					.fortitudeRating(FourColorAttribute.Rating.III)
					.justiceRating(FourColorAttribute.Rating.III)
	);

	public static final DeferredItem<Item> MAGIC_BULLET_CHESTPLATE = createGeoArmorItem(
			"magic_bullet_chestplate", GeoEgoArmorItem::new,
			new GeoBuilder<>().roughHandModel(new PmGeoArmorModel<>("magic_bullet_armor")),
			new PmArmorItem.Builder(PmArmorMaterials.HE, ArmorItem.Type.CHESTPLATE)
					.fortitudeRating(FourColorAttribute.Rating.III)
					.justiceRating(FourColorAttribute.Rating.III)
	);

	public static final DeferredItem<Item> MAGIC_BULLET_LEGGINGS = createGeoArmorItem(
			"magic_bullet_leggings", GeoEgoArmorItem::new,
			new GeoBuilder<>().roughHandModel(new PmGeoArmorModel<>("magic_bullet_armor")),
			new PmArmorItem.Builder(PmArmorMaterials.HE, ArmorItem.Type.LEGGINGS)
					.fortitudeRating(FourColorAttribute.Rating.III)
					.justiceRating(FourColorAttribute.Rating.III)
	);

	///  WAW

	/// ALEPH


	/// 饰品

	/// ZAYIN

	/// TETH

	///  HE

	public static final DeferredItem<EgoCurioItem> MAGIC_BULLET_PIPE = createCuriosItem(
			"magic_bullet_pipe",
			EgoCurioItem::new, new EgoCurioItem.Builder(-5, -5, 0, 10, new GeoCurioModel<>("magic_bullet_pipe")));

	///  WAW

	/// ALEPH

	public static final DeferredItem<EgoCurioItem> PARADISE_LOST_WINGS = createCuriosItem(
			"paradise_lost_wings",
			EgoCurioItem::new, new EgoCurioItem.Builder(10, 10, 0, 10, new GeoCurioModel<>("paradise_lost_wings")));

	private static DeferredItem<Item> registerSimpleItem(String name, Item.Properties props) {
		return ITEM_REGISTER.registerSimpleItem(name, props);
	}

	private static DeferredItem<BlockItem> registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
		return ITEM_REGISTER.registerSimpleBlockItem(name, block);
	}

	private static DeferredItem<Item> registerSimpleIconItem(String name) {
		return ITEM_REGISTER.registerSimpleItem(name, new Item.Properties().stacksTo(1));
	}

	private static DeferredItem<Item> creativeToolItem(String name) {
		return ITEM_REGISTER.registerSimpleItem(name, new Item.Properties().stacksTo(1));
	}

	private static DeferredItem<Item> creativeToolItem(String name, Function<Item.Properties, ? extends Item> item) {
		return ITEM_REGISTER.registerItem(name, item, new Item.Properties().stacksTo(1));
	}

	private static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item, Item.Properties properties) {
		return ITEM_REGISTER.registerItem(name, item, properties.stacksTo(1));
	}

	private static DeferredItem<Item> createItem(String name, Function<Item.Properties, ? extends Item> item) {
		return ITEM_REGISTER.registerItem(name, item, new Item.Properties().stacksTo(1));
	}

	private static DeferredItem<Item> createArmorItem(String name, Function<PmArmorItem.Builder, ? extends PmArmorItem> armorItem, PmArmorItem.Builder builder) {
		return ITEM_REGISTER.register(name, () -> armorItem.apply(builder));
	}

	private static <T extends Item & GeoItem> DeferredItem<Item> createGeoArmorItem(String name,
			BiFunction<PmArmorItem.Builder, GeoBuilder, GeoEgoArmorItem> armorItem,
			GeoBuilder geoBuilder, PmArmorItem.Builder builder) {
		return ITEM_REGISTER.register(name, () -> armorItem.apply(builder, geoBuilder));
	}

	private static DeferredItem<Item> createEgoArmorItem(String name, Function<PmArmorItem.Builder, ? extends EgoArmorItem> armorItem, PmArmorItem.Builder builder) {
		return ITEM_REGISTER.register(name, () -> armorItem.apply(builder));
	}

	private static DeferredItem<Item> createWeaponItem(String name, Function<Weapon.Builder, ? extends Weapon> weaponItem, Weapon.Builder builder) {
		return ITEM_REGISTER.register(name, () -> weaponItem.apply(builder));
	}

	private static DeferredItem<Item> createEgoWeaponItem(String name, Function<Weapon.Builder, ? extends EgoWeapon> weaponItem, Weapon.Builder builder) {
		return ITEM_REGISTER.register(name, () -> weaponItem.apply(builder));
	}

	private static DeferredItem<EgoCurioItem> createCuriosItem(String name, Function<EgoCurioItem.Builder, ? extends EgoCurioItem> curiosItem, EgoCurioItem.Builder builder) {
		return ITEM_REGISTER.register(name, () -> curiosItem.apply(builder));
	}

	private static DeferredItem<Item> createRemoteEgoWeaponItem(String name, Function<Weapon.Builder, ? extends RemoteEgoWeapon> curiosItem, Weapon.Builder builder) {
		return ITEM_REGISTER.register(name, () -> curiosItem.apply(builder));
	}
}
