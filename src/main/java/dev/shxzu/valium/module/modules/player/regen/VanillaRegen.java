package dev.shxzu.valium.module.modules.player.regen;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.player.Regen;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PacketUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class VanillaRegen extends SubMode<Regen> {
    public VanillaRegen(String name, Regen parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.getHealth() < getParentModule().health.getValue()) {
            for (int i = 0; i < getParentModule().packets.getValue(); i++) {
                PacketUtils.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(mc.player.isOnGround()));
            }
        }
    };
}
