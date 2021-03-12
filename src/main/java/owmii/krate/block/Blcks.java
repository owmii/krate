package owmii.krate.block;

import net.minecraft.block.Block;
import owmii.krate.Krate;
import owmii.lib.block.Properties;
import owmii.lib.registry.Registry;
import owmii.lib.registry.VarReg;

public class Blcks {
    public static final Registry<Block> REG = new Registry<>(Block.class, Krate.MOD_ID);
    public static final VarReg<Tier, Block> KRATE = REG.getVar("krate", variant -> new KrateBlock(Properties.woodNoSolid(1.0F, 10.0F), variant), Tier.values());
}
