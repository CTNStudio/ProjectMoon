package ctn.project_moon.common.item.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.project_moon.api.FourColorAttribute;
import ctn.project_moon.client.models.GeoCurioModel;
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

import static ctn.project_moon.init.PmItemDataComponents.IS_RESTRAIN;

/**
 * ego饰品 普通饰品请额外建个类
 *
 * @author Dusttt
 */
public class EgoCurioItem extends Item implements ICurioItem, GeoItem {
	protected final float                       addFortitude;
	protected final float                       addPrudence;
	protected final float                       addTemperance;
	protected final float                       addJustice;
	protected final GeoCurioModel<EgoCurioItem> model;
	private final   AnimatableInstanceCache     cache = GeckoLibUtil.createInstanceCache(this);

	public EgoCurioItem(float addFortitude, float addPrudence, float addTemperance, float addJustice, Builder builder) {
		super(builder.properties.component(IS_RESTRAIN, false).stacksTo(1).durability(0));
		this.model         = builder.model;
		this.addFortitude  = addFortitude;
		this.addPrudence   = addPrudence;
		this.addTemperance = addTemperance;
		this.addJustice    = addJustice;
	}

	public EgoCurioItem(Builder builder) {
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
			modifier.put(
					fortitudeAdditional,
					new AttributeModifier(id, addFortitude, AttributeModifier.Operation.ADD_VALUE));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public GeoCurioModel<EgoCurioItem> getModel() {
		return model;
	}

	public static class Builder {
		private float                       addFortitude;
		private float                       addPrudence;
		private float                       addTemperance;
		private float                       addJustice;
		private Item.Properties             properties = new Item.Properties();
		private GeoCurioModel<EgoCurioItem> model;

		public Builder(GeoCurioModel<EgoCurioItem> model) {
			this.model = model;
		}

		public Builder(float addFortitude, float addPrudence, float addTemperance, float addJustice, GeoCurioModel<EgoCurioItem> model) {
			this(model);
			this.fortitude(addFortitude);
			this.prudence(addPrudence);
			this.temperance(addTemperance);
			this.justice(addJustice);
		}

		public Builder properties(Item.Properties properties) {
			this.properties = properties;
			return this;
		}

		public Builder model(GeoCurioModel<EgoCurioItem> model) {
			this.model = model;
			return this;
		}

		public Builder fortitude(float addFortitude) {
			this.addFortitude = addFortitude;
			return this;
		}

		public Builder prudence(float addPrudence) {
			this.addPrudence = addPrudence;
			return this;
		}

		public Builder temperance(float addTemperance) {
			this.addTemperance = addTemperance;
			return this;
		}

		public Builder justice(float addJustice) {
			this.addJustice = addJustice;
			return this;
		}

	}
}
