package dev.shxzu.valium.module.modules.client;

import dev.shxzu.valium.Client;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;

// thunderhack dev gave me these
public class PostProcessing extends Module {
    public static final BooleanSetting targetHud = new BooleanSetting("TargetHud", false);
    public static final BooleanSetting waterMark = new BooleanSetting("WaterMark", false);
    public static final BooleanSetting arrayList = new BooleanSetting("Arraylist", false);

    public PostProcessing() {
        super("PostProcessing", "Adds blur", 0, ModuleCategory.CLIENT);
        this.addSettings(targetHud, waterMark, arrayList);
    }

    public static boolean shouldBlurTargetHud() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && targetHud.getValue();
    }

    public static boolean shouldBlurWaterMark() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && waterMark.getValue();
    }

    public static boolean shouldBlurArrayList() {
        return Client.INSTANCE.getModuleManager().getModule(PostProcessing.class).isEnabled() && arrayList.getValue();
    }
}
