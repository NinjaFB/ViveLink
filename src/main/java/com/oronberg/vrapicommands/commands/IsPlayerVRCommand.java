package com.oronberg.vrapicommands.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.PlayerEntity;


public class IsPlayerVRCommand {
    public IsPlayerVRCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("vrapi").then(Commands.literal("isvr").then(Commands.argument("target", EntityArgument.players()).executes((command) -> {
            return isvr(command, EntityArgument.getPlayers(command, "target"));
        }))));
    }

    private int isvr(CommandSource source, Collection<PlayerEntity> serverplayer) throws CommandSyntaxException {
        
        return 0;
    }
}
