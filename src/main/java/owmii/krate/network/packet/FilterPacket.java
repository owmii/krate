package owmii.krate.network.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.krate.item.FilterItem;
import owmii.lib.network.IPacket;

import java.util.function.Supplier;

public class FilterPacket implements IPacket<FilterPacket> {
    private int action;

    public FilterPacket(int action) {
        this.action = action;
    }

    public FilterPacket() {
        this(0);
    }

    @Override
    public void encode(FilterPacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.action);
    }

    @Override
    public FilterPacket decode(PacketBuffer buffer) {
        return new FilterPacket(buffer.readInt());
    }

    @Override
    public void handle(FilterPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem() instanceof FilterItem) {
                    FilterItem filter = (FilterItem) stack.getItem();
                    switch (msg.action) {
                        case -1:
                            filter.getFilter(stack).clear();
                            break;
                        case 0:
                            filter.getFilter(stack).switchMode();
                            break;
                        case 1:
                            filter.getFilter(stack).switchTag();
                            break;
                        case 2:
                            filter.getFilter(stack).switchNBT();
                            break;
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
