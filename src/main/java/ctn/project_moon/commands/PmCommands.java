package ctn.project_moon.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ctn.project_moon.api.FourColorAttribute;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static ctn.project_moon.PmMain.MOD_ID;
import static ctn.project_moon.api.FourColorAttribute.*;

public class PmCommands {

	public static final DoubleArgumentType FOUR_COLOR_ARG   = DoubleArgumentType.doubleArg(1d, 500d);
	public static final String             ATTRIBUTE_TO_SET = MOD_ID + ".commands.attribute_to_set.";

	/**
	 * 四色属性设置命令
	 */
	public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("PmSetPlayer")//句首
				.requires(source -> source.hasPermission(2))//权限需求
				.then(Commands.argument("target", EntityArgument.player())
						//勇气分支
						.then(Commands.literal(Type.FORTITUDE.getName()).then(Commands.argument("value", FOUR_COLOR_ARG)
								.executes(context -> extracted(context, Type.FORTITUDE))))
						//谨慎分支
						.then(Commands.literal(Type.PRUDENCE.getName()).then(Commands.argument("value", FOUR_COLOR_ARG)
								.executes(context -> extracted(context, Type.PRUDENCE))))
						//自律分支
						.then(Commands.literal(Type.TEMPERANCE.getName()).then(Commands.argument("value", FOUR_COLOR_ARG)
								.executes(context -> extracted(context, Type.TEMPERANCE))))
						//正义分支
						.then(Commands.literal(Type.JUSTICE.getName()).then(Commands.argument("value", FOUR_COLOR_ARG)
								.executes(context -> extracted(context, Type.JUSTICE))))));
	}

	private static int extracted(CommandContext<CommandSourceStack> context, FourColorAttribute.Type type) throws CommandSyntaxException {
		double value = DoubleArgumentType.getDouble(context, "value");
		ServerPlayer player = EntityArgument.getPlayer(context, "target");
		switch (type) {
			case FORTITUDE -> setBaseFortitude(player, (int) value);
			case PRUDENCE -> setBasePrudence(player, (int) value);
			case TEMPERANCE -> setBaseTemperance(player, (int) value);
			case JUSTICE -> setBaseJustice(player, (int) value);
			case COMPOSITE_RATING ->
					throw new UnsupportedOperationException("Composite rating cannot be set directly.");
		}
		context.getSource().sendSuccess(() -> Component.translatable(ATTRIBUTE_TO_SET + type.getName(), value), true);
		return 1;
	}
}
