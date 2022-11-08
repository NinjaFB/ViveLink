package com.oronberg.vrapicommands.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.oronberg.vrapicommands.VRPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.core.jmx.Server;


public class IsPlayerVRCommand {
    public IsPlayerVRCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("isvr").executes((command) -> {
            return isvr(command, Collections.singleton(command.getSource().getPlayerOrException()));
        }));

    }

    private int isvr(CommandContext<CommandSource> command, Set<PlayerEntity> player) {
        boolean isvr = VRPlugin.vrAPI.playerInVR(player.stream().findFirst().get());
        if(isvr) {
            command.getSource().sendSuccess(new StringTextComponent("Yes"), false);
        } else {
            command.getSource().sendSuccess(new StringTextComponent("no"), false);
        }
        return 1;
    }
}