package dev.sinxkyuna.valium.module.modules.combat.criticals;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.mixin.accesors.PlayerMoveC2SPacketAccessor;
import dev.sinxkyuna.valium.module.modules.combat.Criticals;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoGroundCriticals extends SubMode<Criticals> {
    public NoGroundCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                PlayerMoveC2SPacketAccessor accessor = (PlayerMoveC2SPacketAccessor) packet;
                accessor.setOnGround(false);
            }
        }
    };
}
