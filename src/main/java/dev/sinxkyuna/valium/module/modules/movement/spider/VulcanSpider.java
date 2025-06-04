package dev.sinxkyuna.valium.module.modules.movement.spider;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Spider;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

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
