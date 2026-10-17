package org.neocities.zincoctober.toolracklib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.client.Minecraft;

import net.neoforged.neoforge.items.ItemStackHandler;

public class MutPartStackHandler<T> extends ItemStackHandler {
    public MutPartToolOutput output;
    public ArrayList<PartBranch> partTree = new ArrayList<PartBranch>(List.of(new PartBranch()));
    protected OnAdd<T> onAdd = null;
    protected OnRemove<T> onRemove = null;
    private RegistryAccess registryAccess;

    public MutPartStackHandler(Level level) {
        this.partTree.get(0).filter.add(toolracklib.INITIAL_PARTS);
        output = new MutPartToolOutput();
        if (level instanceof ServerLevel serverLevel) {
            this.registryAccess = serverLevel.registryAccess();
        } else {
            this.registryAccess = Minecraft.getInstance().getConnection().registryAccess();
        }

    }

    public MutPartStackHandler(OnAdd<T> onAdd, OnRemove<T> onRemove, Level level) {
        this.onAdd = onAdd;
        this.onRemove = onRemove;
        this.partTree.get(0).filter.add(toolracklib.INITIAL_PARTS);
        output = new MutPartToolOutput();
        if (level instanceof ServerLevel serverLevel) {
            this.registryAccess = serverLevel.registryAccess();
        } else {
            this.registryAccess = Minecraft.getInstance().getConnection().registryAccess();
        }
    }

    public void setInitSlot(T initUI) {
        this.partTree.get(0).owner = initUI;
    }

    @Override
    protected void onContentsChanged(int slot) {
        clean(slot);

        if (getStackInSlot(slot) != ItemStack.EMPTY) {

            ItemStack stack = getStackInSlot(slot);
            if (stack.getItem() instanceof MutItemPart part) {
                for (MutItemPart.Hardpoint hardpoint : part.hardpoints) {

                    //create new slots
                    int newSlot = findOpen();
                    partTree.get(slot).children.add(newSlot);

                    PartBranch newPartBranch = new PartBranch();
                    newPartBranch.filter.addAll(hardpoint.validForSlot());

                    if (newSlot == getSlots()) {
                        this.extend();
                        partTree.add(newPartBranch);
                    } else {
                        partTree.set(newSlot, newPartBranch);
                    }

                    //manage UI
                    if (onAdd != null) {
                        onAdd.accept(slot, newSlot, this, hardpoint.UIStyle());
                    }
                }
            }
        }

        if (this.getStackInSlot(0) != ItemStack.EMPTY) {
            output.setStackInSlot(0, MutableItem.fromParts(stacks, registryAccess));
        }
    }

    protected void extend() {
        ArrayList<ItemStack> stacks = new ArrayList<>(this.stacks);
        stacks.add(ItemStack.EMPTY);
        NonNullList<ItemStack> nonNullStacks = NonNullList.of(ItemStack.EMPTY, stacks.toArray(new ItemStack[0]));
        this.stacks = nonNullStacks;
    }

    protected void clean(int slot) {
        for (int childSlot : partTree.get(slot).children) {
            clean(childSlot);

            PartBranch childBranch = partTree.get(childSlot);

            //manage UI
            if (onRemove != null) {
                onRemove.accept(slot, this, childBranch.owner);
            }

            //clean up removed slots
            childBranch = new PartBranch();
        }
        partTree.get(slot).children = NonNullList.create();
    }

    protected int findOpen() {
        int i = 0;
        for (PartBranch partBranch : partTree) {
            if (partBranch.owner == null) {
                return i;
            }
            i++;
        }
        return i;
    }

    public interface OnAdd<T> {
        void accept(int slot, int newSlot, MutPartStackHandler<T> handler, String UIStyle);
    }

    public interface OnRemove<T> {
        void accept(int slot, MutPartStackHandler<T> handler, T UI);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (stack.getItem() instanceof MutItemPart) {
            for (TagKey<Item> filter : partTree.get(slot).filter) {
                if (stack.is(filter)) {
                    return true;
                }
            }
        }

        return false;
    }

    public class PartBranch {
        public NonNullList<Integer> children = NonNullList.create();
        public T owner = null;
        protected NonNullList<TagKey<Item>> filter = NonNullList.create();
    }

    public class MutPartToolOutput extends ItemStackHandler {

        public MutPartToolOutput() {
            super();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }
    }
}
