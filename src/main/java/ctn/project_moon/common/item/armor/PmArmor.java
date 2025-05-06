package ctn.project_moon.common.item.armor;

import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import static ctn.project_moon.PmMain.MOD_ID;

public class PmArmor extends ArmorItem implements Equipable {

    public PmArmor(Builder builder) {
        this(builder.build(), builder);
    }

    public PmArmor(Properties properties, Builder builder) {
        super(builder.material, builder.type, properties.stacksTo(1));
    }

    public static class Builder{
        private double physicsResistance = 0;
        private double spiritResistance = 0;
        private double erosionResistance = 0;
        private double theSoulResistance =0;
        private int durability;
        private Item.Properties properties = new Item.Properties();
        private final Holder<ArmorMaterial> material;
        private final ArmorItem.Type type;

        public Builder(Holder<ArmorMaterial> material, ArmorItem.Type type) {
            this.material= material;
            this.type = type;
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
            return properties;
        }

        public Builder set(double physics, double spirit, double erosion, double theSoul, int durability) {
            this.physicsResistance = -physics;
            this.spiritResistance = -spirit;
            this.erosionResistance = -erosion;
            this.theSoulResistance = -theSoul;
            this.durability = durability;
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
