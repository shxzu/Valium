package dev.sinxkyuna.valium.module.modules.other;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.utils.render.notifications.impl.Notification;
import dev.sinxkyuna.valium.utils.render.notifications.impl.NotificationMoode;
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
