package owmii.krate.client.render.tile;

import net.minecraft.block.Block;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import owmii.krate.block.Blcks;
import owmii.krate.block.Tiles;
import owmii.lib.client.renderer.item.TEItemRenderer;

public class TileRenderer {
    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Tiles.KRATE_SMALL, KrateRenderer::new);
        ClientRegistry.bindTileEntityRenderer(Tiles.KRATE_BASIC, KrateRenderer::new);
        ClientRegistry.bindTileEntityRenderer(Tiles.KRATE_BIG, KrateRenderer::new);
        ClientRegistry.bindTileEntityRenderer(Tiles.KRATE_LARGE, KrateRenderer::new);
        TEItemRenderer.register(Blcks.KRATE.getArr(Block[]::new));
    }
}
