package dev.sinxkyuna.valium.module.modules.movement.speed;

import dev.sinxkyuna.valium.Client;
import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventMotionPre;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.modules.movement.Speed;
import dev.sinxkyuna.valium.module.modules.other.Disabler;
import dev.sinxkyuna.valium.module.modules.player.Scaffold;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import dev.sinxkyuna.valium.utils.mc.MoveUtils;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;
import net.minecraft.entity.effect.StatusEffects;


public class WatchdogSpeed extends SubMode<Speed> {
    public WatchdogSpeed(String name, Speed parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull())
            return;
        if (!MoveUtils.isMoving2()) {
            return;
        }

        if (!getParentModule().isFullBlockBelow()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled()) {
            Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
            if (Scaffold.keepY.getValue()) {
                Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
                if (Scaffold.keepYMode.isMode("Watchdog") && MoveUtils.isMoving2() && Client.INSTANCE.getModuleManager().getModule(Scaffold.class).blocksPlaced >= 1) {
                    Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
                    if (Scaffold.keepYLowHop.getValue()) {
                        boolean canLowHop = Client.INSTANCE.getModuleManager().getModule(Disabler.class).isEnabled() && Disabler.watchdogMotion.getValue() && Client.INSTANCE.getModuleManager().getModule(Disabler.class).canLowHop;
                        if (canLowHop) {
                            return;
                        }
                    }
                }
            }
        }

        if (getParentModule().watchdogLowHop.getValue()) {
            boolean canLowHop = Client.INSTANCE.getModuleManager().getModule(Disabler.class).isEnabled() && Disabler.watchdogMotion.getValue() && Client.INSTANCE.getModuleManager().getModule(Disabler.class).canLowHop;
            if (!getParentModule().watchdogNeedDisabler.getValue() || canLowHop) {
                switch (PlayerUtil.inAirTicks()) {
                    case 3 -> mc.player.getVelocity().y -= 0.0025;
                    case 4 -> mc.player.getVelocity().y -= 0.04;
                    case 5 -> mc.player.getVelocity().y -= 0.1905189780583944;
                    case 6 -> mc.player.getVelocity().multiply(0.1);
                }
            }
        }

        if (getParentModule().watchdogStrafe.getValue()) {
            if (mc.player.fallDistance > 1 || mc.player.isOnGround()) {
                return;
            }

            if (PlayerUtil.inAirTicks() == 1) {
                MoveUtils.strafe();
            }

            if (!PlayerUtil.blockStateRelativeToPlayer(0, mc.player.getVelocity().y, 0).isAir() && PlayerUtil.inAirTicks() > 2) {
                MoveUtils.strafe();
            }

            if (PlayerUtil.inAirTicks() < 2 || (PlayerUtil.blockStateRelativeToPlayer(0, mc.player.getVelocity().y * 3, 0).isAir() && PlayerUtil.inAirTicks() != 9)) {
                return;
            }

            mc.player.getVelocity().y += 0.0754f;
            MoveUtils.strafe();
        }
    };

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (!MoveUtils.isMoving2()) {
            return;
        }

        if (mc.player.isOnGround() && getParentModule().isFullBlockBelow()) {
            double speed = 0.281 + 0.13 * (mc.player.getStatusEffect(StatusEffects.SPEED) != null ? mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() : 0);
            MoveUtils.strafe(speed);
        }
    };
}
