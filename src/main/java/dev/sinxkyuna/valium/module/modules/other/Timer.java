package dev.sinxkyuna.valium.module.modules.other;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.NumberSetting;
import dev.sinxkyuna.valium.utils.mc.PlayerUtil;

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
