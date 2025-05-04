package dev.shxzu.valium.module.modules.other;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.network.EventPacket;
import dev.shxzu.valium.mixin.accesors.PlayerPositionLookS2CPacketAccessor;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", "Prevents the server from rotating your head", 0, ModuleCategory.OTHER);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        if (event.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
            ((PlayerPositionLookS2CPacketAccessor) packet).setPitch(mc.player.getPitch());
            ((PlayerPositionLookS2CPacketAccessor) packet).setYaw(mc.player.getYaw());
        }
    };
}
