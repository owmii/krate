package owmii.krate.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import owmii.krate.block.KrateTile;
import owmii.krate.block.Tier;
import owmii.lib.item.ItemBase;
import owmii.lib.logistics.inventory.Inventory;

public class KrateUpgradeItem extends ItemBase {
    private final Tier tier;

    public KrateUpgradeItem(Properties properties, Tier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, Vector3d hit) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof KrateTile) {
            KrateTile krate = (KrateTile) tile;
            Tier tier = krate.getVariant();
            if (this.tier.ordinal() - 1 == tier.ordinal()) {
                if (!world.isRemote) {
                    Inventory inv = new Inventory(krate.getInventory().getSlots());
                    inv.deserializeNBT(krate.getInventory().serializeNBT());
                    krate.getInventory().clear();
                    world.setBlockState(pos, this.tier.getBlock().getDefaultState().with(BlockStateProperties.WATERLOGGED, false), 3);
                    TileEntity tile2 = world.getTileEntity(pos);
                    if (tile2 instanceof KrateTile) {
                        KrateTile krate2 = (KrateTile) tile2;
                        Inventory inv2 = krate2.getInventory();
                        for (int i = 0; i < tier.getInvSize(); i++) {
                            inv2.setStack(i, inv.getStackInSlot(i));
                        }
                        int j = 0;
                        for (int i = tier.getInvSize(); i < inv.getSlots(); i++) {
                            inv2.setStack(j + this.tier.getInvSize(), inv.getStackInSlot(i));
                            j++;
                        }
                        krate2.copy(krate);
                        if (!player.isCreative()) {
                            player.getHeldItem(hand).shrink(1);
                        }
                    }
                }
                return ActionResultType.SUCCESS;
            } else {
                player.sendStatusMessage(new TranslationTextComponent("chat.krate.upgrade.not.applicable").mergeStyle(TextFormatting.RED)
                        .append(new ItemStack(Tier.values()[this.tier.ordinal() - 1].getBlock()).getDisplayName().copyRaw().mergeStyle(TextFormatting.GRAY)), true);
                return ActionResultType.FAIL;
            }
        }
        return super.onItemUseFirst(stack, world, pos, player, hand, side, hit);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
}
