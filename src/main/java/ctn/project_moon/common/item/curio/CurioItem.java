package ctn.project_moon.common.item.curio;


import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * ego饰品
 *
 * @author Dusttt
 */
public class CurioItem extends Item implements ICurioItem {

    private final float addFortitude;
    private final float addPrudence;
    private final float addTemperance;
    private final float addJustice;

    private String modelId;
//    private final   AnimatableInstanceCache     cache = GeckoLibUtil.createInstanceCache(this);
//    protected final PmGeoCurioModel<CuriosItem> model;
//
//    @Override
//    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
//        consumer.accept(new GeoRenderProvider() {
//            private GeoArmorRenderer<?> renderer;
//
//            @Override
//            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(
//                    @Nullable T livingEntity,
//                    ItemStack itemStack,
//                    @Nullable EquipmentSlot equipmentSlot,
//                    @Nullable HumanoidModel<T> original) {
//                if (this.renderer == null)
//                    this.renderer = new PmCuriosRenderer<>(model);
//                return this.renderer;
//            }
//        });
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return this.cache;
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//    }

    public CurioItem(float addFortitude, float addPrudence, float addTemperance, float addJustice, Properties properties, Builder builder) {
        super(properties.stacksTo(1).durability(0));
        this.addFortitude  = addFortitude;
        this.addPrudence   = addPrudence;
        this.addTemperance = addTemperance;
        this.addJustice = addJustice;
        this.modelId    = builder.modelId;
    }
    public CurioItem(Builder builder) {
        this(builder.addFortitude, builder.addPrudence, builder.addTemperance, builder.addJustice, builder.properties, builder);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer player)
            FourColorAttribute.renewFourColorAttribute(player);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer player)
            FourColorAttribute.renewFourColorAttribute(player);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ICurioItem.super.canEquip(slotContext, stack);
    }

    /**
     * 属性加成
     */
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(addFortitude, modifier, PmEntityAttributes.FORTITUDE_ADDITIONAL, id);
        addAttribute(addPrudence, modifier, PmEntityAttributes.PRUDENCE_ADDITIONAL, id);
        addAttribute(addTemperance, modifier, PmEntityAttributes.TEMPERANCE_ADDITIONAL, id);
        addAttribute(addJustice, modifier, PmEntityAttributes.JUSTICE_ADDITIONAL, id);
        return modifier;
    }

    private void addAttribute(float addJustice, Multimap<Holder<Attribute>, AttributeModifier> modifier, Holder<Attribute> justiceAdditional, ResourceLocation id) {
        if (addJustice != 0) {
            modifier.put(justiceAdditional, new AttributeModifier(id, addJustice, AttributeModifier.Operation.ADD_VALUE));
        }
    }

	public String getModelId() {
		return modelId;
	}

	public static class Builder {
        private final String modelId;

        private float addFortitude;
        private float addPrudence;
        private float addTemperance;
        private float addJustice;

        private Item.Properties properties = new Item.Properties();

        public Builder() {
            this.modelId = "curio";
        }

        public Builder(float addFortitude, float addPrudence, float addTemperance, float addJustice, String modelId) {
            this.setAddFortitude(addFortitude);
            this.setAddPrudence(addPrudence);
            this.setAddTemperance(addTemperance);
            this.setAddJustice(addJustice);
            this.modelId = modelId;
        }

        public Builder(float addFortitude, float addPrudence, float addTemperance, float addJustice) {
            this(addFortitude, addPrudence, addTemperance, addJustice, "curio");
        }

        private Builder properties(Item.Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder setAddFortitude(float addFortitude) {
            this.addFortitude = addFortitude;
            return this;
        }

        public Builder setAddPrudence(float addPrudence) {
            this.addPrudence = addPrudence;
            return this;
        }

        public Builder setAddTemperance(float addTemperance) {
            this.addTemperance = addTemperance;
            return this;
        }

        public Builder setAddJustice(float addJustice) {
            this.addJustice = addJustice;
            return this;
        }
    }
}
