package owmii.krate.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import owmii.krate.block.KrateTile;
import owmii.lib.logistics.inventory.AbstractTileContainer;

public class CollectSettingContainer extends AbstractTileContainer<KrateTile> {

    public CollectSettingContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(Containers.COLLECT_SET, id, inventory, buffer);
    }

    public CollectSettingContainer(int id, PlayerInventory inventory, KrateTile te) {
        super(Containers.COLLECT_SET, id, inventory, te);
    }

    public static CollectSettingContainer create(int id, PlayerInventory inventory, PacketBuffer buffer) {
        return new CollectSettingContainer(id, inventory, buffer);
    }

    @Override
    protected void init(PlayerInventory inventory, KrateTile te) {
        addPlayerInventory(inventory, 8, 62, 4);
    }
}
