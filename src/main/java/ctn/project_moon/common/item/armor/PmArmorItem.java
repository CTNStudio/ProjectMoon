package ctn.project_moon.common.item.armor;

import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.capability.item.IUsageReqItem;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.common.item.components.ItemColorUsageReq.notToExceed;
import static ctn.project_moon.init.PmItemDataComponents.ITEM_COLOR_USAGE_REQ;

public class PmArmorItem extends ArmorItem implements IUsageReqItem {

	public PmArmorItem(Builder builder) {
		this(builder.build(), builder);
	}

	public PmArmorItem(Properties properties, Builder builder) {
		super(builder.material, builder.type, properties.stacksTo(1));
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

	public static class Builder {
		private final Holder<ArmorMaterial> material;
		private final ArmorItem.Type        type;
		private       double                physicsResistance = 0;
		private       double                spiritResistance  = 0;
		private       double                erosionResistance = 0;
		private       double                theSoulResistance = 0;
		// 预留的耐久
		private       int                   durability;
		private       Item.Properties       properties        = new Item.Properties();
		private FourColorAttribute.Rating fortitudeRating;
		private FourColorAttribute.Rating prudenceRating;
		private FourColorAttribute.Rating temperanceRating;
		private FourColorAttribute.Rating justiceRating;
		private FourColorAttribute.Rating compositeRating;

		public Builder(Holder<ArmorMaterial> material, ArmorItem.Type type) {
			this.material = material;
			this.type     = type;
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

		private ItemAttributeModifiers getItemAttributeModifiers(ArmorItem.Type type) {
			EquipmentSlotGroup dropLocation = EquipmentSlotGroup.bySlot(type.getSlot());
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			builder.add(PmEntityAttributes.PHYSICS_RESISTANCE, new AttributeModifier(getLocation("physics_resistance.", type), physicsResistance, AttributeModifier.Operation.ADD_VALUE), dropLocation);
			builder.add(PmEntityAttributes.SPIRIT_RESISTANCE, new AttributeModifier(getLocation("spirit_resistance.", type), spiritResistance, AttributeModifier.Operation.ADD_VALUE), dropLocation);
			builder.add(PmEntityAttributes.EROSION_RESISTANCE, new AttributeModifier(getLocation("erosion_resistance.", type), erosionResistance, AttributeModifier.Operation.ADD_VALUE), dropLocation);
			builder.add(PmEntityAttributes.THE_SOUL_RESISTANCE, new AttributeModifier(getLocation("the_soul_resistance.", type), theSoulResistance, AttributeModifier.Operation.ADD_VALUE), dropLocation);
			vanillaArmorAttributes(type, builder, dropLocation);
			return builder.build();
		}

		private void vanillaArmorAttributes(Type type, ItemAttributeModifiers.Builder builder, EquipmentSlotGroup equipmentslotgroup) {
			int i = material.value().getDefense(type);
			float f = material.value().toughness();
			ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
			builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
			builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, f, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
			float f1 = material.value().knockbackResistance();
			if (f1 > 0.0F) {
				builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, f1, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
			}
		}

		public Item.Properties build() {
			properties.attributes(getItemAttributeModifiers(type));
			if (durability > 0) {
				properties.durability(durability);
			}
			if (fortitudeRating != null || prudenceRating != null || temperanceRating != null || justiceRating != null || compositeRating != null) {
				properties.component(ITEM_COLOR_USAGE_REQ, notToExceed(fortitudeRating, prudenceRating, temperanceRating, justiceRating, compositeRating));
			}
			return properties;
		}

		public Builder set(double physics, double spirit, double erosion, double theSoul, int durability) {
			this.physicsResistance = -physics;
			this.spiritResistance  = -spirit;
			this.erosionResistance = -erosion;
			this.theSoulResistance = -theSoul;
			this.durability        = durability;
			return this;
		}

		public Builder properties(Properties properties) {
			this.properties = properties;
			return this;
		}

		public Builder durability(int durability) {
			this.durability = durability;
			return this;
		}

		public Builder the_soul(double value) {
			this.theSoulResistance = value;
			return this;
		}

		public Builder erosion(double value) {
			this.erosionResistance = value;
			return this;
		}

		public Builder spirit(double value) {
			this.spiritResistance = value;
			return this;
		}

		public Builder physics(double value) {
			this.physicsResistance = value;
			return this;
		}

		private @NotNull ResourceLocation getLocation(String name, Type type) {
			return ResourceLocation.fromNamespaceAndPath(MOD_ID, name + type.getName());
		}
	}
}
