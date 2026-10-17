package org.neocities.zincoctober.toolracklib;

import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.core.RegistryAccess;

public class MutItemPart extends Item {
    public final List<Hardpoint> hardpoints;

    public MutItemPart(Item.Properties properties, List<Hardpoint> hardpoints) {
        super(properties);
        this.hardpoints = hardpoints;
    }

    public ItemStack fabricate(ItemStack stack, ResourceLocation material) {
        stack.set(TRLDataComponents.MATERIAL, material);
        return stack;
    }

    public ItemBuilder assemble(ItemBuilder builder, RegistryAccess registryAccess, ItemStack stack) {
        return builder;
    }

    public record Hardpoint(
        List<TagKey<Item>> validForSlot,
        String UIStyle
    ) {}

    public class ItemBuilder {
        public MutableItem Finish() {
            return new MutableItem(new Item.Properties());
        }
    }
}
