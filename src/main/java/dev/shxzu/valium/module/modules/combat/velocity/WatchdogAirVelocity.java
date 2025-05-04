package dev.shxzu.valium.module.modules.combat.velocity;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.network.EventPacket;
import dev.shxzu.valium.mixin.accesors.EntityVelocityUpdateS2CPacketAccessor;
import dev.shxzu.valium.module.modules.combat.Velocity;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PlayerUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class WatchdogAirVelocity extends SubMode<Velocity> {
    public WatchdogAirVelocity(String name, Velocity parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        Packet<?> packet = event.getPacket();

        if (packet instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacketAccessor s12 = (EntityVelocityUpdateS2CPacketAccessor) packet;
            if (s12.getId() == mc.player.getId()) {
                if (getParentModule().canWork(event)) {
                    if (PlayerUtil.inAirTicks() <= getParentModule().airTicks.getValue()) {
                        mc.player.setVelocity(mc.player.getVelocity().x, s12.getVelocityY() / 8000D, mc.player.getVelocity().z);
                    }
                    event.cancel();
                }
            }
        }
    };
}
