package owmii.krate.network.packet;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import owmii.krate.block.KrateTile;
import owmii.krate.inventory.HopperSettingContainer;
import owmii.lib.network.IPacket;

import java.util.function.Supplier;

public class ReqHopperSettingPacket implements IPacket<ReqHopperSettingPacket> {
    private BlockPos pos;
    private Direction side;

    public ReqHopperSettingPacket(BlockPos pos, Direction side) {
        this.pos = pos;
        this.side = side;
    }

    public ReqHopperSettingPacket() {
        this(BlockPos.ZERO, Direction.NORTH);
    }

    @Override
    public void encode(ReqHopperSettingPacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.side.getIndex());
    }

    @Override
    public ReqHopperSettingPacket decode(PacketBuffer buffer) {
        return new ReqHopperSettingPacket(buffer.readBlockPos(), Direction.byIndex(buffer.readInt()));
    }

    @Override
    public void handle(ReqHopperSettingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity te = world.getTileEntity(msg.pos);
                if (te instanceof KrateTile) {
                    BlockState state = world.getBlockState(msg.pos);
                    NetworkHooks.openGui(player, new SimpleNamedContainerProvider((i, playerInventory, playerEntity) -> {
                                return new HopperSettingContainer(i, playerInventory, (KrateTile) te);
                            }, new ItemStack(state.getBlock()).getDisplayName())
                            , buffer -> {
                                buffer.writeBlockPos(msg.pos);
                                buffer.writeInt(msg.side.getIndex());
                            });
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
