package dev.sinxkyuna.valium.module.modules.combat.criticals;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventAttack;
import dev.sinxkyuna.valium.module.modules.combat.Criticals;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.entity.effect.StatusEffects;

public class JumpCriticals extends SubMode<Criticals> {
    public JumpCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        boolean cantCritLegit = mc.player.fallDistance > 0.0F && !mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater() && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) && !mc.player.hasVehicle() && event.getTarget() != null;

        if (cantCritLegit) {
            return;
        }

        if(mc.player.isOnGround()) {
            mc.player.jump();
        }
    };
}
