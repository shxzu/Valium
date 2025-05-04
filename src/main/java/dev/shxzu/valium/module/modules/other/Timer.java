package dev.shxzu.valium.module.modules.other;

import dev.shxzu.valium.event.bus.Listener;
import dev.shxzu.valium.event.bus.annotations.EventLink;
import dev.shxzu.valium.event.impl.player.EventTickPre;
import dev.shxzu.valium.module.Module;
import dev.shxzu.valium.module.ModuleCategory;
import dev.shxzu.valium.module.setting.impl.NumberSetting;
import dev.shxzu.valium.utils.mc.PlayerUtil;

public class Timer extends Module {
    public static final NumberSetting dhauohfeidbf = new NumberSetting("Timer", 0.1, 10, 1, 0.001);

    public Timer() {
        super("Timer", "Modify game speed", 0, ModuleCategory.OTHER);
        this.addSetting(dhauohfeidbf);
    }

    @Override
    public void onEnable() {
        PlayerUtil.setTimer(dhauohfeidbf.getValueFloat());
        super.onEnable();
    }

    @EventLink
    public final Listener<EventTickPre> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        PlayerUtil.setTimer(dhauohfeidbf.getValueFloat());
    };

    @Override
    public void onDisable() {
        PlayerUtil.setTimer(1.0f);
        super.onDisable();
    }
}
