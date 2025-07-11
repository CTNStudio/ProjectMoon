package ctn.project_moon.common.item.weapon.abstract_item;

import ctn.project_moon.api.attr.FourColorAttribute;
import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.capability.IRandomDamage;
import ctn.project_moon.capability.item.IColorDamageTypeItem;
import ctn.project_moon.capability.item.IInvincibleTickItem;
import ctn.project_moon.capability.item.IUsageReqItem;
import ctn.project_moon.client.models.GuiItemModel;
import ctn.project_moon.client.models.PmGeoItemModel;
import ctn.project_moon.client.renderer_providers.PmGeoItemRenderProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
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
import static ctn.project_moon.common.item.components.ItemColorUsageReq.notToExceed;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;

/**
 * 武器基类
 */
public abstract class Weapon extends Item implements GeoItem, IRandomDamage, IColorDamageTypeItem, IInvincibleTickItem, IUsageReqItem {
	public static final ResourceLocation        ENTITY_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "entity_range");
	public static final ResourceLocation        BLOCK_RANGE  = ResourceLocation.fromNamespaceAndPath(MOD_ID, "block_range");
	// 注：这个只对近战攻击有效果
	protected final     PmDamageTool.ColorType  initialColorDamageType;
	private final       AnimatableInstanceCache cache        = GeckoLibUtil.createInstanceCache(this);
	private final       int                     minDamage;
	private final       int                     maxDamage;
	protected           GeoModel<Weapon>        defaultModel;
	protected           GeoModel<Weapon>        guiModel;
	
	public Weapon(Builder builder) {
		this(builder.build(), builder);
	}
	
	public Weapon(Builder builder, PmDamageTool.ColorType initialColorDamageType) {
		this(builder.build(), builder.initialColorDamageType(initialColorDamageType));
	}
	
	public Weapon(Item.Properties properties, Builder builder) {
		this(properties, builder, builder.initialColorDamageType);
	}
	
	public Weapon(Item.Properties properties, Builder builder, PmDamageTool.ColorType initialColorDamageType) {
		super(properties
				      .attributes(builder.getItemAttributeModifiers())
				      .stacksTo(1));
		this.maxDamage              = builder.maxDamage;
		this.minDamage              = builder.minDamage;
		this.initialColorDamageType = initialColorDamageType;
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
	
	public void setItemModel(GeoModel<Weapon> defaultModel, GeoModel<Weapon> guiModel) {
		setDefaultModel(defaultModel);
		setGuiModel(guiModel);
	}
	
	public void setItemModel(String modelName) {
		setDefaultModel(new PmGeoItemModel<>(modelName));
		setGuiModel(new GuiItemModel<>(modelName));
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
	
	/// 获取物品当前的伤害颜色<p>注：这个只对近战攻击有效果
	@Override
	@CheckForNull
	public PmDamageTool.ColorType getColorDamageType(ItemStack stack) {
		return initialColorDamageType;
	}
	
	/// 获取伤害类型描述
	@Override
	public Component getColorDamageTypeToTooltip() {
		return null;
	}
	
	
	/**
	 * 使用物品时触发
	 */
	@Override
	public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	
	}
	
	/**
	 * 攻击时触发
	 */
	@Override
	public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	
	}
	
	/**
	 * 在手上时触发
	 */
	@Override
	public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	
	}
	
	/**
	 * 物品在背包时里触发
	 */
	@Override
	public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	
	}
	
	/**
	 * 在装备里时触发，如盔甲，饰品
	 */
	@Override
	public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {
	
	}
	
	/// 武器属性构造器
	public static class Builder {
		// EGO伤害
		private int minDamage, maxDamage;
		// 适用与近战EGO，和部分远程EGO
		private float                     attackSpeed;
		// 近战攻击距离 & 可以摸到方块的距离
		private float                     attackDistance;
		// 预留的耐久
		private int                       durability;
		private Item.Properties           properties             = new Item.Properties();
		// 注：这个只对近战攻击有效果
		private PmDamageTool.ColorType    initialColorDamageType = PmDamageTool.ColorType.PHYSICS;
		private FourColorAttribute.Rating fortitudeRating;
		private FourColorAttribute.Rating prudenceRating;
		private FourColorAttribute.Rating temperanceRating;
		private FourColorAttribute.Rating justiceRating;
		private FourColorAttribute.Rating compositeRating;
		
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
		
		/// 注：这个只对近战攻击有效果
		public Builder initialColorDamageType(PmDamageTool.ColorType initialColorDamageType) {
			this.initialColorDamageType = initialColorDamageType;
			return this;
		}
		
		public Item.Properties build() {
			properties.attributes(getItemAttributeModifiers());
			if (durability > 0) {
				properties.durability(durability);
			}
			if (fortitudeRating != null || prudenceRating != null || temperanceRating != null || justiceRating != null || compositeRating != null) {
				properties.component(ITEM_COLOR_USAGE_REQ, notToExceed(fortitudeRating, prudenceRating, temperanceRating, justiceRating, compositeRating));
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
		
		/// 勇气
		public Builder fortitudeRating(FourColorAttribute.Rating fortitudeRating) {
			this.fortitudeRating = fortitudeRating;
			return this;
		}
		
		/// 理智
		public Builder prudenceRating(FourColorAttribute.Rating prudenceRating) {
			this.prudenceRating = prudenceRating;
			return this;
		}
		
		/// 自律
		public Builder temperanceRating(FourColorAttribute.Rating temperanceRating) {
			this.temperanceRating = temperanceRating;
			return this;
		}
		
		/// 正义
		public Builder justiceRating(FourColorAttribute.Rating justiceRating) {
			this.justiceRating = justiceRating;
			return this;
		}
		
		/// 综合
		public Builder compositeRating(FourColorAttribute.Rating compositeRating) {
			this.compositeRating = compositeRating;
			return this;
		}
	}
}
