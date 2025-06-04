package dev.sinxkyuna.valium.module.modules.player.scaffold.tower;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.player.Scaffold;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;

public class NormalTower extends SubMode<Scaffold> {
    public NormalTower(String name, Scaffold parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (getParentModule().canTower()) {
            MoveUtils.setMotionY(0.42F);
        }
    };
}
