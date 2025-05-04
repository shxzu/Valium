package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.movement.spider.VanillaSpider;
import dev.shxzu.valium.module.modules.movement.spider.VerusSpider;
import dev.shxzu.valium.module.modules.movement.spider.VulcanSpider;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.module.setting.impl.newmodesetting.NewModeSetting;

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
