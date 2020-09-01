package owmii.krate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import owmii.krate.block.Blcks;
import owmii.krate.block.Tiles;
import owmii.krate.client.Client;
import owmii.krate.config.Configs;
import owmii.krate.inventory.Containers;
import owmii.krate.item.Itms;
import owmii.krate.network.Packets;
import owmii.lib.api.IClient;
import owmii.lib.api.IMod;
import owmii.lib.network.Network;

import javax.annotation.Nullable;

@Mod(Krate.MOD_ID)
public class Krate implements IMod {
    public static final String MOD_ID = "krate";
    public static final Network NET = new Network(MOD_ID);

    public Krate() {
        Blcks.REG.init();
        Tiles.REG.init();
        Itms.REG.init();
        Containers.REG.init();

        loadListeners();
        Configs.register();
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {
        Packets.register();
    }

    @Nullable
    @Override
    public IClient getClient() {
        return Client.INSTANCE;
    }
}