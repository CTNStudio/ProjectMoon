package ctn.project_moon.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 多重状态图像控件(1维)
 *
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class StateSprite extends AbstractWidget implements MessageTooltip{
	protected final ResourceLocation[] texture;
	protected final List<Component>  messages = new ArrayList<>();
	protected       int              index   = 0;   // 索引从0开始
	protected       Tooltip          tooltip;
	
	public StateSprite(int x, int y, int width, int height, ResourceLocation[] texture, Component component) {
		super(x, y, width, height, component);
		this.texture = texture;
	}
	
	@Override
	protected void renderWidget(GuiGraphics guiGraphics,
			int mouseX, int mouseY, float partialTick) {
		guiGraphics.blitSprite(texture[index], getX(), getY(), width, height);
		if (isHovered()) {
			renderTooltip(guiGraphics, mouseX, mouseY);
		}
	}
	
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	
	@SuppressWarnings("DuplicatedCode")
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(0, 0, 100);
		if (tooltip != null) {
			tooltip.run(this, guiGraphics, mouseX, mouseY);
		}
		if (messages.isEmpty()) {
			guiGraphics.renderTooltip(Minecraft.getInstance().font, getMessage(), mouseX, mouseY);
			return;
		}
		guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, messages, mouseX, mouseY);
		guiGraphics.pose().popPose();
	}
	
	@Override
	public List<Component> getMessageList() {
		return messages;
	}
	
	public void setIndex(int index){
		// 索引不能为0
		assert index != 0 : "Index cannot be 0 !";
		// 索引不能大于或等于纹理数量
		assert index < texture.length : "Index cannot be greater than or equal to the number of textures !";
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	
	@Override
	protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}
	
	public interface Tooltip {
		void run(StateSprite widget, GuiGraphics guiGraphics, int mouseX, int mouseY);
	}
}
