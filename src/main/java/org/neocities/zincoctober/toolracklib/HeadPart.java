package org.neocities.zincoctober.toolracklib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.neocities.zincoctober.toolracklib.HandlePart.HandleBuilder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;

public class HeadPart extends MutItemPart {
    TagKey<Block> toolFor = BlockTags.AIR;
    boolean isWeapon = false;

    public HeadPart(Item.Properties properties, TagKey<Block> toolFor, boolean weapon) {
        super(properties, List.of());
    }

    @Override
    public ItemStack fabricate(ItemStack stack, ResourceLocation material) {
        stack.set(TRLDataComponents.MATERIAL, material);
        return stack;
    }

    @Override
    public ItemBuilder assemble(ItemBuilder builder, RegistryAccess registryAccess, ItemStack stack) {
        if (builder instanceof HandlePart.HandleBuilder handleBuilder) {
            
            Optional<Registry<Material>> registerOpt = registryAccess.registry(Materials.MATERIAL_REGISTRY_KEY);
            if (registerOpt.isPresent()) {
                Registry<Material> register = registerOpt.get();
                
                Material material = register.get(stack.get(TRLDataComponents.MATERIAL));
                HandlePart.HandleBuilder.HandleHead handleHead = handleBuilder.new HandleHead();
                handleHead.attackDamage = material.baseSwingDamage();
                handleHead.attackSpeed = material.baseSwingSpeed();
                handleHead.miningSpeed = material.baseBreakSpeed();
                handleHead.incorrectTierFor = material.incorrectFor();
                handleHead.weapon = isWeapon;
                handleHead.toolFor = toolFor;

                handleBuilder.heads.add(handleHead);

                handleBuilder.durabilityFlat = material.baseDurability();
            }
        }        

        return builder;
    }
}
