package owmii.krate.block;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import owmii.lib.block.IVariant;

public enum Tier implements IVariant<Tier> {
    SMALL(9, 1),
    BASIC(9, 4),
    BIG(9, 8),
    LARGE(18, 8);

    private final int rows;
    private final int columns;

    Tier(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Tier[] getVariants() {
        return values();
    }

    public int getInvSize() {
        return this.rows * this.columns;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Block getBlock() {
        if (equals(SMALL)) {
            return Blcks.KRATE_SMALL;
        } else if (equals(BASIC)) {
            return Blcks.KRATE_BASIC;
        } else if (equals(BIG)) {
            return Blcks.KRATE_BIG;
        }
        return Blcks.KRATE_LARGE;
    }

    public TileEntityType<?> getType() {
        if (equals(SMALL)) {
            return Tiles.KRATE_SMALL;
        } else if (equals(BASIC)) {
            return Tiles.KRATE_BASIC;
        } else if (equals(BIG)) {
            return Tiles.KRATE_BIG;
        }
        return Tiles.KRATE_LARGE;
    }
}
