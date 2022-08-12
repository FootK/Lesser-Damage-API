package adl.packets;

import adl.adl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(adl.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Server
        net.messageBuilder(MoreLivesPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MoreLivesPacket::new)
                .encoder(MoreLivesPacket::toBytes)
                .consumerMainThread(MoreLivesPacket::handle)
                .add();

        net.messageBuilder(MoreDamDivPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MoreDamDivPacket::new)
                .encoder(MoreDamDivPacket::toBytes)
                .consumerMainThread(MoreDamDivPacket::handle)
                .add();

        // Client
        net.messageBuilder(LivesDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LivesDataPacket::new)
                .encoder(LivesDataPacket::toBytes)
                .consumerMainThread(LivesDataPacket::handle)
                .add();

        net.messageBuilder(DamageDivPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DamageDivPacket::new)
                .encoder(DamageDivPacket::toBytes)
                .consumerMainThread(DamageDivPacket::handle)
                .add();
    }


    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
