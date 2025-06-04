package dev.sinxkyuna.valium.module.modules.player.nofall;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.player.NoFall;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.PacketUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PacketNoFall extends SubMode<NoFall> {
    public PacketNoFall(String name, NoFall parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (PlayerUtil.isOverVoid()) {
            return;
        }
        if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue()) {
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            mc.player.fallDistance = 0;
        }
    };
}
