package org.neocities.zincoctober.toolracklib;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class MutItemPart extends Item {
    public static final Material defaultMaterial = new Material(BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.STONE_TOOL_MATERIALS), 10, BlockTags.INCORRECT_FOR_WOODEN_TOOL, 5.0F, 1.0F, 0.0F, false);

    public MutItemPart(Item.Properties properties) {
        super(properties);
    }

    private Material getMaterial() {
        return defaultMaterial;
    }

    public ItemStack fabricate(ItemStack stack, ResourceLocation material) {
        stack.set(TRLDataComponents.MATERIAL, material);
        return stack;
    }
}
