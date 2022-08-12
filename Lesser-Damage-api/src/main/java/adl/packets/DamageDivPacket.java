package adl.packets;

import adl.client.ClientDefenceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DamageDivPacket {
    private final long damageDiv;


    public DamageDivPacket(long damageDiv) {
        this.damageDiv = damageDiv;

    }

    public DamageDivPacket(FriendlyByteBuf buf) {
        this.damageDiv = buf.readLong();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(damageDiv);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientDefenceData.setDamageDivider(damageDiv));
        return true;
    }
}
