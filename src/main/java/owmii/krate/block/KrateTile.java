package owmii.krate.block;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import owmii.krate.handler.CompactingHandler;
import owmii.krate.item.FilterItem;
import owmii.krate.item.Itms;
import owmii.krate.item.UpgradeItem;
import owmii.lib.block.AbstractTickableTile;
import owmii.lib.block.IInventoryHolder;
import owmii.lib.logistics.inventory.Inventory;
import owmii.lib.logistics.inventory.SidedHopper;
import owmii.lib.util.Stack;
import owmii.lib.util.math.Box;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class KrateTile extends AbstractTickableTile<Tier, KrateBlock> implements IInventoryHolder {
    private final Box box = new Box(0.0D, 0.0D, 4.0D, 4.0D, 4.0D, 4.0D, 0.0D, 12.0D);
    private final SidedHopper hopper;
    public final int exSlots = 6;
    private boolean keepInventory;
    private boolean itemTransfer;
    private boolean collect;
    private boolean compact;
    private boolean compactSleep;
    private boolean smallMatrix;
    private int range = 8;

    @OnlyIn(Dist.CLIENT)
    public boolean visualize;

    public KrateTile(Tier tier) {
        super(tier.getType(), tier);
        this.inv.set(tier.getInvSize() + this.exSlots);
        this.hopper = new SidedHopper(this.inv);
    }

    public void copy(KrateTile other) {
        CompoundNBT nbt = new CompoundNBT();
        other.box.write(nbt, "item_collect");
        this.box.read(nbt, "item_collect");
        other.hopper.write(nbt, "item_collect");
        this.hopper.read(nbt, "item_collect");
        this.keepInventory = other.keepInventory;
        this.itemTransfer = other.itemTransfer;
        this.collect = other.collect;
        this.compact = other.compact;
        this.compactSleep = other.compactSleep;
        this.smallMatrix = other.smallMatrix;
        this.range = other.range;
        sync();
    }

    @Override
    protected void readSync(CompoundNBT nbt) {
        super.readSync(nbt);
        this.itemTransfer = nbt.getBoolean("item_transfer");
        this.collect = nbt.getBoolean("item_collect");
        this.compact = nbt.getBoolean("item_compact");
        this.smallMatrix = nbt.getBoolean("small_matrix");
        this.box.read(nbt, "item_collect");
        this.hopper.read(nbt, "item_transfer");
    }

    @Override
    protected CompoundNBT writeSync(CompoundNBT nbt) {
        nbt.putBoolean("item_transfer", this.itemTransfer);
        nbt.putBoolean("item_collect", this.collect);
        nbt.putBoolean("item_compact", this.compact);
        nbt.putBoolean("small_matrix", this.smallMatrix);
        this.box.write(nbt, "item_collect");
        this.hopper.write(nbt, "item_transfer");
        return super.writeSync(nbt);
    }

    @Override
    public void readStorable(CompoundNBT nbt) {
        this.keepInventory = nbt.getBoolean("keep_inventory");
        super.readStorable(nbt);
    }

    @Override
    public CompoundNBT writeStorable(CompoundNBT nbt) {
        nbt.putBoolean("keep_inventory", this.keepInventory);
        return super.writeStorable(nbt);
    }

    @Override
    protected int postTick(World world) {
        if (!isRemote()) {
            if (this.collect && this.ticks % 20 == 0) {
                if (!this.inv.isFull()) {
                    List<ItemEntity> entities = world.getEntitiesWithinAABB(ItemEntity.class, this.box.geAxis(getPos()));
                    entities.forEach(entity -> {
                        ItemStack stack = entity.getItem();
                        if (!stack.isEmpty() && checkFilter(stack)) {
                            ItemStack stack1 = ItemHandlerHelper.insertItem(this.inv, stack, false);
                            if (stack1.isEmpty()) {
                                entity.remove();
                            } else if (stack1.getCount() < stack.getCount()) {
                                entity.setItem(stack1);
                            }
                        }
                    });
                }
            }
            if (this.itemTransfer && this.ticks % 10 == 0) {
                for (Direction side : Direction.values()) {
                    LazyOptional<IItemHandler> cap = Inventory.get(world, this.pos.offset(side), side.getOpposite());
                    cap.ifPresent(handler -> this.hopper.transfer(side, handler, 3, this::checkFilter, this::checkPushFilter,
                            IntStream.range(this.inv.getSlots() - this.exSlots, this.inv.getSlots()).toArray()));
                }
            }
            if (this.compact && !this.compactSleep && this.ticks % 10 == 0) {
                boolean sleep = true;
                label:
                for (int i = 0; i < this.variant.getInvSize(); i++) {
                    ItemStack stack = this.inv.getStackInSlot(i);
                    ItemStack res = CompactingHandler.get(world, stack, this.smallMatrix);
                    Set<ItemStack> stacks = new HashSet<>();
                    int matrix = this.smallMatrix ? 4 : 9;
                    if (!res.isEmpty()) {
                        int sum = 0;
                        for (int j = 0; j < this.variant.getInvSize(); j++) {
                            ItemStack stack2 = this.inv.getStackInSlot(j);
                            int count = stack2.getCount();
                            if (stack2.isItemEqual(stack)) {
                                if (count >= matrix) {
                                    Iterator<ItemStack> itr = stacks.iterator();
                                    stack2.shrink(matrix - stacks.stream().mapToInt(ItemStack::getCount).sum());
                                    while (itr.hasNext()) {
                                        ItemStack toRemove = itr.next();
                                        toRemove.shrink(toRemove.getCount());
                                        itr.remove();
                                    }
                                    ItemHandlerHelper.insertItem(this.inv, res.copy(), false);
                                    sleep = false;
                                    break label;
                                } else {
                                    int total = sum + count;
                                    if (total >= matrix) {
                                        Iterator<ItemStack> itr = stacks.iterator();
                                        stack2.shrink(matrix - stacks.stream().mapToInt(ItemStack::getCount).sum());
                                        while (itr.hasNext()) {
                                            ItemStack toRemove = itr.next();
                                            toRemove.shrink(toRemove.getCount());
                                            itr.remove();
                                        }
                                        ItemHandlerHelper.insertItem(this.inv, res.copy(), false);
                                        sleep = false;
                                        break label;
                                    } else {
                                        sum += count;
                                        stacks.add(stack2);
                                    }
                                }
                            }
                        }
                    }
                }
                this.compactSleep = sleep;
            }
        }
        return super.postTick(world);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    protected void onFirstTick(World world) {
        super.onFirstTick(world);
        checkUpgrades();
    }

    @Override
    public void onSlotChanged(int slot) {
        checkUpgrades();
    }

    private void checkUpgrades() {
        this.compactSleep = false;
        int size = this.variant.getInvSize();
        boolean itemTransfer = false;
        boolean collect = false;
        boolean keepInv = false;
        boolean compact = false;
        for (int i = 0; i < 4; i++) {
            ItemStack stack = this.inv.getStackInSlot(i + size + 2);
            if (stack.getItem() == Itms.HOPPER_UPGRADE) {
                itemTransfer = true;
            } else if (stack.getItem() == Itms.COLLECT_UPGRADE) {
                collect = true;
            } else if (stack.getItem() == Itms.SHULKER_UPGRADE) {
                keepInv = true;
            } else if (stack.getItem() == Itms.COMPACT_UPGRADE) {
                compact = true;
            }
        }
        boolean sync = false;
        if (this.itemTransfer != itemTransfer) {
            this.itemTransfer = itemTransfer;
            if (!itemTransfer) {
                if (this.world != null) {
                    Stack.drop(this.world, getPos(), this.inv.getStackInSlot(size + 1));
                    this.inv.setStack(size + 1, ItemStack.EMPTY);
                }
            }
            sync = true;
        }
        if (this.collect != collect) {
            this.collect = collect;
            if (!collect) {
                this.box.reset();
                if (isRemote()) {
                    this.visualize = false;
                }
            }
            sync = true;
        }
        if (this.keepInventory != keepInv) {
            this.keepInventory = keepInv;
            sync = true;
        }
        if (this.compact != compact) {
            this.compact = compact;
            sync = true;
        }
        if (sync) {
            sync();
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        int size = this.variant.getInvSize();
        if (slot >= size) {
            if (slot == size || slot == size + 1) {
                return stack.getItem() instanceof FilterItem;
            }
            for (int i = 0; i < 4; i++) {
                ItemStack stack1 = this.inv.getStackInSlot(i + size + 2);
                if (stack.isItemEqual(stack1)) {
                    return false;
                }
            }
            return stack.getItem() instanceof UpgradeItem;
        }
        return checkFilter(stack);
    }

    private boolean checkFilter(ItemStack stack) {
        ItemStack filterStack = this.inv.getStackInSlot(this.variant.getInvSize());
        if (filterStack.getItem() instanceof FilterItem) {
            FilterItem filter = (FilterItem) filterStack.getItem();
            return filter.getFilter(filterStack).checkStack(stack);
        }
        return true;
    }

    private boolean checkPushFilter(ItemStack stack) {
        ItemStack filterStack = this.inv.getStackInSlot(this.variant.getInvSize() + 1);
        if (filterStack.getItem() instanceof FilterItem) {
            FilterItem filter = (FilterItem) filterStack.getItem();
            return filter.getFilter(filterStack).checkStack(stack);
        }
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return slot < this.variant.getInvSize();
    }

    @Override
    protected boolean keepInventory() {
        return this.keepInventory;
    }

    public boolean canCollect() {
        return this.collect;
    }

    public boolean canTransferItems() {
        return this.itemTransfer;
    }

    public boolean canCompact() {
        return this.compact;
    }

    public boolean isSmallMatrix() {
        return this.smallMatrix;
    }

    public void switchMatrix() {
        this.smallMatrix = !this.smallMatrix;
        this.compactSleep = false;
    }

    public Box getBox() {
        return this.box;
    }

    public SidedHopper getHopper() {
        return this.hopper;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return this.visualize ? INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
    }
}
