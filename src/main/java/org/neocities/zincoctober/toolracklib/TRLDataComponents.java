package org.neocities.zincoctober.toolracklib;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record TRLDataComponents() {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, toolracklib.MODID);

    // public static final Supplier<DataComponentType<MutItemPropertyDC>> MUT_ITEM_PROPERTIES = REGISTRAR.registerComponentType(
    //     "mutable_item_properties",
    //     builder -> builder
    //         .persistent(MutItemPropertyDC.CODEC)
    //         .networkSynchronized(MutItemPropertyDC.STREAM_CODEC)
    // );

    public static final Supplier<DataComponentType<ResourceLocation>> MATERIAL = REGISTRAR.registerComponentType(
        "part_material",
        builder -> builder
             .persistent(ResourceLocation.CODEC)
             .networkSynchronized(ResourceLocation.STREAM_CODEC)
     );
}
