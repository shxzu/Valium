package dev.sinxkyuna.valium.module.modules.other;

import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.BooleanSetting;

public class AntiCheat extends Module {
    public static final BooleanSetting checkSelf = new BooleanSetting("Check self", true);

    public AntiCheat() {
        super("AntiCheat", "Detects if other players are cheating", 0, ModuleCategory.OTHER);
        this.addSettings(checkSelf);
    }
}