package dev.shxzu.valium.module.modules.client;

import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.ModeSetting;
import dev.shxzu.valium.module.setting.impl.NumberSetting;

public class Theme extends Module {
    public static final ModeSetting themeMode = new ModeSetting("Theme", "Bubblegum", "Bubblegum", "Shade", "Moon", "Lime", "Rise", "Fire", "Mango", "XMAX", "Amethyst", "Vibes", "Tenacity", "Custom");
    public static final NumberSetting redMain = new NumberSetting("Red Main", 0, 255, 0, 1);
    public static final NumberSetting greenMain = new NumberSetting("Green Main", 0, 255, 0, 1);
    public static final NumberSetting blueMain = new NumberSetting("Blue Main", 0, 255, 0, 1);
    public static final NumberSetting redSecond = new NumberSetting("Red Second", 0, 255, 255, 1);
    public static final NumberSetting greenSecond = new NumberSetting("Green Second", 0, 255, 255, 1);
    public static final NumberSetting blueSecond = new NumberSetting("Blue Second", 0, 255, 255, 1);

    public Theme() {
        super("Theme", "Changes the clients theme", 0, ModuleCategory.CLIENT);
        this.addSettings(themeMode, redMain, greenMain, blueMain, redSecond, greenSecond, blueSecond);
        redMain.addDependency(themeMode, "Custom");
        greenMain.addDependency(themeMode, "Custom");
        blueMain.addDependency(themeMode, "Custom");
        redSecond.addDependency(themeMode, "Custom");
        greenSecond.addDependency(themeMode, "Custom");
        blueSecond.addDependency(themeMode, "Custom");
    }

    @Override
    public void onEnable() {
        this.toggle();
        super.onEnable();
    }
}
