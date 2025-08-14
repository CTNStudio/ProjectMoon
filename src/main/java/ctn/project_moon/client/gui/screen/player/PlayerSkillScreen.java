package ctn.project_moon.client.gui.screen.player;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.client.gui.tool.blit_nine_sliced.SliceSprite;
import ctn.project_moon.client.gui.tool.blit_nine_sliced.SliceSpriteData;
import ctn.project_moon.common.menu.PlayerSkillMenu;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICuriosScreen;

import java.util.ArrayList;
import java.util.List;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PlayerSkillScreen extends EffectRenderingInventoryScreen<PlayerSkillMenu> implements ICuriosScreen {
	// 纹理
	public static final ResourceLocation BG = getPath("container/player_skill");
	
	// 属性
	private final Player                            player;
	private final IModPlayerMixin                   playerMixin;
	private final ISkillHandler                     handler;
	private final NonNullList<SkillStack>           copySkills;
	private final List<PlayerSkillScreenV.SkillBox> skillBoxList = new ArrayList<>();
	
	public PlayerSkillScreen(PlayerSkillMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		player      = playerInventory.player;
		playerMixin = (IModPlayerMixin) player;
		handler     = playerMixin.getSkillHandler();
		copySkills  = NonNullList.copyOf(handler.getSkills());
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/" + path + ".png");
	}
	
	private static @NotNull ResourceLocation getSpritePath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_skill/" + path);
	}
	
	@Override
	protected void init() {
		imageWidth  = Math.max(176, (int) (width * 0.4));
		imageHeight = Math.max(166, (int) (height * 0.7));
		super.init();
		int stackX = leftPos + 6;
		int stackY = topPos + 17;
		skillBoxList.clear();
	}
	
	@Override
	protected <T extends GuiEventListener & NarratableEntry> @NotNull T addWidget(@NotNull T listener) {
		return super.addWidget(listener);
	}
	
	@Override
	protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
	}
	
	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		SliceSprite.blitNineSliced(guiGraphics, leftPos, topPos,
				new SliceSpriteData(BG,
						176, 166,
						0, 0, imageWidth, imageHeight,
						5, 18, 14, 7));
	}
}
