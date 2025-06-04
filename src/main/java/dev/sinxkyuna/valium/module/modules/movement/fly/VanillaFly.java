package dev.sinxkyuna.valium.module.modules.movement.fly;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Fly;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;

public class VanillaFly extends SubMode<Fly> {
    public VanillaFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        mc.player.getVelocity().y = 0.0D + (mc.options.jumpKey.isPressed() ? 0.42f : 0.0D) - (mc.options.sneakKey.isPressed() ? 0.42f : 0.0D);
        if (MoveUtils.isMoving2()) {
            MoveUtils.strafe(getParentModule().speed.getValue());
        }
    };
}
