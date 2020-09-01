package owmii.krate.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import owmii.krate.block.KrateTile;
import owmii.krate.block.Tier;
import owmii.lib.logistics.inventory.AbstractTileContainer;
import owmii.lib.logistics.inventory.slot.SlotBase;

public class KrateContainer extends AbstractTileContainer<KrateTile> {
    private final boolean large;

    public KrateContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(Containers.KRATE, id, inventory, buffer);
        this.large = this.te.getVariant().equals(Tier.LARGE);
    }

    public KrateContainer(int id, PlayerInventory inventory, KrateTile te) {
        super(Containers.KRATE, id, inventory, te);
        this.large = this.te.getVariant().equals(Tier.LARGE);
    }

    public static KrateContainer create(int id, PlayerInventory inventory, PacketBuffer buffer) {
        return new KrateContainer(id, inventory, buffer);
    }

    @Override
    protected void init(PlayerInventory inventory, KrateTile te) {
        Tier e = this.te.getVariant();
        for (int i = 0; i < e.getColumns(); ++i) {
            for (int j = 0; j < e.getRows(); ++j) {
                addSlot(new SlotBase(te.getInventory(), j + i * e.getRows(), 8 + j * 18, i * 18 + 8));
            }
        }
        addSlot(new SlotBase(te.getInventory(), e.getInvSize(), -22, 8));
        for (int i = 2; i < 6; i++) {
            addSlot(new SlotBase(te.getInventory(), e.getInvSize() + i, -22, 35 + (i - 2) * 19));
        }
        int y1 = e.equals(Tier.SMALL) ? 41 : e.equals(Tier.BIG) || e.equals(Tier.LARGE) ? 167 : 95;
        addPlayerInventory(inventory, e.equals(Tier.LARGE) ? 89 : 8, y1, 4);
    }

    public boolean isLarge() {
        return this.large;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        boolean flag = false;
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            int vs = this.te.getVariant().getInvSize();
            for (int i = 0; i < this.te.exSlots; i++) {
                if (this.te.canInsert(vs + i, stack) && i != 1) {
                    flag = true;
                    break;
                }
            }
            int size = this.te.getInventory().getSlots();
            if (index < size) {
                if (!mergeItemStack(stack1, size, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(stack1, flag ? this.te.getVariant().getInvSize() : 0, size, false)) {
                return ItemStack.EMPTY;
            }
            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
