package dev.sinxkyuna.valium.module.modules.movement;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.modules.movement.spider.VanillaSpider;
import dev.sinxkyuna.valium.module.modules.movement.spider.VerusSpider;
import dev.sinxkyuna.valium.module.modules.movement.spider.VulcanSpider;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class Spider extends Module {

    public final NewModeSetting spiderMode = new NewModeSetting("Mode", "Vanilla",
            new VanillaSpider("Vanilla", this),
            new VerusSpider("Verus", this),
            new VulcanSpider("Vulcan", this));

    public final NumberSetting verticalMotion = new NumberSetting("Vertical Motion", 0.1, 1, 0.42, 0.01);

    public Spider() {
        super("Spider", "Allows you to climb walls", 0, ModuleCategory.MOVEMENT);
        this.addSettings(spiderMode, verticalMotion);

        verticalMotion.addDependency(spiderMode, "Vanilla");
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        this.setSuffix(spiderMode.getCurrentMode().getName());
    };
}
