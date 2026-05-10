package org.neocities.zincoctober.toolracklib;

import java.util.List;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.item.component.Tool;

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
        stack.set(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(BlockTags.INCORRECT_FOR_WOODEN_TOOL), Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, 5.0F)), 1.0F, 1));
        player.getInventory().add(stack);
        return 1;
    }
}
