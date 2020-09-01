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

public class ResetRangePacket implements IPacket<ResetRangePacket> {
    private BlockPos pos;

    public ResetRangePacket(BlockPos pos) {
        this.pos = pos;
    }

    public ResetRangePacket() {
        this(BlockPos.ZERO);
    }

    @Override
    public void encode(ResetRangePacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
    }

    @Override
    public ResetRangePacket decode(PacketBuffer buffer) {
        return new ResetRangePacket(buffer.readBlockPos());
    }

    @Override
    public void handle(ResetRangePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity tile = world.getTileEntity(msg.pos);
                if (tile instanceof KrateTile) {
                    KrateTile krate = (KrateTile) tile;
                    krate.getBox().reset();
                    krate.sync();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
