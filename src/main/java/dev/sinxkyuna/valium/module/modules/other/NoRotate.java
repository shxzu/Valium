package dev.sinxkyuna.valium.module.modules.other;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.mixin.accesors.PlayerPositionLookS2CPacketAccessor;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
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
