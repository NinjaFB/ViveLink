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
        literalargumentbuilder.then(Commands.literal("isvr").executes((command) ->
                isvr(command, Collections.singleton(command.getSource().getPlayerOrException()))).then(Commands.argument("target", EntityArgument.player()).executes((command) ->
                isvr(command, Collections.singleton(EntityArgument.getPlayer(command, "target"))))));
        dispatcher.register(literalargumentbuilder);
    }

    private static int isvr(CommandContext<CommandSource> command, Set<PlayerEntity> player) {
        if(!player.stream().findFirst().isPresent()) {
            command.getSource().sendFailure(new StringTextComponent("A thing happened and the command failed"));
        }
        boolean isvr = VRPlugin.vrAPI.playerInVR(player.stream().findFirst().get());
        if(isvr) {
            command.getSource().sendSuccess(new StringTextComponent("Yes"), false);
        } else {
                command.getSource().sendSuccess(new StringTextComponent("no"), false);
        }
        return 1;
    }
}