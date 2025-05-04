package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.movement.clicktp.MospixelClickTp;
import dev.shxzu.valium.module.modules.movement.clicktp.VanillaClickTp;
import dev.shxzu.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class ClickTP extends Module {
    public final NewModeSetting clickTPMode = new NewModeSetting("ClickTP Mode", "Vanilla",
            new VanillaClickTp("Vanilla", this),
            new MospixelClickTp("Mospixel", this));

    public ClickTP() {
        super("ClickTP", "Middle Click To Teleport", 0, ModuleCategory.MOVEMENT);
        this.addSettings(clickTPMode);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(clickTPMode.getCurrentMode().getName());
    };
}
