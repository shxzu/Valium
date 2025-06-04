package dev.sinxkyuna.valium.module.modules.player.nofall;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.network.EventPacket;
import dev.sinxkyuna.valium.event.types.TransferOrder;
import dev.sinxkyuna.valium.mixin.accesors.PlayerMoveC2SPacketAccessor;
import dev.sinxkyuna.valium.module.modules.player.NoFall;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoGroundNoFall extends SubMode<NoFall> {
    public NoGroundNoFall(String name, NoFall parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (PlayerUtil.isOverVoid()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                PlayerMoveC2SPacketAccessor accessor = (PlayerMoveC2SPacketAccessor) packet;

                if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue() || mc.player.fallDistance == 0) {
                    accessor.setOnGround(false);
                }
            }
        }
    };
}
