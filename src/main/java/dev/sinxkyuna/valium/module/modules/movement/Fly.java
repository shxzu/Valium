package dev.sinxkyuna.valium.module.modules.movement;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.modules.movement.fly.AirWalkFly;
import dev.sinxkyuna.valium.module.modules.movement.fly.CubecraftFly;
import dev.sinxkyuna.valium.module.modules.movement.fly.OldGrimBoatFly;
import dev.sinxkyuna.valium.module.modules.movement.fly.VanillaFly;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;
import dev.sinxkyuna.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class Fly extends Module {
    public final NewModeSetting flyMode = new NewModeSetting("Fly Mode", "Vanilla",
            new VanillaFly("Vanilla", this),
            new CubecraftFly("Cubecraft", this),
            new AirWalkFly("Air Walk", this),
            new OldGrimBoatFly("Old Grim Boat", this));

    public final NumberSetting speed = new NumberSetting("Speed", 0, 5, 1, 0.001);
    public final NumberSetting ccSpeed = new NumberSetting("Speed", 0, 2, 0.5, 0.05);


    public Fly() {
        super("Fly", "Lets you fly in vanilla", 0, ModuleCategory.MOVEMENT);
        addSettings(flyMode, speed, ccSpeed);
        speed.addDependency(flyMode, "Vanilla");
        ccSpeed.addDependency(flyMode, "Cubecraft");
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