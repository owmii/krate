package owmii.krate.client.screen.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import owmii.krate.Krate;
import owmii.krate.block.KrateTile;
import owmii.krate.network.packet.ReqKrateScreenPacket;
import owmii.lib.client.screen.AbstractTileScreen;
import owmii.lib.client.screen.Texture;
import owmii.lib.logistics.inventory.AbstractTileContainer;

public class KrateSettingScreen<C extends AbstractTileContainer<KrateTile>> extends AbstractTileScreen<KrateTile, C> {
    public KrateSettingScreen(C container, PlayerInventory inv, ITextComponent title, Texture backGround) {
        super(container, inv, title, backGround);
    }

    @Override
    public void closeScreen() {
        Krate.NET.toServer(new ReqKrateScreenPacket(this.te.getPos()));
    }

    @Override
    protected boolean hasRedstone() {
        return false;
    }
}
