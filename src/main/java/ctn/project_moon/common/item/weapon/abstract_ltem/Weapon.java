package ctn.project_moon.common.item.weapon.abstract_ltem;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.client.renderer_providers.PmGeoItemRenderProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

import javax.annotation.CheckForNull;
import java.util.function.Consumer;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmItemDataComponents.COLOR_DAMAGE_TYPE;

/**
 * 武器基类
 */
public abstract class Weapon extends Item implements
		GeoItem, IRandomDamage, IColorDamageTypeItem, IInvincibleTickItem {
	public static final ResourceLocation        ENTITY_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "entity_range");
	public static final ResourceLocation        BLOCK_RANGE  = ResourceLocation.fromNamespaceAndPath(MOD_ID, "block_range");
	private final       AnimatableInstanceCache cache        = GeckoLibUtil.createInstanceCache(this);
	private final       int                     maxDamage;
	private final       int                     minDamage;
	protected           GeoModel<Weapon>        defaultModel;
	protected           GeoModel<Weapon>        guiModel;

	public Weapon(Builder builder) {
		this(builder.build(), builder);
	}

	public Weapon(Item.Properties properties, Builder builder) {
		super(properties.attributes(builder.getItemAttributeModifiers())
				.component(COLOR_DAMAGE_TYPE.get(), PmDamageTool.ColorType.PHYSICS.getName())
				.stacksTo(1));
		this.maxDamage = builder.maxDamage;
		this.minDamage = builder.minDamage;
	}

	/// 获取武器攻击时造成的无敌帧
	@Override
	public int getInvincibleTick(ItemStack stack) {
		return 20;
	}

	/// 设置模型
	public void setDefaultModel(GeoModel<Weapon> defaultModel) {
		this.defaultModel = defaultModel;
	}

	/// 设置在GUI中的模型
	public void setGuiModel(GeoModel<Weapon> guiModel) {
		this.guiModel = guiModel;
	}

	/// 创建GEO模型渲染
	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new PmGeoItemRenderProvider<>(defaultModel, guiModel));
	}

	/// 是否可以挖掘方块
	@Override
	public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
		return !player.isCreative();
	}

	/// 获取最小伤害
	@Override
	public int getMinDamage() {
		return minDamage;
	}

	/// 获取最大伤害
	@Override
	public int getMaxDamage() {
		return maxDamage;
	}

	/// 创建动画控制器
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
	}

	/// 获取动画实例缓存
	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	/// 获取物品伤害颜色
	@Override
	@CheckForNull
	public PmDamageTool.ColorType getDamageType(ItemStack stack) {
		return PmDamageTool.ColorType.PHYSICS;
	}

	/// 获取伤害类型描述
	@Override
	public Component getFourColorDamageTypeToTooltip() {
		return null;
	}

	/// 武器属性构造器
	public static class Builder {
		// EGO伤害
		private int minDamage, maxDamage;
		// 适用与近战EGO，和部分远程EGO
		private float           attackSpeed;
		// 近战攻击距离 & 可以摸到方块的距离
		private float           attackDistance;
		// 预留的耐久
		private int             durability;
		private Item.Properties properties = new Item.Properties();

		public Builder() {
		}

		public Builder(int minDamage, int maxDamage, float attackSpeed) {
			this.minDamage   = minDamage;
			this.maxDamage   = maxDamage;
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
