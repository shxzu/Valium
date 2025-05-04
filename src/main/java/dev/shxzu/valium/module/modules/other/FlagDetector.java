package dev.shxzu.valium.module.modules.other;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.network.EventPacket;
import dev.shxzu.valium.event.types.TransferOrder;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.utils.render.notifications.impl.Notification;
import dev.shxzu.valium.utils.render.notifications.impl.NotificationMoode;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class FlagDetector extends Module {
    public FlagDetector() {
        super("FlagDetector", "Detects flags", 0, ModuleCategory.OTHER);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.RECEIVE) {
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
                Client.INSTANCE.getNotificationManager().addNewNotification(new Notification("You flagged", 1000, NotificationMoode.WARNING));
            }
        }
    };
}
