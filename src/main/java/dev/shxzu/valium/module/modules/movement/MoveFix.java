package dev.shxzu.valium.module.modules.movement;

import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;

public class MoveFix extends Module {
    public static final BooleanSetting silent = new BooleanSetting("Silent", false);

    public MoveFix() {
        super("MoveFix", "Fixes your movement", 0, ModuleCategory.MOVEMENT);
        this.addSettings(silent);
    }
}
