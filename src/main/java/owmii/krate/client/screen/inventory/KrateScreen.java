package owmii.krate.client.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import owmii.krate.Krate;
import owmii.krate.block.KrateTile;
import owmii.krate.client.screen.Textures;
import owmii.krate.inventory.KrateContainer;
import owmii.krate.network.packet.CompactingPacket;
import owmii.krate.network.packet.ReqCollectSettingPacket;
import owmii.krate.network.packet.ReqHopperSettingPacket;
import owmii.lib.client.screen.container.AbstractTileScreen;
import owmii.lib.client.screen.widget.IconButton;

public class KrateScreen extends AbstractTileScreen<KrateTile, KrateContainer> {
    private final boolean large;
    private IconButton collectButton = IconButton.EMPTY;
    private IconButton hopperButton = IconButton.EMPTY;
    private IconButton compactButton = IconButton.EMPTY;
    public static int prevSide;

    public KrateScreen(KrateContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, Textures.BG_MAP.get(container.te.getVariant()));
        this.large = container.isLarge();
    }

    @Override
    protected void init() {
        super.init();
        this.collectButton = addButton(new IconButton(this.guiLeft + this.xSize, this.guiTop + 8, Textures.COLLECT_BTN, button -> {
            Krate.NET.toServer(new ReqCollectSettingPacket(this.te.getPos()));
        }, this).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.collecting.setting").mergeStyle(TextFormatting.GRAY));
        }));
        this.hopperButton = addButton(new IconButton(this.guiLeft + this.xSize, this.guiTop + 8, Textures.HOPPER_BTN, button -> {
            Krate.NET.toServer(new ReqHopperSettingPacket(this.te.getPos(), Direction.byIndex(prevSide)));
        }, this).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.hopper.setting").mergeStyle(TextFormatting.GRAY));
        }));
        this.compactButton = addButton(new IconButton(this.guiLeft + this.xSize, this.guiTop + 8, Textures.COMPACT_TAB.get(this.te.isSmallMatrix()), button -> {
            Krate.NET.toServer(new CompactingPacket(this.te.getPos()));
        }, this).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.compacting.mode").mergeStyle(TextFormatting.GRAY)
                    .append(new TranslationTextComponent("gui.krate.compacting.matrix." + (this.te.isSmallMatrix() ? "small" : "big")).mergeStyle(TextFormatting.DARK_AQUA)));
        }));
        refresh();
    }

    @Override
    public void tick() {
        super.tick();
        refresh();
    }

    private void refresh() {
        this.collectButton.visible = this.te.canCollect();
        this.hopperButton.visible = this.te.canTransferItems();
        this.compactButton.visible = this.te.canCompact();
        int y = this.collectButton.visible ? 18 : 0;
        this.hopperButton.y = this.guiTop + 8 + y;
        y += this.hopperButton.visible ? 18 : 0;
        this.compactButton.y = this.guiTop + 8 + y;
        this.compactButton.setTexture(Textures.COMPACT_TAB.get(this.te.isSmallMatrix()));
    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        if (this.large) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Textures.KRATE_144_0.draw(matrix, this.guiLeft, this.guiTop);
            Textures.KRATE_144_1.draw(matrix, this.guiLeft + Textures.KRATE_144_0.getWidth(), this.guiTop);
        } else super.drawBackground(matrix, partialTicks, mouseX, mouseY);
        Textures.UP_SLOTS.draw(matrix, this.guiLeft - 27, this.guiTop + 3);
    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        String title = this.title.getString();
        int width = this.font.getStringWidth(title);
        this.font.drawStringWithShadow(matrix, title, this.xSize / 2 - width / 2, -14.0F, 0x999999);
    }

    @Override
    protected boolean hasRedstone() {
        return false;
    }
}
