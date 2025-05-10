package ctn.project_moon.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import ctn.project_moon.api.PlayerAttribute;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PmCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack>  dispatcher) {
        dispatcher.register(Commands.literal("PmSetPlayer")//句首
                .requires(source -> source.hasPermission(2))//权限需求
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.literal("prudence"))//自律分支
                            .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0d, 500d))
                                .executes(context -> {
                                    Player player = EntityArgument.getPlayer(context, "target");
                                    double value = DoubleArgumentType.getDouble(context, "value");
                                    PlayerAttribute.setPrudence(player, (int)value);
                                    context.getSource().sendSuccess(() -> Component.literal("玩家自律已设置为： " + (int)value), true);
                                    return 1;
                                }))
                        .then(Commands.literal("justice")//正义分支
                            .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0d, 500d))
                                .executes(context -> {
                                    Player player = EntityArgument.getPlayer(context, "target");
                                    double value = DoubleArgumentType.getDouble(context, "value");
                                    PlayerAttribute.setJustice(player, (int)value);
                                    context.getSource().sendSuccess(() -> Component.literal("玩家正义已设置为： " + (int)value), true);
                                    return 1;
                                })))));
    }
}
