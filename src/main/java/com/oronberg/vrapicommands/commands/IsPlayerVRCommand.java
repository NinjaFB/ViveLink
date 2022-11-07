package com.oronberg.vrapicommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class IsPlayerVRCommand {
    public IsPlayerVRCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("vrapi")).then(Commands.literal("isvr"))
    }

}
