package com.oronberg.vivelink.events;

import com.oronberg.vivelink.ViveLink;
import com.oronberg.vivelink.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = ViveLink.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        Commands.register(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
