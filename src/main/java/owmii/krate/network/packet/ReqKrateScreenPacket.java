package owmii.krate.network.packet;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import owmii.krate.block.KrateTile;
import owmii.krate.inventory.KrateContainer;
import owmii.lib.network.IPacket;

import java.util.function.Supplier;

public class ReqKrateScreenPacket implements IPacket<ReqKrateScreenPacket> {
    private BlockPos pos;

    public ReqKrateScreenPacket(BlockPos pos) {
        this.pos = pos;
    }

    public ReqKrateScreenPacket() {
        this(BlockPos.ZERO);
    }

    @Override
    public void encode(ReqKrateScreenPacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
    }

    @Override
    public ReqKrateScreenPacket decode(PacketBuffer buffer) {
        return new ReqKrateScreenPacket(buffer.readBlockPos());
    }

    @Override
    public void handle(ReqKrateScreenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                World world = player.getEntityWorld();
                TileEntity te = world.getTileEntity(msg.pos);
                if (te instanceof KrateTile) {
                    BlockState state = world.getBlockState(msg.pos);
                    NetworkHooks.openGui(player, new SimpleNamedContainerProvider((i, playerInventory, playerEntity) -> {
                                return new KrateContainer(i, playerInventory, (KrateTile) te);
                            }, new ItemStack(state.getBlock()).getDisplayName())
                            , buffer -> {
                                buffer.writeBlockPos(msg.pos);
                            });
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
