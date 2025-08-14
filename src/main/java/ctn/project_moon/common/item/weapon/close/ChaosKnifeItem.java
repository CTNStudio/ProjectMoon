package ctn.project_moon.common.item.weapon.close;

import ctn.project_moon.api.tool.PmDamageTool;
import ctn.project_moon.common.item.weapon.abstract_item.EgoWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckForNull;
import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.tool.PmDamageTool.ColorType.*;
import static ctn.project_moon.init.PmItemDataComponents.COLOR_DAMAGE_TYPE;
import static ctn.project_moon.tool.PmColourTool.createColorText;
import static ctn.project_moon.tool.PmColourTool.i18ColorText;

/// 混沌刀
public class ChaosKnifeItem extends EgoWeapon {
	public ChaosKnifeItem(Properties properties) {
		super(properties.component(COLOR_DAMAGE_TYPE.get(), PHYSICS.getName()), new Builder().minDamage(6).maxDamage(7).attackSpeed(-1.4F));
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
		ItemStack itemStack = player.getItemInHand(usedHand);
		PmDamageTool.ColorType type = is(itemStack.get(COLOR_DAMAGE_TYPE));
		if (type == null) {
			return InteractionResultHolder.success(itemStack);
		}
		var damageIndex = type.getIndex() + 1;
		if (damageIndex >= values().length) {
			damageIndex = 0;
		}
		itemStack.set(COLOR_DAMAGE_TYPE, values()[damageIndex].getName());
		return InteractionResultHolder.success(itemStack);
	}
	
	@Override
	public Component getColorDamageTypeToTooltip() {
		MutableComponent component = i18ColorText(MOD_ID + ".item_tooltip.geo_describe.damage_type", "#AAAAAA");
		component.append(Component.literal(" ").append(createColorText(" ????", "#ffb638")));
		return component;
	}
	
	@Override
	@CheckForNull
	public PmDamageTool.ColorType getColorDamageType(ItemStack stack) {
		return is(stack.get(COLOR_DAMAGE_TYPE));
	}
	
	@Override
	public List<PmDamageTool.ColorType> getCanCauseDamageTypes() {
		return List.of(PmDamageTool.ColorType.values());
	}
	
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
	}
}
