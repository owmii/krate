package owmii.krate.item;

import net.minecraft.item.Item;
import owmii.krate.Krate;
import owmii.krate.block.Blcks;
import owmii.lib.registry.Registry;

public class Itms {
    public static final Registry<Item> REG = new Registry<>(Item.class, Blcks.REG.getBlockItems(ItemGroups.MAIN), Krate.MOD_ID);
    public static final Item FILTER = REG.register("filter", new FilterItem(new Item.Properties().group(ItemGroups.MAIN).maxStackSize(1)));
    public static final Item EMPTY_UPGRADE = REG.register("empty_upgrade", new UpgradeItem(new Item.Properties().group(ItemGroups.MAIN)));
    public static final Item HOPPER_UPGRADE = REG.register("hopper_upgrade", new UpgradeItem(new Item.Properties().group(ItemGroups.MAIN).maxStackSize(1)));
    public static final Item COLLECT_UPGRADE = REG.register("collect_upgrade", new UpgradeItem(new Item.Properties().group(ItemGroups.MAIN).maxStackSize(1)));
    public static final Item SHULKER_UPGRADE = REG.register("shulker_upgrade", new UpgradeItem(new Item.Properties().group(ItemGroups.MAIN).maxStackSize(1)));
    public static final Item COMPACT_UPGRADE = REG.register("compacting_upgrade", new UpgradeItem(new Item.Properties().group(ItemGroups.MAIN).maxStackSize(1)));
}
