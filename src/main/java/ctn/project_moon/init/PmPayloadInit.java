package ctn.project_moon.init;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.common.payloadInit.data.FourColorData;
import ctn.project_moon.common.payloadInit.data.RationalityValueData;
import ctn.project_moon.common.payloadInit.data.skill.AllSkillsData;
import ctn.project_moon.common.payloadInit.data.skill.SkillsData;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IPlayerMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import static ctn.project_moon.api.attr.MobGeneralAttribute.RATIONALITY_VALUE;
import static ctn.project_moon.api.attr.PlayerAttribute.*;

/// 数据包集
public class PmPayloadInit {
	/// 同步理智值
	public static void syncRationality(ServerPlayer serverPlayer) {
		CompoundTag nbt = serverPlayer.getPersistentData();
		sendToPlayer(
				serverPlayer,
				new RationalityValueData(
						nbt.getDouble(RATIONALITY_VALUE)));
	}
	
	/// 同步四色属性
	public static void syncFourColorAttribute(ServerPlayer serverPlayer) {
		CompoundTag nbt = serverPlayer.getPersistentData();
		sendToPlayer(
				serverPlayer,
				new FourColorData(
						nbt.getInt(BASE_FORTITUDE),
						nbt.getInt(BASE_PRUDENCE),
						nbt.getInt(BASE_TEMPERANCE),
						nbt.getInt(BASE_JUSTICE)
				));
	}
	
	/// 同步技能
	public static void syncSkill(ServerPlayer player) {
		ISkillHandler handler = ((IPlayerMixin) player).getSkillHandler();
		sendToPlayer(
				player,
				new AllSkillsData(handler.getSkills()));
	}
	
	/// 同步技能
	public static void syncSkill(ServerPlayer player, SkillStack skill, int index) {
		sendToPlayer(
				player,
				new SkillsData(skill, index));
	}
	
	/// 发送玩家数据包（服务端到客户端）
	public static void sendToPlayer(ServerPlayer serverPlayer, CustomPacketPayload customPacketPayload) {
		PacketDistributor.sendToPlayer(serverPlayer, customPacketPayload);
	}
}
