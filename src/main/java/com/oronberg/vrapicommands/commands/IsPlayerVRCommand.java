package com.oronberg.vrapicommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import java.util.Collections;
import java.util.Set;

import com.oronberg.vrapicommands.VRPlugin;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;

import static com.oronberg.vrapicommands.commands.Controller.*;

public class IsPlayerVRCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("vr");
        for(Controller controller : Controller.values())
            literalargumentbuilder.then(Commands.literal("isvr").executes((command) ->
                isvr(command, Collections.singleton(command.getSource().getPlayerOrException()))).then(Commands.argument("target", EntityArgument.player()).executes((command) ->
                isvr(command, Collections.singleton(EntityArgument.getPlayer(command, "target")))))).then(Commands.literal("getrot").then(Commands.literal(controller.getName()).executes((command) ->
                getrot(command, Collections.singleton(command.getSource().getPlayerOrException()), controller)).then(Commands.argument("target", EntityArgument.player()).executes((command) ->
                getrot(command, Collections.singleton(EntityArgument.getPlayer(command, "target")), controller))))).then(Commands.literal("getpos").then(Commands.literal(controller.getName()).executes((command) ->
                getpos(command, Collections.singleton(command.getSource().getPlayerOrException()), controller)).then(Commands.argument("target", EntityArgument.player()).executes((command) ->
                getpos(command, Collections.singleton(EntityArgument.getPlayer(command, "target")), controller)))));


        dispatcher.register(literalargumentbuilder);
    }

    private static int isvr(CommandContext<CommandSource> command, Set<PlayerEntity> player) {
        if(!player.stream().findFirst().isPresent()) {
            command.getSource().sendFailure(new StringTextComponent("A thing happened and the command failed"));
        }
        boolean isvr = VRPlugin.vrAPI.playerInVR(player.stream().findFirst().get());
        if(isvr) {
            command.getSource().sendSuccess(new StringTextComponent("Target player is in VR."), false);
        } else {
                command.getSource().sendSuccess(new StringTextComponent("Target player is not in VR."), false);
        }
        return 0;
    }

    private static int getrot(CommandContext<CommandSource> command, Set<PlayerEntity> player, Controller controller) {
        IVRPlayer vrplayer = VRPlugin.vrAPI.getVRPlayer(player.stream().findFirst().get());
        if(vrplayer == null) {
            command.getSource().sendSuccess(new StringTextComponent("Target player is not in VR."), false);
            return 1;
        }
        String rotation;
        if(controller == MAIN) {
            rotation = String.valueOf(vrplayer.getController0().getPitch())+","+String.valueOf(vrplayer.getController0().getYaw())+","+String.valueOf(vrplayer.getController0().getRoll());
        } else if (controller == OFF) {
            rotation = String.valueOf(vrplayer.getController1().getPitch())+","+String.valueOf(vrplayer.getController1().getYaw())+","+String.valueOf(vrplayer.getController1().getRoll());
        } else if (controller == HMD) {
            rotation = String.valueOf(vrplayer.getHMD().getPitch())+","+String.valueOf(vrplayer.getHMD().getYaw())+","+String.valueOf(vrplayer.getHMD().getRoll());
        } else {
            command.getSource().sendFailure(new StringTextComponent("controller_type was incorrect type."));
            return 1;
        }

        String message = rotation;
        command.getSource().sendSuccess(new StringTextComponent(message), false);
        return 0;
    }
    private static int getpos(CommandContext<CommandSource> command, Set<PlayerEntity> player, Controller controller) {
        IVRPlayer vrplayer = VRPlugin.vrAPI.getVRPlayer(player.stream().findFirst().get());
        if(vrplayer == null) {
            command.getSource().sendSuccess(new StringTextComponent("Target player is not in VR."), false);
            return 1;
        }
        Vector3d position;
        if(controller == MAIN) {
            position = vrplayer.getController0().position();
        } else if (controller == OFF) {
            position = vrplayer.getController1().position();
        } else if (controller == HMD) {
            position = vrplayer.getHMD().position();
        } else {
            command.getSource().sendFailure(new StringTextComponent("controller_type was incorrect type"));
            return 1;
        }

        String message = position;
        command.getSource().sendSuccess(new StringTextComponent(message), false);
        return 0;
    }
}