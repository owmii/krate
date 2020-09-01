package owmii.krate.handler;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import owmii.lib.logistics.inventory.AbstractContainer;
import owmii.lib.util.Server;

import java.util.*;

public class CompactingHandler {
    private static final Map<IItemProvider, ItemStack> CACHE_2X2 = new HashMap<>();
    private static final Map<IItemProvider, ItemStack> CACHE_3X3 = new HashMap<>();
    private static final Set<IItemProvider> CACHE_EMPTY_2X2 = new HashSet<>();
    private static final Set<IItemProvider> CACHE_EMPTY_3X3 = new HashSet<>();

    public static ItemStack get(World world, ItemStack stack, boolean small) {
        if (!stack.isEmpty()) {
            if (small) {
                if (CACHE_2X2.containsKey(stack.getItem())) {
                    return CACHE_2X2.get(stack.getItem());
                } else if (CACHE_EMPTY_2X2.contains(stack.getItem())) {
                    return ItemStack.EMPTY;
                }
                ItemStack resStack = result(world, stack, true);
                if (resStack.isEmpty()) {
                    CACHE_EMPTY_2X2.add(stack.getItem());
                    return ItemStack.EMPTY;
                } else {
                    CACHE_2X2.put(stack.getItem(), resStack.copy());
                    return resStack.copy();
                }
            } else {
                if (CACHE_3X3.containsKey(stack.getItem())) {
                    return CACHE_3X3.get(stack.getItem());
                } else if (CACHE_EMPTY_3X3.contains(stack.getItem())) {
                    return ItemStack.EMPTY;
                }
                ItemStack resStack = result(world, stack, false);
                if (resStack.isEmpty()) {
                    CACHE_EMPTY_3X3.add(stack.getItem());
                    return ItemStack.EMPTY;
                } else {
                    CACHE_3X3.put(stack.getItem(), resStack.copy());
                    return resStack.copy();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    static ItemStack result(World world, ItemStack stack, boolean small) {
        if (!world.isRemote) {
            CraftingInventory inventory = new CraftingInventory(AbstractContainer.DUMMY, 3, 3);
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                if (small && (i == 2 || i > 4)) continue;
                inventory.setInventorySlotContents(i, stack.copy());
            }
            Optional<ICraftingRecipe> result = Server.get().getRecipeManager().getRecipe(IRecipeType.CRAFTING, inventory, world);
            if (result.isPresent()) {
                return result.get().getRecipeOutput();
            }
        }
        return ItemStack.EMPTY;
    }

    public static void clearCache() {
        CACHE_2X2.clear();
        CACHE_3X3.clear();
        CACHE_EMPTY_2X2.clear();
        CACHE_EMPTY_3X3.clear();
    }
}
