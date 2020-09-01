package owmii.krate.network;

import owmii.krate.Krate;
import owmii.krate.network.packet.*;

public class Packets {
    public static void register() {
        Krate.NET.register(new FilterPacket());
        Krate.NET.register(new DirectionalRangePacket());
        Krate.NET.register(new RangePacket());
        Krate.NET.register(new ResetRangePacket());
        Krate.NET.register(new ReqHopperSettingPacket());
        Krate.NET.register(new ReqCollectSettingPacket());
        Krate.NET.register(new ReqKrateScreenPacket());
        Krate.NET.register(new HopperPacket());
        Krate.NET.register(new CompactingPacket());
    }
}
