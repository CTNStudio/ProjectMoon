package ctn.project_moon.registrar;

import ctn.project_moon.common.item.curio.EgoCurioItem;
import ctn.project_moon.common.renderers.CuriosItemRenderer;
import ctn.project_moon.init.PmItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CurioEvents {
	/** 注册饰品渲染 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		register(PmItems.PARADISE_LOST_WINGS.get());
		register(PmItems.MAGIC_BULLET_PIPE.get());
	}

	private static void register(EgoCurioItem item) {
		CuriosRendererRegistry.register(item, () -> new CuriosItemRenderer(item));
	}
}
