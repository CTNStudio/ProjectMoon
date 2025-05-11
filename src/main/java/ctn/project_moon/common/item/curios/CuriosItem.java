package ctn.project_moon.common.item.curios;


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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * ego饰品
 * @author Dusttt
 */
public class CuriosItem extends Item implements ICurioItem, GeoItem {
    private final float addFortitude;
    private final float addPrudence;
    private final float addTemperance;
    private final float addJustice;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public CuriosItem(float addFortitude, float addPrudence, float addTemperance, float addJustice, Properties properties) {
        super(properties);
        this.addFortitude = addFortitude;
        this.addPrudence = addPrudence;
        this.addTemperance = addTemperance;
        this.addJustice = addJustice;
    }
    public CuriosItem(float addFortitude, float addPrudence, float addTemperance, float addJustice) {
        super(new Item.Properties().stacksTo(1).durability(0));
        this.addFortitude = addFortitude;
        this.addPrudence = addPrudence;
        this.addTemperance = addTemperance;
        this.addJustice = addJustice;
    }
    public CuriosItem(Builder builder){
        this(builder.addFortitude, builder.addPrudence, builder.addTemperance, builder.addJustice, builder.properties);
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
        if(addFortitude!=0)
            modifier.put(PmEntityAttributes.FORTITUDE_ADDITIONAL,
                    new AttributeModifier(id, addFortitude, AttributeModifier.Operation.ADD_VALUE));
        if(addPrudence!=0)
            modifier.put(PmEntityAttributes.PRUDENCE_ADDITIONAL,
                    new AttributeModifier(id, addPrudence, AttributeModifier.Operation.ADD_VALUE));
        if(addTemperance!=0)
            modifier.put(PmEntityAttributes.TEMPERANCE_ADDITIONAL,
                    new AttributeModifier(id, addTemperance, AttributeModifier.Operation.ADD_VALUE));
        if(addJustice!=0)
            modifier.put(PmEntityAttributes.JUSTICE_ADDITIONAL,
                    new AttributeModifier(id, addJustice, AttributeModifier.Operation.ADD_VALUE));
        return modifier;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


    public static class Builder {
        private float addFortitude,addPrudence,addTemperance,addJustice;
        private Item.Properties properties = new Item.Properties().durability(0).stacksTo(1);

        public Builder(){
        }
        public Builder(float addFortitude, float addPrudence, float addTemperance, float addJustice) {
            this.addFortitude = addFortitude;
            this.addPrudence = addPrudence;
            this.addTemperance = addTemperance;
            this.addJustice = addJustice;
        }
        private Builder properties(Item.Properties properties) {
            this.properties = properties;
            return this;
        }
    }
}
