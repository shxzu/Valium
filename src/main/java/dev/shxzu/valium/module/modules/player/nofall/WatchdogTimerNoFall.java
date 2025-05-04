package dev.shxzu.valium.module.modules.player.nofall;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.player.NoFall;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PacketUtils;
import dev.shxzu.valium.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class WatchdogTimerNoFall extends SubMode<NoFall> {
    public WatchdogTimerNoFall(String name, NoFall parentModule) {
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
            PlayerUtil.setTimer(0.5f);

            PacketUtils.sendPacketSilently(new PlayerMoveC2SPacket.OnGroundOnly(true));

            mc.player.fallDistance = 0f;

            Client.INSTANCE.getDelayUtil().queue(ev -> PlayerUtil.setTimer(1F), 1);
        }
    };
}
