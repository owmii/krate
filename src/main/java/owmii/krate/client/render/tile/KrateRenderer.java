package owmii.krate.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.AxisAlignedBB;
import owmii.krate.block.KrateTile;
import owmii.krate.client.model.KrateModel;
import owmii.lib.client.renderer.tile.AbstractTileRenderer;

public class KrateRenderer extends AbstractTileRenderer<KrateTile> {
    public static final KrateModel MODEL = new KrateModel(RenderType::getEntitySolid);

    public KrateRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(KrateTile te, float pt, MatrixStack matrix, IRenderTypeBuffer rtb, Minecraft mc, ClientWorld world, ClientPlayerEntity player, int light, int ov) {
        matrix.push();
        matrix.translate(0.5, 1.5, 0.5);
        matrix.scale(1.0f, -1.0f, -1.0f);
        MODEL.render(te, this, matrix, rtb, light, ov);
        matrix.pop();

        matrix.push();
        if (te.visualize) {
            IVertexBuilder ivertexbuilder = rtb.getBuffer(RenderType.getLines());
            AxisAlignedBB box = te.getBox().geAxis();
            WorldRenderer.drawBoundingBox(matrix, ivertexbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrix.pop();


    }

    @Override
    public boolean isGlobalRenderer(KrateTile te) {
        return te.visualize;
    }
}
