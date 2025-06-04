package dev.sinxkyuna.valium.module.modules.movement.speed;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Speed;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.CombatUtils;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

public class NCPSpeed extends SubMode<Speed> {
    public NCPSpeed(String name, Speed parentModule) {
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
        if (mc.player.hurtTime > getParentModule().ncpHurtBoostHurttime.getValueInt() && getParentModule().ncpHurtBoost.getValue() && CombatUtils.isInCombat()) {
            MoveUtils.strafe(getParentModule().ncpHurtBoostSpeed.getValue());
        } else {
            MoveUtils.strafe(Math.max(MoveUtils.getSpeed(), MoveUtils.getAllowedHorizontalDistance()));
        }

        if (getParentModule().ncpLowHop.getValue()) {
            if (PlayerUtil.inAirTicks() == 4) {
                MoveUtils.setMotionY(-0.09800000190734864);
            }
        }

        if (getParentModule().ncpTimerBoost.getValue()) {
            switch (PlayerUtil.inAirTicks()) {
                case 0 -> PlayerUtil.setTimer(0.8f);
                case 1 -> PlayerUtil.setTimer(1.5f);
                case 3 -> PlayerUtil.setTimer(1.008f);
            }
        }
        if (getParentModule().ncpGlide.getValue()) {
            if (PlayerUtil.getDistanceToGround() != 0 && PlayerUtil.getDistanceToGround() <= 1 && mc.player.fallDistance <= 1) {
                if (PlayerUtil.inAirTicks() >= 10) {
                    MoveUtils.setMotionY(MoveUtils.getMotionY() / 27);
                }
            }
        }
    };
}