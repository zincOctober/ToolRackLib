package org.neocities.zincoctober.toolracklib.nomad;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.inventory.InventorySlots;
import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.UI;

class NomadBench extends Block implements BlockUIMenuType.BlockUI {

    public NomadBench(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<NomadBench> codec() {
        return Blocks.NOMAD_BENCH_CODEC.value();
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockUIMenuType.openUI((ServerPlayer) player, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(BlockUIMenuType.BlockUIHolder holder) {
        return ModularUI.of(UI.of(
            new UIElement().addChildren(
                new InventorySlots()
            )
        ), holder.player);
    }
}
