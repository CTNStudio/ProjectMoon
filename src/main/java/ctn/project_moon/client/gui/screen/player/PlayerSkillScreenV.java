package ctn.project_moon.client.gui.screen.player;

import com.mojang.blaze3d.platform.InputConstants;
import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.client.gui.hud.SkillLayersDrew;
import ctn.project_moon.client.gui.tool.blit_nine_sliced.SliceSprite;
import ctn.project_moon.client.gui.tool.blit_nine_sliced.SliceSpriteData;
import ctn.project_moon.common.menu.PlayerSkillMenu;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import ctn.project_moon.tool.PmColourTool;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
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

// TODO 重构

/**
 * @author 尽
 */
@OnlyIn(Dist.CLIENT)
public class PlayerSkillScreenV extends EffectRenderingInventoryScreen<PlayerSkillMenu> implements ICuriosScreen {
	// 纹理
	public static final ResourceLocation BG = getPath("container/player_skill");
	
	public static final WidgetSprites SLIDER = new WidgetSprites(
			getSpritePath("slider"),
			getSpritePath("slider_press"),
			getSpritePath("slider_disable")
	);
	// 翻译键
	
	private static final String                  PREFIX       = MOD_ID + ".gui.player_skill";
	public static final  String                  TITLE        = PREFIX + ".title";
	// 属性
	private final        Player                  player;
	private final        IModPlayerMixin         playerMixin;
	private final        ISkillHandler           handler;
	private final        NonNullList<SkillStack> copySkills;
	private final        List<SkillBox>          skillBoxList = new ArrayList<>();
	
