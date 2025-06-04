package dev.sinxkyuna.valium.module.modules.movement.clicktp;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventMotionPre;
import dev.sinxkyuna.valium.module.modules.movement.ClickTP;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.RayTraceUtils;
import net.minecraft.util.hit.BlockHitResult;

public class VanillaClickTp extends SubMode<ClickTP> {
    public VanillaClickTp(String name, ClickTP parentModule) {
        super(name, parentModule);
    }

    private boolean pressed;

    @Override
    public void onEnable() {
        pressed = false;
        super.onEnable();
    }

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }

        if (mc.options.pickItemKey.isPressed()) {
            if (!pressed) {
                pressed = true;
                final BlockHitResult blockHitResult = RayTraceUtils.rayTrace(70, mc.player.getYaw(), mc.player.getPitch());

                if (blockHitResult != null) {
                    if (blockHitResult.getBlockPos() != null) {
                        mc.player.setPosition(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() + 1, blockHitResult.getBlockPos().getZ());
                    }
                }
            }
        } else {
            if (pressed) {
                pressed = false;
            }
        }
    };
}
