package dev.sinxkyuna.valium.module.modules.movement;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.modules.movement.clicktp.MospixelClickTp;
import dev.sinxkyuna.valium.module.modules.movement.clicktp.VanillaClickTp;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.NewModeSetting;

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
