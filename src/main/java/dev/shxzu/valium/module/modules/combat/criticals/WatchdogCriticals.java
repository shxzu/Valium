package dev.shxzu.valium.module.modules.combat.criticals;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventAttack;
import dev.shxzu.valium.module.modules.combat.Criticals;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;

public class WatchdogCriticals extends SubMode<Criticals> {
    public WatchdogCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.isOnGround()) {
            mc.player.setPosition(mc.player.getX(), mc.player.getY() + 0.001D, mc.player.getZ());
        }
    };
}
