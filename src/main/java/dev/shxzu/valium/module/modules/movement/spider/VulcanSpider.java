package dev.shxzu.valium.module.modules.movement.spider;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.movement.Spider;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PlayerUtil;

public class VulcanSpider extends SubMode<Spider> {
    public VulcanSpider(String name, Spider parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.horizontalCollision && mc.player.fallDistance < 1) {
            if (PlayerUtil.ticksExisted() % 2 == 0) {
                mc.player.jump();
            }
        }
    };
}
