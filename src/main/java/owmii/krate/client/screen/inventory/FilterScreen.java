package owmii.krate.client.screen.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import owmii.krate.Krate;
import owmii.krate.client.screen.Textures;
import owmii.krate.inventory.FilterContainer;
import owmii.krate.inventory.GhostSlot;
import owmii.krate.item.FilterItem;
import owmii.krate.item.Itms;
import owmii.krate.network.packet.FilterPacket;
import owmii.lib.client.screen.container.AbstractContainerScreen;
import owmii.lib.client.screen.widget.IconButton;
import owmii.lib.logistics.Filter;

public class FilterScreen extends AbstractContainerScreen<FilterContainer> {
    private IconButton typeButton = IconButton.EMPTY;
    private IconButton nbtButton = IconButton.EMPTY;
    private IconButton tagButton = IconButton.EMPTY;
    private IconButton clearButton = IconButton.EMPTY;

    private ItemStack stack = ItemStack.EMPTY;
    private final Filter filter;

    public FilterScreen(FilterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, Textures.FILTER);
        PlayerEntity player = this.mc.player;
        if (player != null) {
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.getItem() instanceof FilterItem) {
                this.stack = stack;
            }
        }
        this.filter = ((FilterItem) Itms.FILTER).getFilter(this.stack);
    }

    @Override
    protected void init() {
        super.init();
        this.typeButton = addButton(new IconButton(this.guiLeft + 6, this.guiTop + 5, Textures.MODE_MAP.get(this.filter.isBlackList()), (button) -> {
            Krate.NET.toServer(new FilterPacket(0));
            this.filter.switchMode();
        }, this)).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.filter.mode." + (this.filter.isBlackList() ? "blacklist" : "whitelist")).mergeStyle(TextFormatting.GRAY));
        });
        this.tagButton = addButton(new IconButton(this.guiLeft + 18, this.guiTop + 5, Textures.TAG_MAP.get(this.filter.compareTag()), (button) -> {
            Krate.NET.toServer(new FilterPacket(1));
            this.filter.switchTag();
        }, this)).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.filter.tag." + this.filter.compareTag()).mergeStyle(TextFormatting.GRAY));
        });
        this.nbtButton = addButton(new IconButton(this.guiLeft + 30, this.guiTop + 5, Textures.NBT_MAP.get(this.filter.compareNBT()), (button) -> {
            Krate.NET.toServer(new FilterPacket(2));
            this.filter.switchNBT();
        }, this)).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.filter.nbt." + this.filter.compareNBT()).mergeStyle(TextFormatting.GRAY));
        });
        this.clearButton = addButton(new IconButton(this.guiLeft + this.xSize - 18, this.guiTop + 5, Textures.CLEAR, (button) -> {
            Krate.NET.toServer(new FilterPacket(-1));
            if (this.mc.player != null) {
                this.filter.clear();
                this.container.inventorySlots.forEach(slot -> {
                    if (slot instanceof GhostSlot) slot.putStack(ItemStack.EMPTY);
                });
            }
        }, this)).setTooltip(tooltip -> {
            tooltip.add(new TranslationTextComponent("gui.krate.filter.clear").mergeStyle(TextFormatting.GRAY));
        });
    }

    @Override
    public void tick() {
        this.typeButton.setTexture(Textures.MODE_MAP.get(this.filter.isBlackList()));
        this.nbtButton.setTexture(Textures.NBT_MAP.get(this.filter.compareNBT()));
        this.tagButton.setTexture(Textures.TAG_MAP.get(this.filter.compareTag()));
        super.tick();
    }
}
