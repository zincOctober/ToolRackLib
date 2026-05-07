package org.neocities.zincoctober.toolracklib;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.commands.arguments.EntityArgument;

public class MutToolCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("mutConstuct")
                .requires(source -> source.hasPermission(2))
                .then(
                    Commands.argument("target", EntityArgument.player())
                    .executes(
                        source -> giveMutItem(source.getSource(), EntityArgument.getPlayer(source, "target"))
                    )
                )
        );
    }

    private static int giveMutItem(CommandSourceStack source, ServerPlayer player) {
        ItemStack stack = new ItemStack(toolracklib.MUTABLE_ITEM.asItem());
        stack.set(TRLDataComponents.MUT_ITEM_PROPERTIES, new MutItemPropertyDC());
        player.getInventory().add(stack);
        return 1;
    }
}
