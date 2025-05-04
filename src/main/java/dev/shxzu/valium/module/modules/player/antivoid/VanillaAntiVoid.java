package dev.shxzu.valium.module.modules.player.antivoid;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.movement.Fly;
import dev.shxzu.valium.module.modules.player.AntiVoid;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.PlayerUtil;
import net.minecraft.util.math.Vec3d;

public class VanillaAntiVoid extends SubMode<AntiVoid> {
    public VanillaAntiVoid(String name, AntiVoid parentModule) {
        super(name, parentModule);
    }

    private Vec3d lastSafePos;

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Fly.class).isEnabled()) {
            return;
        }

        if (PlayerUtil.isOverVoid() && mc.player.fallDistance >= getParentModule().minFallDistance.getValueFloat()) {
            mc.player.setPosition(lastSafePos);
        } else if (mc.player.isOnGround()) {
            lastSafePos = new Vec3d(mc.player.getBlockPos().toCenterPos().x, mc.player.getY(), mc.player.getBlockPos().toCenterPos().z);
        }
    };
}
