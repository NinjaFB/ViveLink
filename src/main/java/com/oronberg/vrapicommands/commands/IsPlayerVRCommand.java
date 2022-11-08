package com.oronberg.vrapicommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import java.util.Collections;
import java.util.Set;

import com.oronberg.vrapicommands.VRPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;


public class IsPlayerVRCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("vr");

        literalargumentbuilder.then(Commands.literal("isvr")).executes((default_command) -> {
            return isvr(default_command, Collections.singleton(default_command.getSource().getPlayerOrException()));
        }).then(Commands.argument("target", EntityArgument.players()).executes((command) -> {
            return isvr(command, Collections.singleton(EntityArgument.getPlayer(command, "target")));
        }));
        dispatcher.register(literalargumentbuilder);
    }

    private static int isvr(CommandContext<CommandSource> command, Set<PlayerEntity> player) {
        boolean isvr = VRPlugin.vrAPI.playerInVR(player.stream().findFirst().get());
        if(isvr) {
            command.getSource().sendSuccess(new StringTextComponent("Yes"), false);
        } else {
                command.getSource().sendSuccess(new StringTextComponent("no"), false);
        }
        return 1;
    }
}