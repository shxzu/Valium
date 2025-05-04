package dev.shxzu.valium.module.modules.combat.velocity;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.mixin.accesors.KeyBindingAccessor;
import dev.shxzu.valium.module.modules.combat.Velocity;
import dev.shxzu.valium.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.client.option.KeyBinding;

public class LegitVelocity extends SubMode<Velocity> {
    public LegitVelocity(String name, Velocity parentModule) {
        super(name, parentModule);
    }

    private boolean jumped;

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.hurtTime == 9 && mc.currentScreen == null && 85 > Math.random() * 100) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = true);
        } else if (jumped) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = false);
        }
    };

    @Override
    public void onDisable() {
        if (jumped) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = false);
        }
        super.onDisable();
    }

    @Override
    public void onEnable() {
        jumped = false;
        super.onEnable();
    }
}
