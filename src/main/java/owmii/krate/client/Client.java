package owmii.krate.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import owmii.krate.client.render.tile.TileRenderer;
import owmii.krate.client.screen.Screens;
import owmii.lib.api.IClient;

public enum Client implements IClient {
    INSTANCE;

    @Override
    public void client(FMLClientSetupEvent event) {
        TileRenderer.register();
        Screens.register();
    }
}
