package org.neocities.zincoctober.toolracklib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet;

public class HandlePart extends MutItemPart {

    public HandlePart(Item.Properties properties, List<Hardpoint> hardpoints) {
        super(properties, hardpoints);
    }

    @Override
    public ItemStack fabricate(ItemStack stack, ResourceLocation material) {
        stack.set(TRLDataComponents.MATERIAL, material);
        return stack;
    }

    @Override
    public ItemBuilder assemble(ItemBuilder builder, RegistryAccess registryAccess, ItemStack stack) {
        

        builder = new HandleBuilder();
        if (builder instanceof HandleBuilder handleBuilder) {
            
            Optional<Registry<Material>> registerOpt = registryAccess.registry(Materials.MATERIAL_REGISTRY_KEY);
            if (registerOpt.isPresent()) {
                Registry<Material> register = registerOpt.get();
                
                Material material = register.get(stack.get(TRLDataComponents.MATERIAL));
                handleBuilder.durabilityFlat = (int)0.2*material.baseDurability();
                
            }
        }        

        return builder;
    }

    public class HandleBuilder extends ItemBuilder {
        public ArrayList<HandleHead> heads = new ArrayList<HandleHead>();
        public float miningSpeedMult = 0;
        public float durabilityMult = 0;
        public float damageMult = 0;
        public float attackSpeedMult = 0;
        public int durabilityFlat = 0;

        public class HandleHead {
            public TagKey<Block> incorrectTierFor;
            public TagKey<Block> toolFor;

            public boolean weapon = false;
            public float attackDamage = 0;
            public float attackSpeed = 0;
            public float miningSpeed = 0;
        }

        @Override
        public MutableItem Finish() {
            Item.Properties properties = new Item.Properties();
            
            properties.durability((int)(durabilityFlat * durabilityMult));

            ArrayList<Tool.Rule> rules = new ArrayList<Tool.Rule>();
            for (HandleBuilder.HandleHead head : this.heads) {
                rules.add(Tool.Rule.minesAndDrops(head.toolFor, head.miningSpeed));
                rules.add(new Tool.Rule(
                      HolderSet.direct(BuiltInRegistries.BLOCK.getOrCreateTag(head.incorrectTierFor)
                      .stream().filter((b -> b.is(head.incorrectTierFor))).toList())
                      , Optional.empty(), Optional.of(false)));
            }
            properties.component(DataComponents.TOOL, new Tool(
                  rules, 1.0F, 1));

            return new MutableItem(properties);
            
        }
    }
}
