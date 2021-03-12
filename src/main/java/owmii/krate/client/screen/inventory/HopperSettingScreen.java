package owmii.krate.client.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;
import owmii.krate.Krate;
import owmii.krate.client.screen.Textures;
import owmii.krate.inventory.HopperSettingContainer;
import owmii.krate.network.packet.HopperPacket;
import owmii.krate.network.packet.ReqHopperSettingPacket;
import owmii.lib.client.screen.widget.IconButton;
import owmii.lib.client.util.Text;
import owmii.lib.logistics.inventory.Inventory;
import owmii.lib.logistics.inventory.SidedHopper;

public class HopperSettingScreen extends KrateSettingScreen<HopperSettingContainer> {
    private final IconButton[] tabButtons = new IconButton[6];
    private IconButton pushButton = IconButton.EMPTY;
    private IconButton pullButton = IconButton.EMPTY;
    private final Direction side;

    public HopperSettingScreen(HopperSettingContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, Textures.HOPPER_SET);
        this.side = container.getSide();
        KrateScreen.prevSide = this.side.getIndex();
    }

    @Override
    protected void init() {
        super.init();
        int x = 0;
        int y = -23;
        for (int i = 0; i < 6; i++) {
            boolean b = i % 2 != 0;
            boolean b2 = this.side.getIndex() == i;
            x = (b ? 149 + (b2 ? 0 : 3) : 1 + (b2 ? 0 : 1));
            y += (b ? 0 : 27);
            Direction side = Direction.byIndex(i);
            BlockState state = null;
            BlockPos pos = this.te.getPos().offset(side);
            if (this.mc.world != null && !this.mc.world.isAirBlock(pos)) {
                if (Inventory.get(this.mc.world, pos, side).isPresent()) {
                    state = this.mc.world.getBlockState(pos);
                }
            }
            ITextComponent t = new StringTextComponent(state != null ? "" : StringUtils.substring(side.name(), 0, 2)).setStyle(Text.color(0x444444));
            final BlockState state1 = state;
            this.tabButtons[i] = addButton(new IconButton(this.guiLeft + x, this.guiTop + y, t, Textures.TAB_MAP.get(!b).get(b2), button -> {
                Krate.NET.toServer(new ReqHopperSettingPacket(this.te.getPos(), side));
            }, this).setStackInSlot(state != null ? new ItemStack(state.getBlock()) : ItemStack.EMPTY).xOffset(!b2 ? !b ? 2 : -2 : 0).setTooltip(tooltip -> {
                tooltip.add(new TranslationTextComponent("gui.krate.facing").mergeStyle(TextFormatting.GRAY)
                        .append(new TranslationTextComponent("gui.krate." + side.getName2()).mergeStyle(TextFormatting.DARK_GRAY)));
                if (state1 != null) {
                    tooltip.add(new TranslationTextComponent("gui.krate.block").mergeStyle(TextFormatting.GRAY)
                            .append(new ItemStack(state1.getBlock()).getDisplayName().copyRaw().mergeStyle(TextFormatting.DARK_AQUA)));
                }
            }));
        }
        this.pushButton = addButton(new IconButton(this.guiLeft + 61, this.guiTop + 34, Textures.HOPPER_SW_BTN, button -> {
            Krate.NET.toServer(new HopperPacket(this.te.getPos(), this.side, 0));
            this.te.getHopper().switchPush(this.side);
        }, this));
        this.pullButton = addButton(new IconButton(this.guiLeft + 123, this.guiTop + 34, Textures.HOPPER_SW_BTN, button -> {
            Krate.NET.toServer(new HopperPacket(this.te.getPos(), this.side, 1));
            this.te.getHopper().switchPull(this.side);
        }, this));
        refresh();
    }

    @Override
    public void tick() {
        super.tick();
        refresh();
    }

    private void refresh() {
        SidedHopper hopper = this.te.getHopper();
        boolean push = hopper.canPush(this.side);
        boolean pull = hopper.canPull(this.side);
        this.pushButton.setMessage(new TranslationTextComponent(push ? "gui.krate.on" : "gui.krate.off").mergeStyle(push ? TextFormatting.GREEN : TextFormatting.RED));
        this.pullButton.setMessage(new TranslationTextComponent(pull ? "gui.krate.on" : "gui.krate.off").mergeStyle(pull ? TextFormatting.GREEN : TextFormatting.RED));
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.font.drawString(matrix, I18n.format("gui.krate.facing") + TextFormatting.WHITE + I18n.format("gui.krate." +
                this.side.getName2()), this.guiLeft + 34, this.guiTop + 10.5F, 0x444444);
        this.font.drawString(matrix, I18n.format("gui.krate.push"), this.guiLeft + 34, this.guiTop + 41 - 4, 0x555555);
        this.font.drawString(matrix, I18n.format("gui.krate.pull"), this.guiLeft + 96, this.guiTop + 41 - 4, 0x555555);
    }
}
