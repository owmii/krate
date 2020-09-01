package owmii.krate.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.krate.client.render.tile.KrateRenderer;
import owmii.krate.inventory.KrateContainer;
import owmii.lib.block.AbstractBlock;
import owmii.lib.block.AbstractTileEntity;
import owmii.lib.logistics.inventory.AbstractContainer;
import owmii.lib.logistics.inventory.Inventory;

import javax.annotation.Nullable;

public class KrateBlock extends AbstractBlock<Tier> implements IWaterLoggable {
    private static final VoxelShape TOP = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape MID = Block.makeCuboidShape(0.5D, 0.5D, 0.5D, 15.5D, 15.5D, 15.5D);
    private static final VoxelShape BOTTOM = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(TOP, MID, BOTTOM);

    public KrateBlock(Properties properties, Tier variant) {
        super(properties, variant);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new KrateTile(this.variant);
    }

    @Nullable
    @Override
    public <T extends AbstractTileEntity> AbstractContainer getContainer(int id, PlayerInventory inventory, AbstractTileEntity te, BlockRayTraceResult result) {
        if (te instanceof KrateTile) {
            return new KrateContainer(id, inventory, (KrateTile) te);
        }
        return null;
    }

    @Override
    protected Facing getFacing() {
        return Facing.HORIZONTAL;
    }

    @Override
    protected boolean isPlacerFacing() {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof KrateTile) {
            return Inventory.calcRedstone(((KrateTile) tile).getInventory());
        }
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderByItem(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer rtb, int light, int ov) {
        matrix.push();
        matrix.translate(0.5, 1.5, 0.5);
        matrix.scale(1.0f, -1.0f, -1.0f);
        KrateRenderer.MODEL.render(new KrateTile(getVariant()), new KrateRenderer(TileEntityRendererDispatcher.instance), matrix, rtb, light, ov);
        matrix.pop();
    }
}
