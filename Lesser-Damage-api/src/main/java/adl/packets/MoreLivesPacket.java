package adl.packets;

import adl.entity.ModAttributes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static adl.capability.EntityDefence.setLives;

public class MoreLivesPacket {

    public MoreLivesPacket() {

    }

    public MoreLivesPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            setLives(player, (int) ModAttributes.getMaxLives(player));
        });
        return true;
    }

}
