package dev.sinxkyuna.valium.module.modules.movement.spider;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Spider;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;

public class VanillaSpider extends SubMode<Spider> {
    public VanillaSpider(String name, Spider parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.horizontalCollision && mc.player.fallDistance < 1) {
            MoveUtils.setMotionY(getParentModule().verticalMotion.getValueFloat());
        }
    };
}
