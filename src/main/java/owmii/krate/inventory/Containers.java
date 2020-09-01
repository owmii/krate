package owmii.krate.inventory;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import owmii.krate.Krate;
import owmii.lib.registry.Registry;

public class Containers {
    @SuppressWarnings("unchecked")
    public static final Registry<ContainerType<?>> REG = new Registry(ContainerType.class, Krate.MOD_ID);
    public static final ContainerType<KrateContainer> KRATE = REG.register("krate", IForgeContainerType.create(KrateContainer::create));
    public static final ContainerType<HopperSettingContainer> HOPPER_SET = REG.register("hopper_set", IForgeContainerType.create(HopperSettingContainer::create));
    public static final ContainerType<CollectSettingContainer> COLLECT_SET = REG.register("collect_set", IForgeContainerType.create(CollectSettingContainer::create));
    public static final ContainerType<FilterContainer> FILTER = REG.register("filter", IForgeContainerType.create(FilterContainer::create));
}
