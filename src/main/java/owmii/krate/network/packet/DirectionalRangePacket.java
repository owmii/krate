package owmii.krate.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.krate.block.KrateTile;
import owmii.lib.network.IPacket;

import java.util.function.Supplier;

public class DirectionalRangePacket implements IPacket<DirectionalRangePacket> {
    private BlockPos pos;
    private int direction;
    private int dist;

    public DirectionalRangePacket(BlockPos pos, int direction, int dist) {
        this.pos = pos;
        this.direction = direction;
        this.dist = dist;
    }

    public DirectionalRangePacket() {
        this(BlockPos.ZERO, 0, 0);
    }

    @Override
    public void encode(DirectionalRangePacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.direction);
        buffer.writeInt(msg.dist);
    }

    @Override
    public DirectionalRangePacket decode(PacketBuffer buffer) {
        return new DirectionalRangePacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt());
    }

    @Override
    public void handle(DirectionalRangePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity tile = world.getTileEntity(msg.pos);
                if (tile instanceof KrateTile) {
                    KrateTile krate = (KrateTile) tile;
                    krate.getBox().add(Direction.byIndex(msg.direction), msg.dist);
                    krate.sync();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
