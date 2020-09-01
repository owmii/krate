package owmii.krate.inventory;

import net.minecraftforge.items.IItemHandler;
import owmii.lib.logistics.inventory.slot.SlotBase;

public class GhostSlot extends SlotBase {
    public GhostSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
}
