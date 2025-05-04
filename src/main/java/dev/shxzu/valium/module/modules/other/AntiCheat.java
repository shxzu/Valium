package dev.shxzu.valium.module.modules.other;

import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;

public class AntiCheat extends Module {
    public static final BooleanSetting checkSelf = new BooleanSetting("Check self", true);

    public AntiCheat() {
        super("AntiCheat", "Detects if other players are cheating", 0, ModuleCategory.OTHER);
        this.addSettings(checkSelf);
    }
}