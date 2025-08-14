package ctn.project_moon.api.skill;


import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.capability_provider.EntitySkillHandler;
import ctn.project_moon.init.PmSkills;
import ctn.project_moon.mixin_extend.IModPlayerMixin;
import ctn.project_moon.tool.PmPayloadTool;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/// 技能系统工具
public class SkillTool {
	
	/// 玩家读取
	public static void playerLoadAllSkills(Player player) {
		IModPlayerMixin playerMixin = (IModPlayerMixin) player;
		CompoundTag nbt = player.getPersistentData();
		RegistryAccess registry = player.level().registryAccess();
		ISkillHandler handler = playerMixin.getSkillHandler();
		
		EntitySkillHandler.loadAllSkills(nbt, handler, registry);
		handler.setSkillSlotIndex(nbt.getInt("skillSlotIndex"));
	}
	
	/// 玩家保存
	public static void playerSaveAllSkills(ServerPlayer player) {
		IModPlayerMixin playerMixin = (IModPlayerMixin) player;
		CompoundTag nbt = player.getPersistentData();
		RegistryAccess registry = player.level().registryAccess();
		ISkillHandler handler = playerMixin.getSkillHandler();
		int skillSlotIndex = handler.getSkillSlotIndex();
		
		handler.clearSkills(); // 清除
		handler.addSkill(PmSkills.MAGIC_BULLET_FLOODED.get()); // 添加
		handler.addSkill(PmSkills.MAGIC_BULLET_PENETRATING.get()); // 添加
		handler.getSkill(0).setCd(20 * 10); // 设置技能CD
		handler.getSkill(0).setKeyName("key.keyboard.a"); // 设置技能按键
		
		EntitySkillHandler.saveAllSkills(nbt, handler, registry);
		nbt.putInt("skillSlotIndex", skillSlotIndex);
		
		PmPayloadTool.syncAllSkills(player);
	}
}
