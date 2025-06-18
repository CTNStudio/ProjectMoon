package ctn.project_moon.api.skill;


import ctn.project_moon.capability.ISkillHandler;
import ctn.project_moon.capability_provider.EntitySkillHandler;
import ctn.project_moon.init.PmPayloadInit;
import ctn.project_moon.mixin_extend.IPlayerMixin;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/// 技能系统工具
public class SkillTool {
	public static void playerLoadAllSkills(Player player) {
		IPlayerMixin playerMixin = (IPlayerMixin) player;
		CompoundTag nbt = player.getPersistentData();
		RegistryAccess registry = player.level().registryAccess();
		EntitySkillHandler.loadAllSkills(nbt, playerMixin.getSkillHandler(), registry);
	}
	
	public static void playerSaveAllSkills(ServerPlayer player) {
		IPlayerMixin playerMixin = (IPlayerMixin) player;
		CompoundTag nbt = player.getPersistentData();
		RegistryAccess registry = player.level().registryAccess();
		ISkillHandler handler = playerMixin.getSkillHandler();

//		handler.clearSkills(); // 清除
//		handler.addSkill(PmSkills.MAGIC_BULLET_FLOODED.get()); // 添加
//		handler.getSkill(0).setCd(20 * 110); // 设置技能CD
		
		EntitySkillHandler.saveAllSkills(nbt, handler, registry);
		PmPayloadInit.syncSkill(player);
	}
}
