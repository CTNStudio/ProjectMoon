package ctn.project_moon.registrar;

import ctn.project_moon.common.payloadInit.data.FourColorData;
import ctn.project_moon.common.payloadInit.data.RationalityValueData;
import ctn.project_moon.common.payloadInit.data.TempNbtAttrData;
import ctn.project_moon.common.payloadInit.data.open_screen.OpenPlayerAttributeScreenData;
import ctn.project_moon.common.payloadInit.data.open_screen.OpenPlayerSkillScreenData;
import ctn.project_moon.common.payloadInit.data.skill.AllSkillsData;
import ctn.project_moon.common.payloadInit.data.skill.SkillSlotIndexData;
import ctn.project_moon.common.payloadInit.data.skill.SkillsData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static ctn.project_moon.PmMain.MOD_ID;
import static net.neoforged.fml.common.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class RegistrarPayloadInit {
@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1.0");
		/// 接收来自服务端和客户端的数据
		registrar.playBidirectional(SkillSlotIndexData.TYPE, SkillSlotIndexData.CODEC, SkillSlotIndexData::bidirectional);
		
		/// 接收来自服务端的数据
		registrar.playToClient(RationalityValueData.TYPE, RationalityValueData.CODEC, RationalityValueData::toClient);
		registrar.playToClient(TempNbtAttrData.TYPE, TempNbtAttrData.CODEC, TempNbtAttrData::toClient);
		registrar.playToClient(FourColorData.TYPE, FourColorData.CODEC, FourColorData::toClient);
		registrar.playToClient(AllSkillsData.TYPE, AllSkillsData.CODEC, AllSkillsData::toClient);
		registrar.playToClient(SkillsData.TYPE, SkillsData.CODEC, SkillsData::toClient);
		
		/// 接收来自客户端的数据
		registrar.playToServer(OpenPlayerAttributeScreenData.TYPE, OpenPlayerAttributeScreenData.CODEC, OpenPlayerAttributeScreenData::toServer);
		registrar.playToServer(OpenPlayerSkillScreenData.TYPE, OpenPlayerSkillScreenData.CODEC, OpenPlayerSkillScreenData::toServer);
	}
}
