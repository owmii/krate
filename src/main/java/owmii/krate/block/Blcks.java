package owmii.krate.block;

import net.minecraft.block.Block;
import owmii.krate.Krate;
import owmii.lib.block.Properties;
import owmii.lib.registry.Registry;

public class Blcks {
    public static final Registry<Block> REG = new Registry<>(Block.class, Krate.MOD_ID);
    public static final Block KRATE_SMALL = REG.register("krate_small", new KrateBlock(Properties.woodNoSolid(1.0F, 10.0F), Tier.SMALL));
    public static final Block KRATE_BASIC = REG.register("krate_basic", new KrateBlock(Properties.woodNoSolid(1.0F, 10.0F), Tier.BASIC));
    public static final Block KRATE_BIG = REG.register("krate_big", new KrateBlock(Properties.woodNoSolid(1.0F, 10.0F), Tier.BIG));
    public static final Block KRATE_LARGE = REG.register("krate_large", new KrateBlock(Properties.woodNoSolid(1.0F, 10.0F), Tier.LARGE));
}
