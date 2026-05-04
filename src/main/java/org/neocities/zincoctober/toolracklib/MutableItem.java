package org.neocities.zincoctober.toolracklib;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MutableItem extends Item {

    public MutableItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState block) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        if (block.is(properties.effectiveBlocks()))
            return properties.breakspeed();
        return 1.0F;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState block) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        if (block.is(properties.effectiveBlocks()) && !block.is(properties.tierTag()))
            return true;
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        return properties.durability() < properties.durabilityMax();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        return Mth.clamp(13*properties.durability()/properties.durabilityMax(), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        float hue = Math.max(0.0F, (float)properties.durability()/(float)properties.durabilityMax());
        return Mth.hsvToRgb(hue/3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState block, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide
            && block.getDestroySpeed(level, pos) != 0.0F
            && level instanceof ServerLevel serverLevel
            && entity instanceof ServerPlayer serverPlayer) {
                if (!serverPlayer.hasInfiniteMaterials()) {
                    int damage = EnchantmentHelper.processDurabilityChange(serverLevel, stack, 1);
                    if (damage <= 0) {
                    return true;
                    }

                    stack.update(TRLDataComponents.MUT_ITEM_PROPERTIES,
                        new MutItemPropertyDC(),
                        dc -> dc.blunt(damage)
                    );
                }
        }

        return true;
   }

}
