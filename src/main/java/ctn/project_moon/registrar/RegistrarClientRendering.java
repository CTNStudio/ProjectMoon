package ctn.project_moon.registrar;

import ctn.project_moon.client.particles.TextParticle;
import ctn.project_moon.client.screen.PlayerAttributeScreen;
import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import ctn.project_moon.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.project_moon.init.PmEntitys;
import ctn.project_moon.init.PmParticleTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.init.PmMenuType.PLAYER_ATTRIBUTE_MENU;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegistrarClientRendering {

	/**
	 * 注册实体渲染器
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		EntityRenderers.register(PmEntitys.TRAINING_RABBITS.get(), TrainingRabbits.TrainingRabbitsRenderer::new);
		EntityRenderers.register(PmEntitys.PARADISE_LOST_SPIKEWEED.get(), ParadiseLostSpikeweed.TrainingRabbitsRenderer::new);
	}

	/** 注册粒子渲染器 */
	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(PmParticleTypes.TEXT_PARTICLE_TYPE.get(), TextParticle.Provider::new);
	}

	/**
	 * 注册菜单渲染器
	 */
	@SubscribeEvent
	public static void registerMenuScreens(RegisterMenuScreensEvent event) {
		event.register(PLAYER_ATTRIBUTE_MENU.get(), PlayerAttributeScreen::new);
	}
}
