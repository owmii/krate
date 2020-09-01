package owmii.krate.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.krate.Krate;
import owmii.krate.block.Blcks;

public class ItemGroups {
    public static final ItemGroup MAIN = new ItemGroup(Krate.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Blcks.KRATE_SMALL);
        }
    };
}
