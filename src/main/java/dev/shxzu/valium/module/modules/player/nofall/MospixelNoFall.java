package dev.shxzu.valium.module.modules.player.nofall;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.network.EventPacket;
import dev.shxzu.valium.event.types.TransferOrder;
import dev.shxzu.valium.mixin.accesors.PlayerMoveC2SPacketAccessor;
import dev.shxzu.valium.module.modules.player.NoFall;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class MospixelNoFall extends SubMode<NoFall> {
    public MospixelNoFall(String name, NoFall parentModule) {
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

                if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue()) {
                    accessor.setOnGround(mc.player.age % 2 == 0);
                }
            }
        }
    };
}
