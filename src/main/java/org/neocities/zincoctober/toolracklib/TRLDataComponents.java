package org.neocities.zincoctober.toolracklib;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record TRLDataComponents() {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, toolracklib.MODID);

    public static final Supplier<DataComponentType<MutItemPropertyDC>> MUT_ITEM_PROPERTIES = REGISTRAR.registerComponentType(
        "mutable_item_properties",
        builder -> builder
            .persistent(MutItemPropertyDC.CODEC)
            .networkSynchronized(MutItemPropertyDC.STREAM_CODEC)
    );
}
