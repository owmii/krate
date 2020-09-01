package owmii.krate.client.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;
import owmii.krate.Krate;
import owmii.krate.client.screen.Textures;
import owmii.krate.inventory.CollectSettingContainer;
import owmii.krate.network.packet.DirectionalRangePacket;
import owmii.krate.network.packet.RangePacket;
import owmii.krate.network.packet.ResetRangePacket;
import owmii.lib.client.screen.widget.IconButton;

public class CollectSettingScreen extends KrateSettingScreen<CollectSettingContainer> {
    private IconButton[] upRangeButtons = new IconButton[6];
    private IconButton[] downRangeButtons = new IconButton[6];
    private IconButton upRangeAllButton = IconButton.EMPTY;
    private IconButton downRangeAllButton = IconButton.EMPTY;
    private IconButton resetButton = IconButton.EMPTY;
    private IconButton visualButton = IconButton.EMPTY;

    public CollectSettingScreen(CollectSettingContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, Textures.COLLECT_SET);
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < 6; i++) {
            final int id = i;
            this.upRangeButtons[i] = addButton(new IconButton(this.guiLeft + 6 + 24 + (i * 15), this.guiTop + 6, Textures.UP_RANGE, button -> {
                Krate.NET.toServer(new DirectionalRangePacket(this.te.getPos(), id, 1));
                this.te.getBox().add(Direction.byIndex(id), 1);
            }, this));
            this.downRangeButtons[i] = addButton(new IconButton(this.guiLeft + 6 + 24 + (i * 15), this.guiTop + 28, Textures.DOWN_RANGE, button -> {
                Krate.NET.toServer(new DirectionalRangePacket(this.te.getPos(), id, -1));
                this.te.getBox().add(Direction.byIndex(id), -1);
            }, this));
        }
        this.upRangeAllButton = addButton(new IconButton(this.guiLeft + 99 + 24, this.guiTop + 6, Textures.UP_RANGE_ALL, button -> {
            Krate.NET.toServer(new RangePacket(this.te.getPos(), 1));
            this.te.getBox().add(1);
        }, this));
        this.downRangeAllButton = addButton(new IconButton(this.guiLeft + 99 + 24, this.guiTop + 28, Textures.DOWN_RANGE_ALL, button -> {
            Krate.NET.toServer(new RangePacket(this.te.getPos(), -1));
            this.te.getBox().add(-1);
        }, this));
        this.resetButton = addButton(new IconButton(this.guiLeft + 99 + 24, this.guiTop + 16, Textures.RESET_RANGE, button -> {
            Krate.NET.toServer(new ResetRangePacket(this.te.getPos()));
            this.te.getBox().reset();
        }, this).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.reset").mergeStyle(TextFormatting.GRAY));
        }));
        this.visualButton = addButton(new IconButton(this.guiLeft + 113 + 24, this.guiTop + 39, Textures.VIS_MAP.get(this.te.visualize), button -> {
            this.te.visualize = !this.te.visualize;
        }, this).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.visualize").mergeStyle(TextFormatting.LIGHT_PURPLE));
        }));
        refresh();
    }

    @Override
    public void tick() {
        super.tick();
        refresh();
    }

    private void refresh() {
        this.visualButton.setTexture(Textures.VIS_MAP.get(this.te.visualize));
    }

    @Override
    public void render(MatrixStack matrix, int mx, int my, float pt) {
        super.render(matrix, mx, my, pt);
        for (Direction dir : Direction.values()) {
            String s = "" + (int) this.te.getBox().get(dir);
            float f = 4.5F - this.font.getStringWidth(s) / 2.0F;
            this.font.drawString(matrix, s, this.guiLeft + 32.5F + (dir.getIndex() * 15) + f, this.guiTop + 18, 0xcccccc);
            this.font.drawString(matrix, StringUtils.substring(dir.name(), 0, 1), this.guiLeft + 34 + (dir.getIndex() * 15), this.guiTop + 40, 0x333333);
        }
        this.font.drawString(matrix, "Res", this.guiLeft + 126, this.guiTop + 18, 0x444444);
    }
}
