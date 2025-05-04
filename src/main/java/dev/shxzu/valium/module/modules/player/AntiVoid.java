package dev.shxzu.valium.module.modules.player;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.modules.player.antivoid.BlinkAntiVoid;
import dev.shxzu.valium.module.modules.player.antivoid.MospixelAntiVoid;
import dev.shxzu.valium.module.modules.player.antivoid.MotionFlagAntiVoid;
import dev.shxzu.valium.module.modules.player.antivoid.VanillaAntiVoid;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.module.setting.impl.newmodesetting.NewModeSetting;

public class AntiVoid extends Module {
    public NewModeSetting mode = new NewModeSetting("AntiVoid Mode", "Vanilla", new VanillaAntiVoid("Vanilla", this), new MotionFlagAntiVoid("MotionFlag", this), new MospixelAntiVoid("Mospixel", this), new BlinkAntiVoid("Blink", this));
    public final NumberSetting minFallDistance = new NumberSetting("Min AntiVoid Distance", 2, 30, 8, 1);

    public AntiVoid() {
        super("AntiVoid", "Prevents you from falling in the void", 0, ModuleCategory.PLAYER);
        this.addSettings(mode, minFallDistance);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        this.setSuffix(mode.getCurrentMode().getName());
    };
}
