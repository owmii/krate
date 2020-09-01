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

public class CompactingPacket implements IPacket<CompactingPacket> {
    private BlockPos pos;

    public CompactingPacket(BlockPos pos) {
        this.pos = pos;
    }

    public CompactingPacket() {
        this(BlockPos.ZERO);
    }

    @Override
    public void encode(CompactingPacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
    }

    @Override
    public CompactingPacket decode(PacketBuffer buffer) {
        return new CompactingPacket(buffer.readBlockPos());
    }

    @Override
    public void handle(CompactingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity tile = world.getTileEntity(msg.pos);
                if (tile instanceof KrateTile) {
                    KrateTile krate = (KrateTile) tile;
                    krate.switchMatrix();
                    krate.sync();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
