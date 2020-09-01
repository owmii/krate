package owmii.krate.block;

import net.minecraft.tileentity.TileEntityType;
import owmii.krate.Krate;
import owmii.lib.registry.Registry;

public class Tiles {
    @SuppressWarnings("unchecked")
    public static final Registry<TileEntityType<?>> REG = new Registry(TileEntityType.class, Krate.MOD_ID);
    public static final TileEntityType<KrateTile> KRATE_SMALL = REG.register("krate_small", () -> new KrateTile(Tier.SMALL), Blcks.KRATE_SMALL);
    public static final TileEntityType<KrateTile> KRATE_BASIC = REG.register("krate_basic", () -> new KrateTile(Tier.BASIC), Blcks.KRATE_BASIC);
    public static final TileEntityType<KrateTile> KRATE_BIG = REG.register("krate_big", () -> new KrateTile(Tier.BIG), Blcks.KRATE_BIG);
    public static final TileEntityType<KrateTile> KRATE_LARGE = REG.register("krate_large", () -> new KrateTile(Tier.LARGE), Blcks.KRATE_LARGE);
}
