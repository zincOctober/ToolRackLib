package org.neocities.zincoctober.toolracklib;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = toolracklib.MODID)
public class Materials {
    public static final ResourceKey<Registry<Material>> MATERIAL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(toolracklib.MODID, "materials"));

    @SubscribeEvent // on the mod event bus
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                MATERIAL_REGISTRY_KEY,
                Material.CODEC,
                Material.CODEC
        );
    }
}
