package ctn.project_moon.util;

import ctn.project_moon.init.PmDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class PmTool {

	public static int colorConversion(String color) {
		return TextColor.parseColor(handleColor(color)).getOrThrow().getValue();
	}

	public static PmDamageSources getDamageSource(Entity entity) {
		return new PmDamageSources(entity.level().registryAccess());
	}

	public static @NotNull MutableComponent createColorText(String text, String color) {
		color = handleColor(color);
		return Component.literal(text).withColor(PmTool.colorConversion(color));
	}

	private static @NotNull String handleColor(String color) {
		if (color == null || Pattern.matches(color, "^#[a-zA-Z\\d]{6}")) {
			color = "#ffffff";
		}
		return color;
	}

	public static @NotNull MutableComponent i18ColorText(String text, String color) {
		color = handleColor(color);
		return Component.translatable(text).withColor(PmTool.colorConversion(color));
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
