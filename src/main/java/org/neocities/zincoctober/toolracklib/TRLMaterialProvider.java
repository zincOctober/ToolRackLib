package org.neocities.zincoctober.toolracklib;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderOwner;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

public class TRLMaterialProvider extends JsonCodecProvider<Material> {
    private CompletableFuture<HolderLookup.Provider> lookupProvider;

    protected TRLMaterialProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, PackOutput.Target.DATA_PACK, "toolracklib/materials", PackType.SERVER_DATA, Material.CODEC, lookupProvider, toolracklib.MODID, existingFileHelper);
        this.lookupProvider = lookupProvider;
    }

    protected void gather() {
        //try {
            HolderSet<Item> holderSet = HolderSet.emptyNamed(BuiltInRegistries.ITEM.holderOwner(), ItemTags.PLANKS);
            unconditional(ResourceLocation.fromNamespaceAndPath("toolracklib", "planks"), new Material("planks", holderSet, 10, BlockTags.MINEABLE_WITH_PICKAXE, 5.0F, 1.0F, 0.0F));
        // } catch (InterruptedException e) {
        //
        // } catch (ExecutionException e) {
        //
        //}
    }
}
