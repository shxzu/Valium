package dev.shxzu.valium.module.modules.movement.speed;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.movement.Speed;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class GrimCollideSpeed extends SubMode<Speed> {
    public GrimCollideSpeed(String name, Speed parentModule) {
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

        Box box = mc.player.getBoundingBox().expand(1.0);

        for (Entity entity : mc.world.getEntities()) {
            Box entityBox = entity.getBoundingBox();
            if (getParentModule().canCauseSpeed(entity) && box.intersects(entityBox)) {
                collisions++;
            }
        }

        double yaw = MoveUtils.getDirection();
        float boost = 0.08F * collisions;
        mc.player.addVelocity(-Math.sin(yaw) * boost, 0.0, Math.cos(yaw) * boost);
    };
}
