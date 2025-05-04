package dev.shxzu.valium.module.modules.client;

import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.ModeSetting;
import dev.shxzu.valium.module.setting.impl.NumberSetting;

public class Notifications extends Module {
    public static final NumberSetting opacity = new NumberSetting("BG Opacity", 0, 255, 80, 1);
    public static final ModeSetting fontMode = new ModeSetting("Font", "MC", "MC");

    public Notifications() {
        super("Notifications", "Notifies you of client events", 0, ModuleCategory.CLIENT);
        this.addSettings(opacity, fontMode);
    }

    public static int getOpacity() {
        return opacity.getValueInt();
    }

    public static String getFontMode() {
        return fontMode.getMode();
    }
}
