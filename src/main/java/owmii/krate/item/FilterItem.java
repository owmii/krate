package owmii.krate.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import owmii.krate.inventory.Containers;
import owmii.krate.inventory.FilterContainer;
import owmii.lib.item.ItemBase;
import owmii.lib.logistics.Filter;
import owmii.lib.logistics.inventory.ItemInventory;

public class FilterItem extends ItemBase {
    public FilterItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!world.isRemote && player instanceof ServerPlayerEntity && hand.equals(Hand.MAIN_HAND)) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                    return new FilterContainer(Containers.FILTER, id, playerInventory, getInventory(player));
                }

                @Override
                public ITextComponent getDisplayName() {
                    return stack.getDisplayName();
                }
            });
        }
        return ActionResult.resultSuccess(stack);
    }

    public static ItemInventory getInventory(PlayerEntity player) {
        return new ItemInventory(18, player.getHeldItemMainhand());
    }

    public Filter getFilter(ItemStack stack) {
        return new Filter(stack, 18);
    }
}
