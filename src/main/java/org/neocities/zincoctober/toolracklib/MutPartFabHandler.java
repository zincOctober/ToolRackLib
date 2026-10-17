package org.neocities.zincoctober.toolracklib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Holder;

import net.neoforged.neoforge.items.ItemStackHandler;

public class MutPartFabHandler extends ItemStackHandler {
    private Material currentMaterial;
    private RegistryAccess registryAccess;
    public Item part = null;
    public FabResult fabResult;

    public MutPartFabHandler(Level level) {
        super();
        if (level instanceof ServerLevel serverLevel) {
            this.registryAccess = serverLevel.registryAccess();
        } else {
            this.registryAccess = Minecraft.getInstance().getConnection().registryAccess();
        }
        fabResult = new FabResult();
    }

    @Override
    protected void onContentsChanged(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        for (Holder.Reference<Material> materialRef : registryAccess.lookupOrThrow(Materials.MATERIAL_REGISTRY_KEY).listElements().toList()) {
            Material material = materialRef.value();
            if (stack.is(material.component())) {
                currentMaterial = material;
                break;
            } else {
                currentMaterial = null;
            }
        }

        ItemStack newResult = ItemStack.EMPTY;

        toolracklib.LOGGER.debug("material: " + currentMaterial);
        if (currentMaterial != null && part != null) {
            newResult = new ItemStack(part);
            newResult.set(TRLDataComponents.MATERIAL, ResourceLocation.fromNamespaceAndPath("toolracklib", currentMaterial.name()));
        }
        toolracklib.LOGGER.debug("setting: " + newResult);
        fabResult.setStackInSlot(0, newResult);
    }

    public class FabResult extends ItemStackHandler {

        public FabResult() {
            super();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }


    }
}
