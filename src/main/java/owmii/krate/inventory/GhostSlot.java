package owmii.krate.inventory;

import owmii.lib.logistics.inventory.Inventory;
import owmii.lib.logistics.inventory.slot.SlotBase;

public class GhostSlot extends SlotBase {
    public GhostSlot(Inventory itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
}
