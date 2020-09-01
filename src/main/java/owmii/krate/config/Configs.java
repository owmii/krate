package owmii.krate.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import owmii.krate.Krate;
import owmii.lib.config.Config;

public class Configs {
    public static final GeneralConfig GENERAL;
    private static final ForgeConfigSpec GENERAL_SPEC;

    public static void register() {
        final String path = Config.createConfigDir(Krate.MOD_ID);
        Config.registerCommon(GENERAL_SPEC, Krate.MOD_ID + "/general_common.toml");
    }

    static {
        final Pair<GeneralConfig, ForgeConfigSpec> generalPair = Config.get(GeneralConfig::new);
        GENERAL = generalPair.getLeft();
        GENERAL_SPEC = generalPair.getRight();
    }
}
