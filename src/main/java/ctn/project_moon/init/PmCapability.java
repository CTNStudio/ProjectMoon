package ctn.project_moon.init;

import ctn.project_moon.capability.ILevel;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.block.ILevelBlock;
import ctn.project_moon.capability.entity.IColorDamageTypeEntity;
import ctn.project_moon.capability.entity.IInvincibleTickEntity;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.capability.item.IUsageReqItem;
import ctn.project_moon.mixin.DamageSourceMixin;
import ctn.project_moon.mixin_extend.IModDamageSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ENTITY;
import static ctn.project_moon.init.PmCapability.ColorDamageType.COLOR_DAMAGE_TYPE_ITEM;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ENTITY;
import static ctn.project_moon.init.PmCapability.InvincibleTick.INVINCIBLE_TICK_ITEM;
import static ctn.project_moon.init.PmCapability.Level.LEVEL_BlOCK;
import static ctn.project_moon.init.PmCapability.Level.LEVEL_ENTITY;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PmCapability {

	public static final ItemCapability<IRandomDamage, Void> RANDOM_DAMAGE_ITEM =
			ItemCapability.createVoid(getResourceLocation("random_damage"), IRandomDamage.class);

	public static final ItemCapability<IUsageReqItem, Void> USAGE_REQ_ITEM =
			ItemCapability.createVoid(getResourceLocation("usage_req_item"), IUsageReqItem.class);

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

			if (item instanceof ILevel capability) {
				event.registerItem(Level.LEVEL_ITEM, (stack, ctx) -> capability, item);
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

			event.registerEntity(
					LEVEL_ENTITY, entityType, (entity, ctx) ->
							entity instanceof ILevel capability ? capability : null);
		}
	}

	@SubscribeEvent
	public static void registrarBlockEntity(RegisterCapabilitiesEvent event) {
		for (BlockEntityType<?> blockEntityType : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
			event.registerBlockEntity(
					LEVEL_BlOCK, blockEntityType, (blockEntity, ctx) ->
							blockEntity instanceof ILevelBlock provider ? provider : null);
		}
	}

	@SubscribeEvent
	public static void registrarBlock(RegisterCapabilitiesEvent event) {
		for (Block block : BuiltInRegistries.BLOCK) {
			if (block instanceof ILevelBlock capability){
				event.registerBlock(
						LEVEL_BlOCK, (level, blockPos, blockState, blockEntity, ctx) -> capability, block);
			}
		}
	}

	private static @NotNull ResourceLocation getResourceLocation(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static class Level {
		public static final ItemCapability<ILevel, Void> LEVEL_ITEM =
				ItemCapability.createVoid(getResourceLocation("level_item"), ILevel.class);

		public static final EntityCapability<ILevel, Void> LEVEL_ENTITY =
				EntityCapability.createVoid(getResourceLocation("level_entity"), ILevel.class);

		public static final BlockCapability<ILevelBlock, Void> LEVEL_BlOCK =
				BlockCapability.createVoid(getResourceLocation("level_block"), ILevelBlock.class);

	}

	/// 也可以通过mixin类 {@link DamageSourceMixin} 的 {@link IModDamageSource}接口方法代替或覆盖这些
	public static class InvincibleTick {
		public static final ItemCapability<IInvincibleTickItem, Void> INVINCIBLE_TICK_ITEM =
				ItemCapability.createVoid(getResourceLocation("invincible_tick_item"), IInvincibleTickItem.class);

		/// 注意：此能力是不被推荐的，因为实体的伤害方法是多种多样的应该使用<p>
		/// mixin类 {@link DamageSourceMixin} 的 {@link IModDamageSource}接口方法代替，<p>
		/// 或者你想给关于这个实体的所有都是一个值，那可以使用此能力
		/// <p>
		/// 应用场景：让玩家造成的伤害都没有无敌帧，弹幕
		/// <p>
		/// 注：基于原版的伤害系统
		public static final EntityCapability<IInvincibleTickEntity, Void> INVINCIBLE_TICK_ENTITY =
				EntityCapability.createVoid(getResourceLocation("invincible_tick_entity"), IInvincibleTickEntity.class);
	}

	public static class ColorDamageType{
		public static final ItemCapability<IColorDamageTypeItem, Void> COLOR_DAMAGE_TYPE_ITEM =
				ItemCapability.createVoid(getResourceLocation("color_damage_type_item"), IColorDamageTypeItem.class);

		public static final EntityCapability<IColorDamageTypeEntity, Void> COLOR_DAMAGE_TYPE_ENTITY =
				EntityCapability.createVoid(getResourceLocation("color_damage_type_entity"), IColorDamageTypeEntity.class);

	}
}