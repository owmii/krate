package owmii.krate.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.krate.block.KrateTile;
import owmii.lib.network.IPacket;

import java.util.function.Supplier;

public class RangePacket implements IPacket<RangePacket> {
    private BlockPos pos;
    private int dist;

    public RangePacket(BlockPos pos, int dist) {
        this.pos = pos;
        this.dist = dist;
    }

    public RangePacket() {
        this(BlockPos.ZERO, 0);
    }

    @Override
    public void encode(RangePacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.dist);
    }

    @Override
    public RangePacket decode(PacketBuffer buffer) {
        return new RangePacket(buffer.readBlockPos(), buffer.readInt());
    }

    @Override
    public void handle(RangePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity tile = world.getTileEntity(msg.pos);
                if (tile instanceof KrateTile) {
                    KrateTile krate = (KrateTile) tile;
                    krate.getBox().add(msg.dist);
                    krate.sync();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
