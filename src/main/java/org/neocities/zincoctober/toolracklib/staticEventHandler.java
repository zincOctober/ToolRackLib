package org.neocities.zincoctober.toolracklib;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

//@EventBusSubscriber(modid = toolracklib.MODID)
public class staticEventHandler {

    //@SubscribeEvent // on the mod event bus
    public static void gatherData(GatherDataEvent event) {
        toolracklib.LOGGER.info("REGISTERING RECIPIES");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // other providers here
        generator.addProvider(
                event.includeServer(),
                new TRLRecipeProvider(output, lookupProvider)
        );
    }
}
