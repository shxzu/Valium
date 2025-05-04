package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.movement.longjump.DoubleJumpLongJump;
import dev.shxzu.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class LongJump extends Module {
    public final NewModeSetting longJumpMode = new NewModeSetting("Long Jump Mode", "Double Jump",
            new DoubleJumpLongJump("Double Jump", this));

    public LongJump() {
        super("LongJump", "Jumps big long distance", 0, ModuleCategory.MOVEMENT);
        this.addSetting(longJumpMode);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(longJumpMode.getCurrentMode().getName());
    };
}
