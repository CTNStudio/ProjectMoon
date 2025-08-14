package ctn.project_moon.tool;

import ctn.project_moon.init.PmDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class PmTool {
	
	public static PmDamageSources getDamageSource(Entity entity) {
		return new PmDamageSources(entity.level().registryAccess());
	}
	
	public static void incrementNbt(Entity entity, String attribute, int value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putInt(attribute, nbt.getInt(attribute) + value);
	}
	
	public static void incrementNbt(Entity entity, String attribute, double value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putDouble(attribute, nbt.getInt(attribute) + value);
	}
	
	public static void incrementNbt(CompoundTag nbt, String attribute, int value) {
		nbt.putInt(attribute, nbt.getInt(attribute) + value);
	}
	
	public static void incrementNbt(CompoundTag nbt, String attribute, double value) {
		nbt.putDouble(attribute, nbt.getInt(attribute) + value);
	}
	
	public static void incrementNbt(Entity entity, String attribute, boolean value) {
		CompoundTag nbt = entity.getPersistentData();
		nbt.putBoolean(attribute, value);
	}
}
