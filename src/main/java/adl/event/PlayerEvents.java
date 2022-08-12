package adl.event;

import adl.entity.ModAttributes;
import adl.packets.DamageDivPacket;
import adl.packets.LivesDataPacket;
import adl.packets.ModMessages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;

import static adl.capability.EntityDefence.*;

public class PlayerEvents {
    public static void preInit() {
        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.NORMAL, false, TickEvent.PlayerTickEvent.class, PlayerEvents::onPlayerTick);
        forgeBus.addListener(EventPriority.NORMAL, false, EntityJoinLevelEvent.class, PlayerEvents::onPlayerJoin);

    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        if (ev.side == LogicalSide.SERVER) {
            ModMessages.sendToPlayer(new LivesDataPacket(getLives(ev.player)), ((ServerPlayer) ev.player));
            ModMessages.sendToPlayer(new DamageDivPacket(getDamageDivision(ev.player)), ((ServerPlayer) ev.player));

        }

        if (getLives(ev.player) > ModAttributes.getMaxLives(ev.player)) {
            setLives(ev.player, (int) ModAttributes.getMaxLives(ev.player));
        }
    }


    public static void onPlayerJoin(EntityJoinLevelEvent ev) {
        if (!ev.getLevel().isClientSide()) {
            if (ev.getEntity() instanceof ServerPlayer player) {
                ModMessages.sendToPlayer(new LivesDataPacket(getLives(player)), (player));
                ModMessages.sendToPlayer(new DamageDivPacket(getDamageDivision(player)), (player));

            }
        }
    }
}
