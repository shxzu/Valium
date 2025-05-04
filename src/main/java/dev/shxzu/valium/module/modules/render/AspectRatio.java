package dev.shxzu.valium.module.modules.render;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.BooleanSetting;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import net.minecraft.entity.effect.StatusEffects;

public class AspectRatio extends Module {

    public static final NumberSetting ratio = new NumberSetting("Ratio", 0.1, 1.78, 1.78, 0.1);

    public AspectRatio() {
        super("AspectRatio", "Makes your game W I D E or smol.", 0, ModuleCategory.RENDER);
        this.addSettings(ratio);
    }
}
