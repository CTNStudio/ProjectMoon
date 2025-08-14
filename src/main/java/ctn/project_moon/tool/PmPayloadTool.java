package ctn.project_moon.tool;

import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.common.payloadInit.data.FourColorData;
import ctn.project_moon.common.payloadInit.data.RationalityValueData;
import ctn.project_moon.common.payloadInit.data.open_screen.OpenPlayerAttributeScreenData;
import ctn.project_moon.common.payloadInit.data.open_screen.OpenPlayerSkillScreenData;
import ctn.project_moon.common.payloadInit.data.skill.AllSkillsData;
import ctn.project_moon.common.payloadInit.data.skill.SkillsData;
import ctn.project_moon.common.skill.SkillStack;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import static ctn.project_moon.api.attr.MobGeneralAttribute.RATIONALITY_VALUE;
import static ctn.project_moon.api.attr.PlayerAttribute.*;

/// 数据包集
public class PmPayloadTool {
	/// 同步玩家数据-服务端到客户端 ///
	
	/// 同步理智值
	public static void syncRationality(ServerPlayer serverPlayer) {
		CompoundTag nbt = serverPlayer.getPersistentData();
		RationalityValueData customPacketPayload = new RationalityValueData(
				nbt.getDouble(RATIONALITY_VALUE));
		
		sendToPlayer(serverPlayer, customPacketPayload);
	}
	
	/// 同步四色属性
	public static void syncFourColorAttribute(ServerPlayer serverPlayer) {
		CompoundTag nbt = serverPlayer.getPersistentData();
		FourColorData customPacketPayload = new FourColorData(
				nbt.getInt(BASE_FORTITUDE),
				nbt.getInt(BASE_PRUDENCE),
				nbt.getInt(BASE_TEMPERANCE),
				nbt.getInt(BASE_JUSTICE)
		);
		
		sendToPlayer(serverPlayer, customPacketPayload);
	}
	
	/// 同步技能
	public static void syncAllSkills(ServerPlayer serverPlayer) {
		IModPlayerMixin playerMixin = (IModPlayerMixin) serverPlayer;
		ISkillHandler handler = playerMixin.getSkillHandler();
		NonNullList<SkillStack> skills = handler.getSkills();
		AllSkillsData customPacketPayload = new AllSkillsData(skills);
		
		sendToPlayer(serverPlayer, customPacketPayload);
	}
	
	/// 同步技能索引
	public static void syncSkill(ServerPlayer serverPlayer, SkillStack skill, int index) {
		SkillsData customPacketPayload = new SkillsData(skill, index);
		
		sendToPlayer(serverPlayer, customPacketPayload);
	}
	
	public static void syncSkillSlotIndex(){
	
	}
	
	/// 同步玩家数据-客户端到服务端 ///
	
	
	/// 打开界面 ///
	
	/// 打开玩家属性界面
	public static void openPlayerAttributeScreen(ItemStack carried){
		sendToServer(new OpenPlayerAttributeScreenData(carried));
	}
	
	/// 打开玩家技能界面
	public static void openPlayerSkillScreen(ItemStack carried){
		sendToServer(new OpenPlayerSkillScreenData(carried));
	}
	
	/// 工具 ///
	
	/// 发送玩家数据包（服务端到客户端）
	public static void sendToPlayer(ServerPlayer serverPlayer, CustomPacketPayload customPacketPayload) {
		PacketDistributor.sendToPlayer(serverPlayer, customPacketPayload);
	}
	
	/// 发送玩家数据包（客户端到服务端）
	public static void sendToServer(CustomPacketPayload customPacketPayload) {
		PacketDistributor.sendToServer(customPacketPayload);
	}
}
