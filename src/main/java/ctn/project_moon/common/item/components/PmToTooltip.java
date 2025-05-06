package ctn.project_moon.common.item.components;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface PmToTooltip {
    void addToTooltip(Item.TooltipContext context, List<Component> tooltipAdder, TooltipFlag tooltipFlag);
}
