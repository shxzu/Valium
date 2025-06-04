package dev.sinxkyuna.valium.module.modules.movement.speed;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Speed;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

public class IntaveSpeed extends SubMode<Speed> {
    public IntaveSpeed(String name, Speed parentModule) {
        super(name, parentModule);
    }

//    Intave 14 speed w/timer from Liquidbounce (~8.7% faster than vanilla) (yes i skidded it smh)

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (!MoveUtils.isMoving2()) {
            return;
        }
        switch (PlayerUtil.inAirTicks()) {
            case 1:
                mc.player.getVelocity().x *= 1.04;
                mc.player.getVelocity().z *= 1.04;
                break;
            case 2, 3, 4:
                mc.player.getVelocity().x *= 1.02;
                mc.player.getVelocity().z *= 1.02;
                break;
        }
        if (getParentModule().intaveTimer.getValue()) {
            PlayerUtil.setTimer(1.002f);
        }
    };
}
