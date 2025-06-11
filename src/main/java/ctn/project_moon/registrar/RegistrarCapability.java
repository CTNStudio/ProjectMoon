package ctn.project_moon.registrar;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.ILevel;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.entity.IColorDamageTypeEntity;
import ctn.project_moon.capability.entity.IInvincibleTickEntity;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.capability.item.IUsageReqItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ENTITY;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ITEM;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ENTITY;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ITEM;
import static ctn.project_moon.init.PmCapability.*;
import static ctn.project_moon.init.PmCapability.Level.LEVEL_ENTITY;
import static ctn.project_moon.init.PmItems.*;
import static net.minecraft.world.entity.EntityType.*;


/// 注册能力
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegistrarCapability {
	public static final List<ItemLike> ITEM_ZAYIN = List.of(
			DETONATING_BATON,
			SUIT,
			DRESS_PANTS,
			LOAFERS
	);

	public static final List<ItemLike> ITEM_TETH = List.of(
			WRIST_CUTTER
	);

	public static final List<ItemLike> ITEM_HE = List.of(
			BEAR_PAWS,
			MAGIC_BULLET_BOOTS,
			MAGIC_BULLET_CHESTPLATE,
			MAGIC_BULLET_LEGGINGS,
			MAGIC_BULLET_PIPE
	);

	public static final List<ItemLike> ITEM_WAW = List.of(
			LOVE_HATE,
			MAGIC_BULLET
	);

	public static final List<ItemLike> ITEM_ALEPH = List.of(
			PARADISE_LOST,
			PARADISE_LOST_WINGS,
			CREATIVE_SPIRIT_TOOL,
			CHAOS_SWORD
	);

	public static final List<EntityType<?>> ENTITY_ZAYIN = List.of(

	);

	public static final List<EntityType<?>> ENTITY_TETH = List.of(
			CAVE_SPIDER,
			SPIDER,
			PIGLIN,
			PILLAGER,
			VEX,
			SILVERFISH,
			ENDERMITE,
			PHANTOM,
			MAGMA_CUBE,
			HUSK,
			CREEPER,
			BREEZE,
			DROWNED,
			ZOMBIFIED_PIGLIN,
			ZOMBIE,
			STRAY,
			SKELETON,
			BOGGED,
			BLAZE,
			SLIME
	);

	public static final List<EntityType<?>> ENTITY_HE = List.of(
			WITHER_SKELETON,
			WITCH,
			VINDICATOR,
			EVOKER,
			ZOGLIN,
			SHULKER,
			PIGLIN_BRUTE,
			HOGLIN,
			GHAST,
			ENDERMAN,
			GUARDIAN
	);

	public static final List<EntityType<?>> ENTITY_WAW = List.of(
			RAVAGER,
			ELDER_GUARDIAN,
			IRON_GOLEM
	);

	public static final List<EntityType<?>> ENTITY_ALEPH = List.of(
			WITHER,
			ENDER_DRAGON,
			WARDEN
	);

	/// 请在这里注册等级
	@SubscribeEvent
	public static void registrarItemLevel(RegisterCapabilitiesEvent event) {
		registrarItemLevel(event, PmDamageTool.Level.ZAYIN, ITEM_ZAYIN);
		registrarItemLevel(event, PmDamageTool.Level.TETH, ITEM_TETH);
		registrarItemLevel(event, PmDamageTool.Level.HE, ITEM_HE);
		registrarItemLevel(event, PmDamageTool.Level.WAW, ITEM_WAW);
		registrarItemLevel(event, PmDamageTool.Level.ALEPH, ITEM_ALEPH);

		registrarEntityLevel(event, PmDamageTool.Level.ZAYIN, ENTITY_ZAYIN);
		registrarEntityLevel(event, PmDamageTool.Level.TETH, ENTITY_TETH);
		registrarEntityLevel(event, PmDamageTool.Level.HE, ENTITY_HE);
		registrarEntityLevel(event, PmDamageTool.Level.WAW, ENTITY_WAW);
		registrarEntityLevel(event, PmDamageTool.Level.ALEPH, ENTITY_ALEPH);
//		event.registerBlock(LEVEL_BlOCK, (level, blockPos, blockState, blockEntity, ctx) -> capability);, ***);

//		event.registerBlockEntity(LEVEL_BlOCK, ***, (blockEntity, ctx) -> ***);
	}

	@SubscribeEvent
	public static void registrarItem(RegisterCapabilitiesEvent event) {
		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof IRandomDamage capability) {
				event.registerItem(RANDOM_DAMAGE_ITEM, (stack, ctx) -> capability, item);
			}

			if (item instanceof IInvincibleTickItem capability) {
				event.registerItem(INVINCIBLE_TICK_ITEM, (stack, ctx) -> capability, item);
			}

			if (item instanceof IColorDamageTypeItem capability) {
				event.registerItem(COLOR_DAMAGE_TYPE_ITEM, (stack, ctx) -> capability, item);
			}

			if (item instanceof IUsageReqItem capability) {
				event.registerItem(USAGE_REQ_ITEM, (stack, ctx) -> capability, item);
			}
		}
	}

	@SubscribeEvent
	public static void registrarEntity(RegisterCapabilitiesEvent event) {
		for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
			event.registerEntity(
					INVINCIBLE_TICK_ENTITY, entityType, (entity, ctx) ->
							entity instanceof IInvincibleTickEntity capability ? capability : null);

			event.registerEntity(
					COLOR_DAMAGE_TYPE_ENTITY, entityType, (entity, ctx) ->
							entity instanceof IColorDamageTypeEntity capability ? capability : null);
		}
	}

	@SubscribeEvent
	public static void registrarBlockEntity(RegisterCapabilitiesEvent event) {
//		for (BlockEntityType<?> blockEntityType : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
//
//		}
	}

	@SubscribeEvent
	public static void registrarBlock(RegisterCapabilitiesEvent event) {
//		for (Block block : BuiltInRegistries.BLOCK) {
//
//		}
	}

	public static void registrarItemLevel(RegisterCapabilitiesEvent event, PmDamageTool.Level level, List<ItemLike> list) {
		for (ItemLike itemLike : list) {
			event.registerItem(Level.LEVEL_ITEM, (stack, ctx) -> (itemLike instanceof ILevel ilevel) ? ilevel : () -> level, itemLike);
		}
	}

	public static <E extends Entity> void registrarEntityLevel(RegisterCapabilitiesEvent event, PmDamageTool.Level level, EntityType<E> entityType) {
		event.registerEntity(LEVEL_ENTITY, entityType, (stack, ctx) -> (stack instanceof ILevel ilevel) ? ilevel : () -> level);
	}

	public static void registrarEntityLevel(RegisterCapabilitiesEvent event,PmDamageTool.Level level, List<EntityType<?>> entityTypeList) {
		for (EntityType<?> entityType : entityTypeList) {
			registrarEntityLevel(event, level, entityType);
		}
	}
}
