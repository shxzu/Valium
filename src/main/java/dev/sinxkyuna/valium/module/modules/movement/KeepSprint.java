package dev.sinxkyuna.valium.module.modules.movement;

import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.BooleanSetting;

public class KeepSprint extends Module {
    public static final BooleanSetting sprint = new BooleanSetting("Sprint",true);

    public KeepSprint() {
        super("KeepSprint", "Removes attack slowdown", 0, ModuleCategory.MOVEMENT);
        this.addSetting(sprint);
    }
}
