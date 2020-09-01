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

public class HopperPacket implements IPacket<HopperPacket> {
    private BlockPos pos;
    private final Direction side;
    private int mode;

    public HopperPacket(BlockPos pos, Direction side, int mode) {
        this.pos = pos;
        this.side = side;
        this.mode = mode;
    }

    public HopperPacket() {
        this(BlockPos.ZERO, Direction.DOWN, 0);
    }

    @Override
    public void encode(HopperPacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.side.getIndex());
        buffer.writeInt(msg.mode);
    }

    @Override
    public HopperPacket decode(PacketBuffer buffer) {
        return new HopperPacket(buffer.readBlockPos(), Direction.byIndex(buffer.readInt()), buffer.readInt());
    }

    @Override
    public void handle(HopperPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity tile = world.getTileEntity(msg.pos);
                if (tile instanceof KrateTile) {
                    KrateTile krate = (KrateTile) tile;
                    if (msg.mode == 0) {
                        krate.getHopper().switchPush(msg.side);
                    } else krate.getHopper().switchPull(msg.side);
                    krate.sync();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