	public PlayerSkillScreenV(PlayerSkillMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, Component.translatable(TITLE));
		player      = playerInventory.player;
		playerMixin = (IModPlayerMixin) player;
		handler     = playerMixin.getSkillHandler();
		copySkills  = NonNullList.copyOf(handler.getSkills());
	}
	
	private static @NotNull ResourceLocation getSpritePath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_skill/" + path);
	}
	
	private static @NotNull ResourceLocation getPath(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/" + path + ".png");
	}
	
	@Override
	protected void init() {
		imageWidth  = Math.max(176, (int) (width * 0.4));
		imageHeight = Math.max(166, (int) (height * 0.7));
		super.init();
		int stackX = leftPos + 6;
		int stackY = topPos + 17;
		skillBoxList.clear();
		for (SkillStack stack : copySkills) {
			SkillBox box = new SkillBox(stack, imageWidth - 21, stackX, stackY, font);
			skillBoxList.add(box);
			addRenderableWidget(box);
			addRenderableWidget(box.keyBox);
			addRenderableWidget(box.detailsBox);
			SkillLayersDrew.getSkillIconPath(stack);
		}
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
//		RenderSystem.enableBlend();
//		RenderSystem.defaultBlendFunc();
//		guiGraphics.pose().pushPose();
//		guiGraphics.pose().translate(0, 0, 100);
//		guiGraphics.pose().popPose();
//		RenderSystem.enableDepthTest();  // 重新启用深度测试
//		RenderSystem.disableBlend();     // 禁用混合模式
	}
	
	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderSkillList(guiGraphics, leftPos, topPos, mouseX, mouseY, partialTick);
		super.renderTooltip(guiGraphics, mouseX, mouseY);
	}
	
	private void renderSkillList(@NotNull GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTick) {
		y = topPos + 17;
		for (SkillBox box : skillBoxList) {
			box.render(guiGraphics, mouseX, mouseY, partialTick);
			box.setY(y);
			y += box.getHeight() + 1;
		}
	}
	
	/**
	 * 技能栏 UI 模块
	 *
	 * @author 尽
	 */
	public final static class SkillBox extends AbstractWidget {
		private static final WidgetSprites SKILL_DESCRIPTION = new WidgetSprites(
				getPath("sprites/player_skill/skill_description"),
				getPath("sprites/player_skill/skill_description_press"),
				getPath("sprites/player_skill/skill_description_big"),
				getPath("sprites/player_skill/skill_description_big_press")
		);
		
		private static final WidgetSprites KEY_BINDING_BUTTON = new WidgetSprites(
				getSpritePath("key_binding_button"),
				getSpritePath("key_binding_button_press")
		);
		
		private static final int DETAILS_BOX_HEIGHT = 22;
		private static final int KEY_BOX_WIDTH      = 22;
		private static final int KEY_BOX_HEIGHT     = 22;
		private static final int KEY_ICON_SIZE      = 16;
		private static final int WHITE_COLOR        = PmColourTool.colorConversion("#ffffff");
		
		// UI 组件
		private final Button             detailsBox;
		private final ImageButton        keyBox;
		private final SkillStack         stack;
		private final ResourceLocation   skillIconPath;
		private final InputConstants.Key key;
		private final Component          keyText;
		
		// 坐标与布局参数
		private final int keyX, keyY, keyTextX, keyTextY;
		private final Font    font;
		private       boolean isDetailed = false;
		
		public SkillBox(SkillStack stack, int width, int x, int y, Font font) {
			super(x, y, width, DETAILS_BOX_HEIGHT, Component.empty());
			this.stack = stack;
			this.font  = font;
			
			// 计算坐标
			this.keyTextX = x + width - KEY_BOX_WIDTH / 2 + 1;
			this.keyTextY = y + KEY_BOX_HEIGHT / 2 - font.lineHeight / 2 - 1;
			this.keyX     = x + 2;
			this.keyY     = getY() + 2;
			
			// 加载技能图标
			this.skillIconPath = SkillLayersDrew.getSkillIconPath(stack);
			
			// 解析按键信息
			String keyCode = stack.getKeyName();
			if (keyCode != null && !keyCode.trim().isEmpty()) {
				this.key     = InputConstants.getKey(keyCode.trim());
				this.keyText = key.getDisplayName();
			} else {
				this.key     = null;
				this.keyText = Component.translatable("key.keyboard.unknown");
			}
			
			// 创建按钮组件
			int detailsBoxWidth = width - KEY_BOX_WIDTH - 1;
			Button.Builder builder = new Button.Builder(Component.empty(), (button) -> {
				isDetailed = !isDetailed;
			});
			builder.pos(x, y).size(detailsBoxWidth, height);
			this.detailsBox = builder.build();
			this.keyBox     = new ImageButton(
					x + detailsBoxWidth + 1,
					y,
					KEY_BOX_WIDTH,
					KEY_BOX_HEIGHT,
					KEY_BINDING_BUTTON,
					(button) -> {
					}
			);
		}
		
		@Override
		protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			detailsBox.setY(getY());
			detailsBox.render(guiGraphics, mouseX, mouseY, partialTick);
			// 根据状态选择背景样式
			ResourceLocation bgPath = SKILL_DESCRIPTION.get(!detailsBox.isHovered(), isDetailed);
			
			// 更新高度和背景数据
			height = isDetailed ? Math.max(39, height) : 22;
//	        SpriteSlice.SliceSpriteData bg = isDetailed ? detailedBgData : normalBgData;
			
			// 渲染背景
			SliceSprite.blitNineSliced(guiGraphics, getX(), getY(),
					132, 22,
					new SliceSpriteData(
							bgPath,
							132, 22,
							0, 0, width - 1 - KEY_BOX_WIDTH, height,
							19, isDetailed ? 19 : 2, 2, 2
					));
			
			// 渲染技能图标
			guiGraphics.blitSprite(skillIconPath, keyX, getY() + 2, KEY_ICON_SIZE, KEY_ICON_SIZE);
			
			// 渲染按键图标
			keyBox.setY(getY());
			keyBox.render(guiGraphics, mouseX, mouseY, partialTick);
			if (keyText != null) {
				guiGraphics.drawCenteredString(font, keyText, keyTextX, getY() + KEY_BOX_HEIGHT / 2 - font.lineHeight / 2 - 1, WHITE_COLOR);
			}
		}
		
		@Override
		protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		}
		
		public boolean isDetailed() {
			return isDetailed;
		}
		
		public void setDetailed(boolean detailed) {
			isDetailed = detailed;
		}
	}
	
}
