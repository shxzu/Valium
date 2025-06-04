package dev.sinxkyuna.valium.module.modules.movement.speed;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Speed;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

public class UNCPSpeed extends SubMode<Speed> {
    public UNCPSpeed(String name, Speed parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (!MoveUtils.isMoving2()) {
            return;
        }
        if (PlayerUtil.inAirTicks() >= 5.1) {
            PlayerUtil.setTimer(1.2f);
            } else PlayerUtil.setTimer(1.0f);
        MoveUtils.strafe();
        };
    }
