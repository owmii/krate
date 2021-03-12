package owmii.krate.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import owmii.krate.item.FilterItem;
import owmii.lib.logistics.inventory.AbstractContainer;
import owmii.lib.logistics.inventory.ItemInventory;

import javax.annotation.Nullable;

public class FilterContainer extends AbstractContainer {
    private final ItemInventory inventory;
    private final int slots;

    public FilterContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, ItemInventory inventory) {
        super(type, id, playerInventory);
        this.inventory = inventory;
        this.slots = inventory.getSlots();

        for (int l = 0; l < 2; ++l) {
            for (int k = 0; k < 9; ++k) {
                addSlot(new GhostSlot(this.inventory, k + l * 9, 8 + k * 18, l * 18 + 18));
            }
        }
        addPlayerInventory(playerInventory, 8, 69, 4);
    }

    public FilterContainer(@Nullable ContainerType<?> type, int i, PlayerInventory playerInventory, PacketBuffer buffer, int slots) {
        this(type, i, playerInventory, new ItemInventory(slots, playerInventory.player.getHeldItemMainhand()));
    }

    public static FilterContainer create(final int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        return new FilterContainer(Containers.FILTER, i, playerInventory, buffer, 18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return player.getHeldItemMainhand().getItem() instanceof FilterItem;
    }

    @Override
    public ItemStack slotClick(int i, int dragType, ClickType clickType, PlayerEntity player) {
        if (i < 0) return ItemStack.EMPTY;
        Slot slot = getSlot(i);
        ItemStack stack = slot.getStack();
        PlayerInventory inventory = player.inventory;
        if (slot instanceof GhostSlot) {
            if (stack.isEmpty()) {
                ItemStack copy = inventory.getItemStack().copy();
                copy.setCount(1);
                this.inventory.setStackInSlot(i, copy);
                return ItemStack.EMPTY;
            } else {
                this.inventory.setStackInSlot(i, ItemStack.EMPTY);
                return ItemStack.EMPTY;
            }
        }
        if (stack.getItem() instanceof FilterItem && (i - this.inventory.getSlots() - 27) == inventory.currentItem)
            return ItemStack.EMPTY;
        if (clickType == ClickType.SWAP)
            return ItemStack.EMPTY;
        return super.slotClick(i, dragType, clickType, player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        if (!this.inventory.hasEmptySlot()) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            ItemStack copy = stack1.copy();
            copy.setCount(1);
            for (int i = 0; i < this.inventory.getSlots(); i++) {
                if (this.inventory.getStackInSlot(i).isEmpty()) {
                    this.inventory.setStackInSlot(i, copy);
                    return ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }
}
