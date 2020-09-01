package owmii.krate.client.screen;

import net.minecraft.client.gui.ScreenManager;
import owmii.krate.client.screen.inventory.CollectSettingScreen;
import owmii.krate.client.screen.inventory.FilterScreen;
import owmii.krate.client.screen.inventory.HopperSettingScreen;
import owmii.krate.client.screen.inventory.KrateScreen;
import owmii.krate.inventory.Containers;

public class Screens {
    public static void register() {
        ScreenManager.registerFactory(Containers.KRATE, KrateScreen::new);
        ScreenManager.registerFactory(Containers.HOPPER_SET, HopperSettingScreen::new);
        ScreenManager.registerFactory(Containers.COLLECT_SET, CollectSettingScreen::new);
        ScreenManager.registerFactory(Containers.FILTER, FilterScreen::new);
    }
}
