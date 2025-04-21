package ctn.project_moon.events.client;

import ctn.project_moon.client.particles.DamageParticle;
import ctn.project_moon.common.entity.abnos.TrainingRabbits;
import ctn.project_moon.init.PmEntitys;
import ctn.project_moon.init.PmParticleTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 没什么特有的客户端事件
 */
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    /**
     * 注册实体渲染器
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(PmEntitys.TRAINING_RABBITS.get(), TrainingRabbits.TrainingRabbitsRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
       event.registerSpecial(PmParticleTypes.DAMAGE_PARTICLE_TYPE.get(), new DamageParticle.Provider());
    }
}
