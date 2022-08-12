package adl.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static adl.capability.EntityDefence.getDamageDivision;
import static adl.capability.EntityDefence.setDamageDivision;

public class MoreDamDivPacket {

    public MoreDamDivPacket() {

    }

    public MoreDamDivPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            setDamageDivision(player, getDamageDivision(player) * 10);
        });
        return true;
    }

}
