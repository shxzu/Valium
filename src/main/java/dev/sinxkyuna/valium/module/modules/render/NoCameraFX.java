package dev.sinxkyuna.valium.module.modules.render;

import dev.sinxkyuna.valium.event.bus.Listener;
import dev.sinxkyuna.valium.event.bus.annotations.EventLink;
import dev.sinxkyuna.valium.event.impl.player.EventTickPre;
import dev.sinxkyuna.valium.module.Module;
import dev.sinxkyuna.valium.module.ModuleCategory;
import dev.sinxkyuna.valium.module.setting.impl.BooleanSetting;
import net.minecraft.entity.effect.StatusEffects;

public class NoCameraFX extends Module {
    public static BooleanSetting hurtCam = new BooleanSetting("HurtCam", false);
    public static BooleanSetting blindness = new BooleanSetting("Blindness", false);
    public static BooleanSetting darkness = new BooleanSetting("Darkness", false);

    public NoCameraFX() {
        super("NoCameraFX", "Removes the hurt tilt and blindness", 0, ModuleCategory.RENDER);
        this.addSettings(hurtCam, blindness, darkness);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (blindness.getValue() && mc.player.hasStatusEffect(StatusEffects.BLINDNESS))
            mc.player.removeStatusEffect(StatusEffects.BLINDNESS);
        if (darkness.getValue() && mc.player.hasStatusEffect(StatusEffects.DARKNESS))
            mc.player.removeStatusEffect(StatusEffects.DARKNESS);
    };
}
