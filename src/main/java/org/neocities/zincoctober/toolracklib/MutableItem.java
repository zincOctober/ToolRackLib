package org.neocities.zincoctober.toolracklib;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;


public class MutableItem extends Item {

    public MutableItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        MutItemPropertyDC properties = stack.get(TRLDataComponents.MUT_ITEM_PROPERTIES);
        if (state.is(properties.effectiveBlocks()))
            return properties.breakspeed();
        return 1.0F;
    }

}
