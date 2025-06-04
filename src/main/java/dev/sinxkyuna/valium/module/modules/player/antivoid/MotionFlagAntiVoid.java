package dev.sinxkyuna.valium.module.modules.player.antivoid;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Fly;
import dev.sinxkyuna.valium.module.modules.player.AntiVoid;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

public class MotionFlagAntiVoid extends SubMode<AntiVoid> {
    public MotionFlagAntiVoid(String name, AntiVoid parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Fly.class).isEnabled()) {
            return;
        }

        if (PlayerUtil.isOverVoid() && mc.player.fallDistance >= getParentModule().minFallDistance.getValueFloat() && mc.player.getBlockY() + mc.player.getVelocity().y < Math.floor(mc.player.getBlockY())) {
            MoveUtils.setMotionY(3);
            mc.player.fallDistance = 0;
        }
    };
}
