package org.neocities.zincoctober.toolracklib.nomad;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.items.ItemStackHandler;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ItemSlot;
import com.lowdragmc.lowdraglib2.gui.ui.elements.inventory.InventorySlots;
import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.style.StylesheetManager;

import org.neocities.zincoctober.toolracklib.MutItemPart;
import org.neocities.zincoctober.toolracklib.MutPartStackHandler;
import org.neocities.zincoctober.toolracklib.toolracklib;

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

        MutPartStackHandler<UIElement> handler = new MutPartStackHandler<UIElement>(this::onAdd, this::onRemove);
        UIElement initSlot = new UIElement().addClass("slot_mount").addChild(
            new ItemSlot().bind(handler, 0)
        );
        handler.setInitSlot(initSlot);

        ModularUI ui = ModularUI.of(UI.of(
            new UIElement().addChildren(
                new UIElement().addClass("work_row").addClass("panel_bg").addChildren(
                    initSlot.addClass("init_slot")
                ),
                new UIElement().addChildren(
                    new InventorySlots().addClass("panel_bg")
                ).addClass("row")
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
        newMount.addClass("mount");

        UIElement deadMount = new UIElement().addClass("slot_mount");

        mount.addChild(newContainer);
        if (precedes.contains(style)) {
            newContainer.addChildren(
                newMount,
                deadMount
            );
        } else {
            newContainer.addChildren(
                deadMount,
                newMount
            );
        }
    }

    protected void onRemove(int slot, MutPartStackHandler<UIElement> handler, UIElement owner) {
        owner.removeSelf();
    }
}
