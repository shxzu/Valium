package dev.sinxkyuna.valium.module.modules.movement.fly;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.world.EventBlockShape;
import dev.sinxkyuna.valium.module.modules.movement.Fly;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.util.shape.VoxelShapes;

public class AirWalkFly extends SubMode<Fly> {
    public AirWalkFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventBlockShape> eventBlockShapeListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getPos().getY() < mc.player.getBlockY()) {
            event.setShape(VoxelShapes.fullCube());
        }
    };
}
