package dev.shxzu.valium.module.modules.player.scaffold.tower;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.modules.player.Scaffold;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import dev.shxzu.valium.utils.mc.MoveUtils;
import dev.shxzu.valium.utils.mc.PlayerUtil;

public class VulcanTower extends SubMode<Scaffold> {
    public VulcanTower(String name, Scaffold parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (getParentModule().canTower()) {
            if (mc.player.isOnGround()) {
                MoveUtils.setMotionY(0.42F);
            }
            switch (PlayerUtil.inAirTicks() % 3) {
                case 0:
                    MoveUtils.setMotionY(0.41985 + (Math.random() * 0.000095));
                    break;
                case 2:
                    MoveUtils.setMotionY(Math.ceil(mc.player.getY()) - mc.player.getY());
                    break;
            }
        }
    };
}
