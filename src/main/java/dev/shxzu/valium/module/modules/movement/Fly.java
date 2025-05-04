package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.movement.fly.AirWalkFly;
import dev.shxzu.valium.module.modules.movement.fly.GrimBoatFly;
import dev.shxzu.valium.module.modules.movement.fly.VanillaFly;
import dev.shxzu.valium.module.modules.movement.fly.VulcanGlideFly;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class Fly extends Module {
    public final NewModeSetting flyMode = new NewModeSetting("Fly Mode", "Vanilla",
            new VanillaFly("Vanilla", this),
            new AirWalkFly("Air Walk", this),
            new GrimBoatFly("Grim Boat", this),
            new VulcanGlideFly("Vulcan Glide", this));

    public final NumberSetting speed = new NumberSetting("Speed", 0, 5, 1, 0.001);


    public Fly() {
        super("Fly", "Lets you fly in vanilla", 0, ModuleCategory.MOVEMENT);
        addSettings(flyMode, speed);
        speed.addDependency(flyMode, "Vanilla");
    }

    @Override
    public void onEnable() {
        if (isNull()) {
            toggle();
            return;
        }
        super.onEnable();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(flyMode.getCurrentMode().getName());
    };
}