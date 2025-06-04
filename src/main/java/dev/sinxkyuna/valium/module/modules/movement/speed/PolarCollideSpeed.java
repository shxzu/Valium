package dev.sinxkyuna.valium.module.modules.movement.speed;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Speed;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class PolarCollideSpeed extends SubMode<Speed> {
    public PolarCollideSpeed(String name, Speed parentModule) {
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

        if (mc.player.input.movementForward == 0.0f && mc.player.input.movementSideways == 0.0f) {
            return;
        }

        int collisions = 0;

        Box box = mc.player.getBoundingBox();

        for (Entity entity : mc.world.getEntities()) {
            Box entityBox = entity.getBoundingBox();
            if (getParentModule().canCauseSpeed(entity) && box.intersects(entityBox)) {
                collisions++;
            }
        }

        double yaw = MoveUtils.getDirection();
        double boost = 0.12 * collisions;
        mc.player.addVelocity(-Math.sin(yaw) * boost, 0.0, Math.cos(yaw) * boost);
    };
}
