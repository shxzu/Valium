package dev.sinxkyuna.valium.module.modules.movement.fly;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.event.impl.world.EventBlockShape;
import dev.sinxkyuna.valium.module.modules.movement.Fly;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;
import net.minecraft.util.shape.VoxelShapes;

public class CubecraftFly extends SubMode<Fly> {
    public CubecraftFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    private boolean hurt = false;

    @EventLink
    public final Listener<EventBlockShape> eventBlockShapeListener = event -> {
        if(hurt) {
            if (isNull()) {
                return;
            }
            if (event.getPos().getY() < mc.player.getBlockY()) {
                event.setShape(VoxelShapes.fullCube());
            }
        }
    };

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if(hurt) {
            mc.player.setOnGround(true);
            if (mc.options.jumpKey.isPressed()) {
                mc.options.jumpKey.setPressed(false);
            }
            if(MoveUtils.isMoving()) {
                MoveUtils.strafe(getParentModule().ccSpeed.getValue());
            }
        }
    };

    @Override
    public void onDisable() {
        mc.player.setVelocity(0, 0, 0);
        MoveUtils.stop();
        hurt = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        PlayerUtil.damageCubecraft();
        hurt = true;
        super.onEnable();
    }

}
