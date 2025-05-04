package dev.shxzu.valium.event.impl.network;

import dev.shxzu.valium.event.types.CancellableEvent;
import dev.shxzu.valium.event.types.TransferOrder;
import net.minecraft.network.packet.Packet;


public class EventPacket extends CancellableEvent {
    private Packet packet;
    private TransferOrder order;

    public EventPacket(Packet packet, TransferOrder order) {
        this.packet = packet;
        this.order = order;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
    public Packet getPacket(){
        return this.packet;
    }
    public TransferOrder getOrder(){
        return this.order;
    }
}