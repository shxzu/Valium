package dev.sinxkyuna.valium.module.modules.movement.fly;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Fly;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import net.minecraft.entity.vehicle.BoatEntity;

public class OldGrimBoatFly extends SubMode<Fly> {
    public OldGrimBoatFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    private boolean grimBoatSend;

    @Override
    public void onEnable() {
        grimBoatSend = false;
        super.onEnable();
    }
    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.hasVehicle() && mc.player.getVehicle() instanceof BoatEntity boat) {
            mc.player.setYaw(boat.getYaw());
            mc.player.setPitch(90f);
            grimBoatSend = true;
        }

        if (grimBoatSend && mc.player.hasVehicle()) {
            mc.options.sneakKey.setPressed(true);
        }

        if (grimBoatSend && !mc.player.hasVehicle()) {
            MoveUtils.setMotionY(1.8);
            grimBoatSend = false;
            mc.options.sneakKey.setPressed(false);
            getParentModule().toggle();
        }
    };
}
