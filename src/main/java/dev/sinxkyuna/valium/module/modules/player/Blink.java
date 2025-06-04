package dev.sinxkyuna.valium.module.modules.player;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

public class Blink extends Module {

    public Blink() {
        super("Blink", "Holds packets", 0, ModuleCategory.PLAYER);
    }

    public final ArrayList<Packet<?>> packets = new ArrayList<>();

    @Override
    public void onEnable() {
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (isNull()) {
            packets.clear();
        } else {
            for (Packet<?> packet : packets) {
                mc.getNetworkHandler().sendPacket(packet);
            }
        }
        super.onDisable();
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (mc.player == null || mc.world == null) return;

        if (event.getOrder() == TransferOrder.RECEIVE) {
            return;
        }

        Packet<?> packet = event.getPacket();

        event.cancel();
        packets.add(packet);
    };
}
