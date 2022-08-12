package adl.packets;


import adl.client.ClientDefenceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LivesDataPacket {
    private final int lives;

    public LivesDataPacket(int lives) {
        this.lives = lives;
    }

    public LivesDataPacket(FriendlyByteBuf buf) {
        this.lives = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(lives);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientDefenceData.set(lives));
        return true;
    }
}