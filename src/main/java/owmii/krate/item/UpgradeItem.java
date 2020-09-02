package owmii.krate.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import owmii.lib.item.ItemBase;

public class UpgradeItem extends ItemBase {
    public UpgradeItem(Properties properties) {
        super(properties);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
}
