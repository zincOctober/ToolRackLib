package org.neocities.zincoctober.toolracklib;

import java.util.List;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.component.Tool;

public class MutableItem extends Item {

    public MutableItem(Item.Properties properties) {
        super(properties.durability(10).component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(BlockTags.INCORRECT_FOR_WOODEN_TOOL), Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, 2.0F)), 1.0F, 1)));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.set(TRLDataComponents.MUT_ITEM_PROPERTIES, new MutItemPropertyDC());
        stack.set(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(BlockTags.INCORRECT_FOR_WOODEN_TOOL), Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, 5.0F)), 1.0F, 1));
    }

    // @Override
    // public ItemStack getDefaultInstance() {
    //     ItemStack stack = new ItemStack(this);
    //     MutItemPropertyDC props = new MutItemPropertyDC();
    //     stack.set(TRLDataComponents.MUT_ITEM_PROPERTIES, props);
    //     return stack;
    // }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState block) {
        if (!MutableItem.isBroken(stack))
            return super.getDestroySpeed(stack, block);
        return 1.0F;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState block) {
        if (!MutableItem.isBroken(stack))
            return super.isCorrectToolForDrops(stack, block);
        return false;
    }

    // @Override
    // public boolean isBarVisible(ItemStack stack) {
    //     MutItemPropertyDC properties = stack.getOrDefault(TRLDataComponents.MUT_ITEM_PROPERTIES, new MutItemPropertyDC());
    //     return properties.durability() < properties.durabilityMax();
    // }
    //
    // @Override
    // public int getBarWidth(ItemStack stack) {
    //     MutItemPropertyDC properties = stack.getOrDefault(TRLDataComponents.MUT_ITEM_PROPERTIES, new MutItemPropertyDC());
    //     return Mth.clamp(13*properties.durability()/properties.durabilityMax(), 0, 13);
    // }
    //
    // @Override
    // public int getBarColor(ItemStack stack) {
    //     MutItemPropertyDC properties = stack.getOrDefault(TRLDataComponents.MUT_ITEM_PROPERTIES, new MutItemPropertyDC());
    //     float hue = Math.max(0.0F, (float)properties.durability()/(float)properties.durabilityMax());
    //     return Mth.hsvToRgb(hue/3.0F, 1.0F, 1.0F);
    // }

    public static boolean isBroken(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();
        return maxDamage > 0 && stack.getDamageValue() >= maxDamage - 1;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState block, BlockPos pos, LivingEntity entity) {
        if (!MutableItem.isBroken(stack)) {
            int damage = Math.min(1, stack.getMaxDamage() - stack.getDamageValue());
            stack.hurtAndBreak(damage, entity, EquipmentSlot.MAINHAND);
        }

        return true;
   }

}
