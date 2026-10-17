package org.neocities.zincoctober.toolracklib.tools;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item.Properties;

import net.neoforged.neoforge.registries.DeferredItem;

import org.neocities.zincoctober.toolracklib.HeadPart;
import org.neocities.zincoctober.toolracklib.MutItemPart;
import org.neocities.zincoctober.toolracklib.toolracklib;


public class ItemParts {

    public static final DeferredItem<MutItemPart> PICKAXE_PART_ITEM = toolracklib.ITEMS.registerItem(
        "pickaxe_part",
        props -> new HeadPart(props,
            BlockTags.MINEABLE_WITH_PICKAXE, false
        )
    );

    // public static final Supplier<MutItemPart> PICKAXE_PART = toolracklib.ITEM_PARTS.register(
    //     "pickaxe_part",
    //     () -> new MutItemPart(new Properties(),
    //         List.of()
    //     )
    // );

}
