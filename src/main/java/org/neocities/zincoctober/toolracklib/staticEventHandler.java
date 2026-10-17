package org.neocities.zincoctober.toolracklib;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;

import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = toolracklib.MODID)
public class staticEventHandler {

    @SubscribeEvent // on the mod event bus
    public static void gatherData(GatherDataEvent event) {
        toolracklib.LOGGER.info("REGISTERING RECIPIES");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        //ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        event.createDatapackRegistryObjects(
            new RegistrySetBuilder()
                .add(Materials.MATERIAL_REGISTRY_KEY, bootstrap -> {
                    bootstrap.register(
                        ResourceKey.create(
                            Materials.MATERIAL_REGISTRY_KEY,
                            ResourceLocation.fromNamespaceAndPath(toolracklib.MODID, "planks")
                        ),
                        new Material(
                            "planks", bootstrap.lookup(Registries.ITEM).getOrThrow(ItemTags.PLANKS), 10, BlockTags.MINEABLE_WITH_PICKAXE, 5.0F, 1.0F, 0.0F
                        )
                    );
                })
        );

        //other providers here
        generator.addProvider(
                event.includeServer(),
                new TRLRecipeProvider(output, lookupProvider)
        );
        // generator.addProvider(
        //     event.includeServer(),
        //     new TRLMaterialProvider(output, lookupProvider, existingFileHelper)
        // );
    }

    // @SubscribeEvent
    // public static void registerRegistries(NewRegistryEvent event) {
    //     event.register(toolracklib.ITEM_PARTS_REGISTRY);
    // }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        MutToolCommand.register(event.getDispatcher());
    }
}
