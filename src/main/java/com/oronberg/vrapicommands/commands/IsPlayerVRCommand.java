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
                isvr(command, Collections.singleton(EntityArgument.getPlayer(command, "target")))))).then(Commands.literal("getvec").then(Commands.literal(controller.getName()).executes((command) ->
                getvec(command, Collections.singleton(command.getSource().getPlayerOrException()), controller)).then(Commands.argument("target", EntityArgument.player()).executes((command) ->
                getvec(command, Collections.singleton(EntityArgument.getPlayer(command, "target")), controller)))));


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
        return 0;
    }

    private static int getvec(CommandContext<CommandSource> command, Set<PlayerEntity> player, Controller controller) {
        IVRPlayer vrplayer = VRPlugin.vrAPI.getVRPlayer(player.stream().findFirst().get());
        Vector3d lookangle;
        if(controller == MAIN) {
            lookangle = vrplayer.getController0().getLookAngle();
        } else if (controller == OFF) {
            lookangle = vrplayer.getController1().getLookAngle();
        } else if (controller == HMD) {
            lookangle = vrplayer.getHMD().getLookAngle();
        } else {
            command.getSource().sendFailure(new StringTextComponent("controller_type was incorrect type"));
            return 1;
        }

        String message = "Controller" + controller.getName() + "'s look angle is " + lookangle;
        command.getSource().sendSuccess(new StringTextComponent(message), false);
        return 0;
    }
    private static int getpos(CommandContext<CommandSource> command, Set<PlayerEntity> player, Controller controller) {
        IVRPlayer vrplayer = VRPlugin.vrAPI.getVRPlayer(player.stream().findFirst().get());
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

        String message = "Controller" + controller.getName() + "'s position is " + position;
        command.getSource().sendSuccess(new StringTextComponent(message), false);
        return 0;
    }
}