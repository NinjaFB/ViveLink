package com.oronberg.vrapicommands.events;

import com.oronberg.vrapicommands.VRAPICommands;
import com.oronberg.vrapicommands.commands.IsPlayerVRCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = VRAPICommands.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new IsPlayerVRCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
