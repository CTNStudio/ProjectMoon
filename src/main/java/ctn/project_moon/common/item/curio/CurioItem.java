package ctn.project_moon.common.item.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.common.models.PmGeoCurioModel;
import ctn.project_moon.init.PmEntityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * ego饰品
 * @author Dusttt
 */
public class CurioItem extends Item implements ICurioItem, GeoItem {
    private final float addFortitude;
    private final float addPrudence;
    private final float addTemperance;
    private final float addJustice;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final PmGeoCurioModel<CurioItem> model;

    public CurioItem(float addFortitude, float addPrudence, float addTemperance, float addJustice, Builder builder) {
        super(builder.properties.stacksTo(1).durability(0));
        this.model = builder.model;
        this.addFortitude = addFortitude;
        this.addPrudence = addPrudence;
        this.addTemperance = addTemperance;
        this.addJustice = addJustice;
    }

    public CurioItem(Builder builder){
        this(builder.addFortitude, builder.addPrudence, builder.addTemperance, builder.addJustice, builder);
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

    private void addAttribute(float addFortitude, Multimap<Holder<Attribute>, AttributeModifier> modifier, Holder<Attribute> fortitudeAdditional, ResourceLocation id) {
        if (addFortitude != 0)
            modifier.put(fortitudeAdditional,
                    new AttributeModifier(id, addFortitude, AttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public PmGeoCurioModel<CurioItem> getModel() {
        return model;
    }

    public static class Builder {
        private float addFortitude;
        private float addPrudence;
        private float addTemperance;
        private float addJustice;
        private Item.Properties properties = new Item.Properties();
        private PmGeoCurioModel<CurioItem> model;

        public Builder(PmGeoCurioModel<CurioItem> model) {
            this.model = model;
        }

        public Builder(float addFortitude, float addPrudence, float addTemperance, float addJustice, PmGeoCurioModel<CurioItem> model) {
            this(model);
            this.setAddFortitude(addFortitude);
            this.setAddPrudence(addPrudence);
            this.setAddTemperance(addTemperance);
            this.setAddJustice(addJustice);
        }

        public Builder properties(Item.Properties properties) {
            this.setProperties(properties);
            return this;
        }

        public void setModel(PmGeoCurioModel<CurioItem> model) {
            this.model = model;
        }

        public void setAddFortitude(float addFortitude) {
            this.addFortitude = addFortitude;
        }

        public void setAddPrudence(float addPrudence) {
            this.addPrudence = addPrudence;
        }

        public void setAddTemperance(float addTemperance) {
            this.addTemperance = addTemperance;
        }

        public void setAddJustice(float addJustice) {
            this.addJustice = addJustice;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }
}
