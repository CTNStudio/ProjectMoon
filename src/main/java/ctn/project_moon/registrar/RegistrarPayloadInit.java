package ctn.project_moon.registrar;

import ctn.project_moon.common.payloadInit.data.FourColorData;
import ctn.project_moon.common.payloadInit.data.OpenPlayerAttributeScreenData;
import ctn.project_moon.common.payloadInit.data.RationalityValueData;
import ctn.project_moon.common.payloadInit.data.TempNbtAttrData;
import ctn.project_moon.common.payloadInit.data.skill.AllSkillsData;
import ctn.project_moon.common.payloadInit.data.skill.SkillsData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class RegistrarPayloadInit {
	/** 注册有效载荷数据包 */
	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1.0");
		registrar.playToClient(RationalityValueData.TYPE, RationalityValueData.CODEC, RationalityValueData::server);
		registrar.playToClient(TempNbtAttrData.TYPE, TempNbtAttrData.CODEC, TempNbtAttrData::server);
		registrar.playToClient(FourColorData.TYPE, FourColorData.CODEC, FourColorData::server);
		registrar.playToClient(AllSkillsData.TYPE, AllSkillsData.CODEC, AllSkillsData::server);
		registrar.playToClient(SkillsData.TYPE, SkillsData.CODEC, SkillsData::server);
		
		registrar.playToServer(OpenPlayerAttributeScreenData.TYPE, OpenPlayerAttributeScreenData.CODEC, OpenPlayerAttributeScreenData::client);
	}
}
