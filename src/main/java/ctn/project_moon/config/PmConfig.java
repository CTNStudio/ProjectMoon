package ctn.project_moon.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.Logging;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

import static ctn.project_moon.PmMain.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PmConfig {
	public static final Common        COMMON;
	public static final ModConfigSpec COMMON_SPEC;
	static {
		Pair<Common, ModConfigSpec> pair = new Builder().configure(Common::new);
		COMMON      = pair.getLeft();
		COMMON_SPEC = pair.getRight();
	}

	public static final Server        SERVER;
	public static final ModConfigSpec SERVER_SPEC;
	static {
		Pair<Server, ModConfigSpec> pair = new Builder().configure(Server::new);
		SERVER      = pair.getLeft();
		SERVER_SPEC = pair.getRight();
	}

	public static final ClientConfig  CLIENT;
	public static final ModConfigSpec CLIENT_SPEC;
	static {
		Pair<ClientConfig, ModConfigSpec> pair = new Builder().configure(ClientConfig::new);
		CLIENT      = pair.getLeft();
		CLIENT_SPEC = pair.getRight();
	}

	private static BooleanValue builderConfig(Builder builder, String comment, String translation, boolean defaultValue) {
		return builder.translation(translationKey(translation)).comment(comment).define(translation, defaultValue);
	}

	private static DoubleValue builderConfig(Builder builder, String comment, String translation, double defaultValue, double minValue, double maxValue) {
		return builder.translation(translationKey(translation)).comment(comment).defineInRange(translation, defaultValue, minValue, maxValue);
	}

	private static IntValue builderConfig(Builder builder, String comment, String translation, int defaultValue, int minValue, int maxValue) {
		return builder.translation(translationKey(translation)).comment(comment).defineInRange(translation, defaultValue, minValue, maxValue);
	}

	public static String translationKey(String string) {
		return MOD_ID + ".configgui." + string;
	}

	public static String commentKey(String string) {
		return MOD_ID + ".configgui." + string + ".tooltip";
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		LogManager.getLogger().debug(Logging.FORGEMOD, "Loaded ProjectMoon config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		LogManager.getLogger().debug(Logging.FORGEMOD, "ProjectMoon config just got changed on the file system!");
	}

	/**
	 * 双端配置
	 */
	public static class Common {
		//理智/精神伤害配置
		public final BooleanValue ENABLE_RATIONALITY;

		Common(Builder builder) {
			ENABLE_RATIONALITY = builderConfig(
					builder, "是否禁用理智", "enable_rationality", true);
		}
	}

	/**
	 * 服务器配置
	 */
	public static class Server {
		//异想体相关的配置

		//伤害相关的配置
		public final BooleanValue ENABLE_FOUR_COLOR_DAMAGE;

		//理智/精神伤害配置
		public final BooleanValue ENABLE_SPIRIT_DAMAGE;
		public final BooleanValue ENABLE_NATURAL_RATIONALITY_SPIRIT;
		public final BooleanValue ENABLE_LOW_RATIONALITY_NEGATIVE_EFFECT;

		//灵魂伤害配置
		public final BooleanValue ENABLE_THE_SOUL_DAMAGE;
		public final BooleanValue THE_SOUL_AFFECT_ABOMINATIONS;
		public final BooleanValue THE_SOUL_AFFECT_PLAYERS;
		public final BooleanValue THE_SOUL_AFFECT_ENTITIES;

		// 勇气配置
		public final IntValue FORTITUDE_INITIAL_VALUE;

		// 谨慎配置
		public final IntValue PRUDENCE_INITIAL_VALUE;

		// 自律配置
		public final IntValue    TEMPERANCE_INITIAL_VALUE;
		public final DoubleValue TEMPERANCE_BLOCK_BREAK_SPEED;
		public final DoubleValue TEMPERANCE_KNOCKBACK_SPEED;

		// 正义配置
		public final IntValue    JUSTICE_INITIAL_VALUE;
		public final DoubleValue JUSTICE_MOVEMENT_SPEED;
		public final DoubleValue JUSTICE_ATTACK_SPEED;
		public final DoubleValue JUSTICE_SWIM_SPEED;
		public final DoubleValue JUSTICE_FLIGHT_SPEED;
		public final DoubleValue VANILLA_FLYING_SPEED;


		Server(Builder builder) {
			builder.push("fortitude");
			FORTITUDE_INITIAL_VALUE = builderConfig(
					builder, "勇气默认点数", "fortitude_initial_value", 20, 1, 1024);
			builder.pop();

			builder.push("prudence");
			PRUDENCE_INITIAL_VALUE = builderConfig(
					builder, "谨慎默认点数", "prudence_initial_value", 20, 1, 1024);
			builder.pop();

			builder.push("temperance");
			TEMPERANCE_INITIAL_VALUE     = builderConfig(
					builder, "自律默认点数", "temperance_initial_value", 1, 1, 1024);
			TEMPERANCE_BLOCK_BREAK_SPEED = builderConfig(
					builder, "自律加成：方块挖掘速度", "temperance_block_break_speed", 0.02, 0, 1024);
			TEMPERANCE_KNOCKBACK_SPEED   = builderConfig(
					builder, "自律加成：近战击退", "temperance_knockback_speed", 0.015, 0, 1024);
			builder.pop();

			builder.push("justice");
			JUSTICE_INITIAL_VALUE  = builderConfig(
					builder, "正义默认点数", "justice_initial_value", 1, 1, 1024);
			JUSTICE_MOVEMENT_SPEED = builderConfig(
					builder, "正义加成：移速", "justice_movement_speed", 0.001, 0, 1024);
			JUSTICE_ATTACK_SPEED   = builderConfig(
					builder, "正义加成：近战攻击速度", "justice_attack_speed", 0.01, 0, 1024);
			JUSTICE_SWIM_SPEED     = builderConfig(
					builder, "正义加成：游泳速度", "justice_swim_speed", 0.01, 0, 1024);
			JUSTICE_FLIGHT_SPEED   = builderConfig(
					builder, "正义加成：飞行速度", "justice_flight_speed", 0.00013, 0, 1024);
			VANILLA_FLYING_SPEED   = builderConfig(
					builder, "玩家默认飞行速度", "vanilla_flying_speed", 0.05, 0, 1024);
			builder.pop();

			builder.push("enable_four_color_damage");
			ENABLE_FOUR_COLOR_DAMAGE               = builderConfig(
					builder, "四色伤害系统", "enable_four_color_damage", true);
			ENABLE_SPIRIT_DAMAGE                   = builderConfig(
					builder, "精神伤害系统", "enable_spirit_damage", true);
			ENABLE_NATURAL_RATIONALITY_SPIRIT      = builderConfig(
					builder, "自然恢复理智值", "enable_natural_rationality_spirit", true);
			ENABLE_LOW_RATIONALITY_NEGATIVE_EFFECT = builderConfig(
					builder, "玩家低理智负面效果", "enable_low_rationality_negative_effect", true);
			ENABLE_THE_SOUL_DAMAGE                 = builderConfig(
					builder, "灵魂伤害系统", "enable_the_soul_damage", true);
			THE_SOUL_AFFECT_ABOMINATIONS           = builderConfig(
					builder, "灵魂百分比伤害对异想体生效", "the_soul_affect_abominations", false);
			THE_SOUL_AFFECT_PLAYERS                = builderConfig(
					builder, "灵魂百分比伤害对玩家生效", "the_soul_affect_players", true);
			THE_SOUL_AFFECT_ENTITIES               = builderConfig(
					builder, "灵魂百分比伤害对非异想体的生物生效", "the_soul_affect_entities", false);
			builder.pop();
		}
	}

	/**
	 * 客户端配置
	 */
	public static class ClientConfig {
		public final BooleanValue ENABLE_LOW_RATIONALITY_FILTER;
		public final BooleanValue ENABLE_FOUR_COLOR_DAMAGE_FILTER;

		ClientConfig(Builder builder) {
			ENABLE_LOW_RATIONALITY_FILTER   = builderConfig(
					builder, "玩家低理智滤镜", "enable_low_rationality_filter", true);
			ENABLE_FOUR_COLOR_DAMAGE_FILTER = builderConfig(
					builder, "玩家遭受四色伤害滤镜", "enable_four_color_damage_filter", true);
		}
	}
}
