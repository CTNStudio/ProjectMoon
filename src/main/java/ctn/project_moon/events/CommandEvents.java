package ctn.project_moon.events;

import ctn.project_moon.commands.PmCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import static ctn.project_moon.PmMain.MOD_ID;

/**
 * 指令事件
 */
@EventBusSubscriber(modid = MOD_ID)
public class CommandEvents {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        PmCommands.registerCommands(event.getDispatcher());
    }
}
