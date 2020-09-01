package owmii.krate.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import owmii.krate.block.KrateTile;
import owmii.lib.logistics.inventory.AbstractTileContainer;
import owmii.lib.logistics.inventory.slot.SlotBase;

public class HopperSettingContainer extends AbstractTileContainer<KrateTile> {
    private final Direction side;

    public HopperSettingContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(Containers.HOPPER_SET, id, inventory, buffer);
        this.side = Direction.byIndex(buffer.readInt());
    }

    public HopperSettingContainer(int id, PlayerInventory inventory, KrateTile te) {
        super(Containers.HOPPER_SET, id, inventory, te);
        this.side = Direction.NORTH;
    }

    public static HopperSettingContainer create(int id, PlayerInventory inventory, PacketBuffer buffer) {
        return new HopperSettingContainer(id, inventory, buffer);
    }

    @Override
    protected void init(PlayerInventory inventory, KrateTile te) {
        int i = this.te.getVariant().getInvSize();
        addSlot(new SlotBase(te.getInventory(), i, 111, 57));
        addSlot(new SlotBase(te.getInventory(), i + 1, 49, 57));
        addPlayerInventory(inventory, 8, 97, 4);
    }

    public Direction getSide() {
        return this.side;
    }
}
