package org.neocities.zincoctober.toolracklib.nomad;

import java.util.List;

import com.mojang.serialization.MapCodec;

import dev.vfyjxf.taffy.style.FlexDirection;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;

import net.neoforged.neoforge.items.ItemStackHandler;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ItemSlot;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ScrollerView;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Button;
import com.lowdragmc.lowdraglib2.gui.ui.elements.inventory.InventorySlots;
import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.style.StylesheetManager;

import org.neocities.zincoctober.toolracklib.MutItemPart;
import org.neocities.zincoctober.toolracklib.MutPartStackHandler;
import org.neocities.zincoctober.toolracklib.toolracklib;
import org.neocities.zincoctober.toolracklib.MutPartFabHandler;

class NomadBench extends Block implements BlockUIMenuType.BlockUI {
    protected static final List<String> precedes = List.of("above", "left");

    public NomadBench(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<NomadBench> codec() {
        return Blocks.NOMAD_BENCH_CODEC.value();
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockUIMenuType.openUI((ServerPlayer) player, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(BlockUIMenuType.BlockUIHolder holder) {

        var nomadss = StylesheetManager.INSTANCE.getStylesheetSafe(
            ResourceLocation.fromNamespaceAndPath(toolracklib.MODID, "lss/nomad.lss")
        );
        var mcss = StylesheetManager.INSTANCE.getStylesheetSafe(StylesheetManager.MC);

        MutPartStackHandler<UIElement> handler = new MutPartStackHandler<UIElement>(this::onAdd, this::onRemove, holder.player.level());
        UIElement initSlot = new UIElement().addClass("slot_mount").addChild(
            new ItemSlot().bind(handler, 0)
        );
        handler.setInitSlot(initSlot);

        MutPartFabHandler fabHandler = new MutPartFabHandler(holder.player.level());

        ScrollerView partScroller = new ScrollerView();
        partScroller.addClass("part_scroller");
        partScroller.viewContainer(container -> container.addClass("part_scroller_view"));
        for (Holder<Item> part : BuiltInRegistries.ITEM.getOrCreateTag(toolracklib.MUT_PARTS)) {
            String text = part.getRegisteredName();
            Button button = new Button();
            button.setText(text, false);
            button.addClass("part_button");
            button.setOnServerClick(e -> {fabHandler.part = part.value();});

            partScroller.addScrollViewChild(button);
        }

        ModularUI ui = ModularUI.of(UI.of(
            new UIElement().layout(layout -> layout.flexDirection(FlexDirection.ROW)).addChildren(
                new UIElement().addChildren(
                    new UIElement().addClass("work_row").addClass("panel_bg").addChildren(
                        initSlot.addClass("init_slot")
                    ),
                    new UIElement().addChildren(
                        new InventorySlots().addClass("panel_bg")
                    ).addClass("row")
                ),
                new UIElement().addClass("panel_bg").addClass("column").addChildren(
                    new UIElement().addClass("part_bench").addChild(
                        new UIElement().layout(layout -> layout.flexDirection(FlexDirection.ROW)).addChildren(
                            new ItemSlot().bind(fabHandler, 0),
                            new UIElement().addClass("part_bench_arrow"),
                            new ItemSlot().bind(fabHandler.fabResult, 0)
                        )
                    ),
                    new UIElement().addClass("row").addChild(
                        partScroller
                    )
                )
            ),
            nomadss,
            mcss
        ), holder.player);
        return ui;
    }

    protected void onAdd(int slot, int newSlot, MutPartStackHandler<UIElement> handler, String style) {

        UIElement mount = handler.partTree.get(slot).owner;

        UIElement newContainer = new UIElement();
        newContainer.addClass(style + "_container");

        UIElement newMount = new UIElement().addClass("slot_mount").addChild(
            new ItemSlot().bind(handler, newSlot)
        );
        handler.partTree.get(newSlot).owner = newMount;

        mount.addChild(newContainer);
        newContainer.addChildren(
            newMount
        );
    }

    protected void onRemove(int slot, MutPartStackHandler<UIElement> handler, UIElement owner) {
        if (slot == 0) {
            owner.removeSelf();
        } else {
            owner.getParent().removeSelf();
        }
    }
}
