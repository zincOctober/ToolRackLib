package org.neocities.zincoctober.toolracklib.nomad;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import org.neocities.zincoctober.toolracklib.toolracklib;

public class Blocks {

    public static final DeferredBlock<Block> NOMAD_BENCH = toolracklib.BLOCKS.registerBlock(
        "nomad_bench",
        NomadBench::new, // The factory that the properties will be passed into.
        BlockBehaviour.Properties.of() // The properties to use.
    );

    public static final Supplier<BlockItem> EXAMPLE_BLOCK_ITEM = toolracklib.ITEMS.registerSimpleBlockItem(
        NOMAD_BENCH
    );

    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<NomadBench>> NOMAD_BENCH_CODEC = toolracklib.TYPE_REGISTRAR.register(
        "simple",
        () -> BlockBehaviour.simpleCodec(NomadBench::new)
    );
}
