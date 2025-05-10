package ctn.project_moon.common.item.weapon;

import ctn.project_moon.client.geo.GeoItemRenderProvider;
import ctn.project_moon.common.RandomDamageProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmItemDataComponents.MODE_BOOLEAN;

public abstract class Weapon extends Item implements GeoItem, RandomDamageProcessor {
	public static final ResourceLocation ENTITY_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "entity_range");
	public static final ResourceLocation BLOCK_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "block_range");
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final int maxDamage;
	private final int minDamage;
	/**
	 * 是否是特殊物品
	 */
	private final boolean isSpecialTemplate;
	protected GeoModel<Weapon> defaultModel;
	protected GeoModel<Weapon> guiModel;

	public Weapon(Builder builder) {
		this(builder.build(), false, builder.maxDamage, builder.minDamage);
	}

	public Weapon(Builder builder, boolean isSpecialTemplate) {
		this(builder.build(), isSpecialTemplate, builder.maxDamage, builder.minDamage);
	}

	public Weapon(Item.Properties properties, Builder builder) {
		this(properties.attributes(builder.getItemAttributeModifiers()), false, builder.maxDamage, builder.minDamage);
	}

	public Weapon(Item.Properties properties, boolean isSpecialTemplate, Builder builder) {
		this(properties.attributes(builder.getItemAttributeModifiers()), isSpecialTemplate, builder.maxDamage, builder.minDamage);
	}

	private Weapon(Item.Properties properties, boolean isSpecialTemplate, int maxDamage, int minDamage) {
		super(properties.component(MODE_BOOLEAN, false).stacksTo(1));
		this.isSpecialTemplate = isSpecialTemplate;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
	}

	public void setDefaultModel(GeoModel<Weapon> defaultModel) {
		this.defaultModel = defaultModel;
	}

	public void setGuiModel(GeoModel<Weapon> guiModel) {
		this.guiModel = guiModel;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoItemRenderProvider(defaultModel, guiModel));
	}

	@Override
	public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
		return !player.isCreative();
	}

	public boolean isSpecialTemplate() {
		return isSpecialTemplate;
	}

	@Override
	public int getMinDamage() {
		return minDamage;
	}

	@Override
	public int getMaxDamage() {
		return maxDamage;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static class Builder {
		private int minDamage, maxDamage;
		private float attackSpeed, attackDistance;
		private int durability;
		private Item.Properties properties = new Item.Properties();

		public Builder() {
		}

		public Builder(int minDamage, int maxDamage, float attackSpeed) {
			this.minDamage = minDamage;
			this.maxDamage = maxDamage;
			this.attackSpeed = attackSpeed;
		}

		public Builder(int minDamage, int maxDamage, float attackSpeed, int durability) {
			this(minDamage, maxDamage, attackSpeed);
			this.durability = durability;
		}

		public Builder(int minDamage, int maxDamage, float attackSpeed, float attackDistance) {
			this(minDamage, maxDamage, attackSpeed);
			this.attackDistance = attackDistance;
		}

		public Builder(int minDamage, int maxDamage, float attackSpeed, float attackDistance, int durability) {
			this(minDamage, maxDamage, attackSpeed, attackDistance);
			this.durability = durability;
		}

		public Item.Properties build() {
			properties.attributes(getItemAttributeModifiers());
			if (durability > 0) {
				properties.durability(durability);
			}
			return properties;
		}

		public ItemAttributeModifiers getItemAttributeModifiers() {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, maxDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
			builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
			builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_RANGE, attackDistance, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
			builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_RANGE, attackDistance, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
			return builder.build();
		}

		public Builder durability(int durability) {
			this.durability = durability;
			return this;
		}

		public Builder minDamage(int minDamage) {
			this.minDamage = minDamage;
			return this;
		}

		public Builder maxDamage(int maxDamage) {
			this.maxDamage = maxDamage;
			return this;
		}

		public Builder damage(int damage) {
			this.maxDamage = damage;
			this.minDamage = damage;
			return this;
		}

		public Builder attackSpeed(float attackSpeed) {
			this.attackSpeed = attackSpeed;
			return this;
		}

		public Builder attackDistance(float attackDistance) {
			this.attackDistance = attackDistance;
			return this;
		}

		public Builder properties(Properties properties) {
			this.properties = properties;
			return this;
		}
	}
}
