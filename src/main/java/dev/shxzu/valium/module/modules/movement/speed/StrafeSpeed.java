package dev.shxzu.valium.module.modules.movement.speed;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.movement.Speed;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.MoveUtils;

public class StrafeSpeed extends SubMode<Speed> {
    public StrafeSpeed(String name, Speed parentModule) {
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
        MoveUtils.strafe();
    };
}
