package dev.sinxkyuna.valium.module.modules.render;

import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;

public class AspectRatio extends Module {

    public static final NumberSetting ratio = new NumberSetting("Ratio", 0.1, 1.78, 1.78, 0.1);

    public AspectRatio() {
        super("AspectRatio", "Makes your game W I D E.", 0, ModuleCategory.RENDER);
        this.addSettings(ratio);
    }
}
